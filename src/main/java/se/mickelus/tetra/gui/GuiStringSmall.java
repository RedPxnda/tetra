package se.mickelus.tetra.gui;

import net.minecraft.client.renderer.GlStateManager;

public class GuiStringSmall extends GuiString {

    public GuiStringSmall(int x, int y, String string) {
        super(x*2, y*2, string);
    }

    @Override
    public void draw(int refX, int refY, int screenWidth, int screenHeight, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(.5, .5, .5);
        super.draw(refX*2, refY*2, screenWidth*2, screenHeight*2, mouseX*2, mouseY*2);
        GlStateManager.popMatrix();
    }
}
