package wecui.event.listeners;

import net.minecraft.src.EntityClientPlayerMP;

import org.lwjgl.opengl.GL11;

import wecui.WorldEditCUI;
import wecui.event.WorldRenderEvent;
import wecui.fevents.Listener;

/**
 * Listener for WorldRenderEvent
 * 
 * @author lahwran
 * @author yetanotherx
 * 
 */
public class WorldRenderListener implements Listener<WorldRenderEvent> {

    private WorldEditCUI controller;

    public WorldRenderListener(WorldEditCUI controller) {
        this.controller = controller;
    }

    /**
     * Renders the current selection if it exists
     * @param event 
     */
    @Override
    public void onEvent(WorldRenderEvent event) {
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        //GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(false);
        GL11.glPushMatrix();

        try {
            GL11.glTranslated(-this.getPlayerXGuess(event.getPartialTick()),
                    -this.getPlayerYGuess(event.getPartialTick()),
                    -this.getPlayerZGuess(event.getPartialTick()));
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
            if (this.controller.getSelection() != null) {
                this.controller.getSelection().render();
            }
        } catch (Exception e) {
        }

        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glPopMatrix();

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        //GL11.glEnable(GL11.GL_ALPHA_TEST);
    }

	private double getPlayerXGuess(float renderTick)
	{
		EntityClientPlayerMP thePlayer = this.controller.getMinecraft().thePlayer;
		return thePlayer.prevPosX + ((thePlayer.posX - thePlayer.prevPosX) * renderTick);
	}
	
	private double getPlayerYGuess(float renderTick)
	{
		EntityClientPlayerMP thePlayer = this.controller.getMinecraft().thePlayer;
		return thePlayer.prevPosY + ((thePlayer.posY - thePlayer.prevPosY) * renderTick);
	}
	
	private double getPlayerZGuess(float renderTick)
	{
		EntityClientPlayerMP thePlayer = this.controller.getMinecraft().thePlayer;
		return thePlayer.prevPosZ + ((thePlayer.posZ - thePlayer.prevPosZ) * renderTick);
	}
}
