package wecui.obfuscation;

import deobf.IntHashMap;
import deobf.NetHandler;
import deobf.Packet;
import deobf.Packet3Chat;
import wecui.event.ChatCommandEvent;
import wecui.event.ChatEvent;
import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.util.Map;
import wecui.WorldEditCUI;

/**
 * Replacement for Packet3Chat, in order to listen 
 * to incoming chat without conflicting with mods
 * 
 * This uses reflection to be set as the packet.
 * Don't look at it, it's horribly ugly.
 * 
 * @author lahwran
 * @author yetanotherx
 *
 * @obfuscated
 */
public class Packet3CUIChat extends Packet3Chat {

    protected static WorldEditCUI controller;
    protected static boolean registered = false;
    protected boolean cancelled = false;

    public Packet3CUIChat() {
        super();
    }

    public Packet3CUIChat(String s) {
        super(s);
        ChatEvent chatevent = new ChatEvent(controller, s, ChatEvent.Direction.OUTGOING);
        controller.getEventManager().callEvent(chatevent);
        if (!chatevent.isCancelled() && s.startsWith("/") && s.length() > 1) {
            ChatCommandEvent commandevent = new ChatCommandEvent(controller, s);
            controller.getEventManager().callEvent(chatevent);
            if (commandevent.isHandled()) {
                cancelled = true;
            }
        } else {
            cancelled = true;
        }
    }

    /**
     * I warn you, do not read this method.
     * @param controller 
     */
    @SuppressWarnings("unchecked")
    public static void register(WorldEditCUI controller) {
        //I'm warning you...
        
        if (registered) {
            return;
        }
        registered = true;
        
        //Last chance...
        
        Packet3CUIChat.controller = controller;
        
        try {
            Class<Packet> packetClass = Packet.class;
            Field idstoclassesfield;
            Field classestoidsfield;
            try {
                idstoclassesfield = packetClass.getDeclaredField("j");
                classestoidsfield = packetClass.getDeclaredField("a");
            } catch (NoSuchFieldException e) {
                try {
                    idstoclassesfield = packetClass.getDeclaredField("packetIdToClassMap");
                    classestoidsfield = packetClass.getDeclaredField("packetClassToIdMap");
                } catch (NoSuchFieldException e1) {
                    e.printStackTrace();
                    throw e1;
                }
            }
            idstoclassesfield.setAccessible(true);
            classestoidsfield.setAccessible(true);
            IntHashMap idstoclasses = (IntHashMap) idstoclassesfield.get(null);
            Map<Class<?>, Integer> classestoids = (Map<Class<?>, Integer>) classestoidsfield.get(null);
            idstoclasses.a(3, Packet3CUIChat.class);
            classestoids.put(Packet3CUIChat.class, 3);
            
            //See why I told you not to read this method?
        } catch (Exception e) {
            throw new RuntimeException("Error inserting chat handler - WorldEditCUI and anything that depends on it will not work!", e);
        }
        
        controller.getDebugger().debug("Chat handler registered.");
    }

    public void a(DataOutputStream dataoutputstream) {
        if (!cancelled) {
            a(a, dataoutputstream);
        }
    }

    /**
     * TODO: Apparently this doesn't work right yet
     * @param nethandler 
     */
    public void a(NetHandler nethandler) {
        ChatEvent chatevent = new ChatEvent(controller, a, ChatEvent.Direction.INCOMING);
        controller.getEventManager().callEvent(chatevent);
        if (!chatevent.isCancelled()) {
            nethandler.a(this);
        }
    }
}
