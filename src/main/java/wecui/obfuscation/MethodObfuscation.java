package wecui.obfuscation;

/**
 * Method name obfuscation, used whenever we use reflection. 
 * This lets us keep reflected obfuscated methods in a central
 * class to ease updating.
 * 
 * @author yetanotherx
 * 
 * @obfuscated
 */
public enum MethodObfuscation {

    /**
     * Location of the .minecraft directory in Minecraft.class
     */
    MINECRAFTDIR("aj"),
    /**
     * NetworkManager instance in NetClientHandler.class
     */
    NETWORKMANAGER("g"),
    /**
     * Packet stream list in NetworkManager.class
     */
    PACKETLIST("n"),
    /**
     * ID->Class hashmap for packets in Packet.class
     */
    IDSTOCLASSES("j"),
    /**
     * Class->ID hashmap for packets in Packet.class
     */
    CLASSESTOIDS("a");
    protected String variable;

    private MethodObfuscation(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    public static String getVariable(MethodObfuscation type) {
        return type.getVariable();
    }
}
