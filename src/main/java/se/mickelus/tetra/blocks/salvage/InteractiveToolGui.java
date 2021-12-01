package se.mickelus.tetra.blocks.salvage;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ToolAction;
import se.mickelus.mgui.gui.GuiElement;
import se.mickelus.mgui.gui.animation.Applier;
import se.mickelus.mgui.gui.animation.KeyframeAnimation;
import se.mickelus.mgui.gui.impl.GuiColors;
import se.mickelus.tetra.blocks.workbench.gui.GuiTool;
import se.mickelus.tetra.properties.PropertyHelper;

import javax.annotation.ParametersAreNonnullByDefault;
@ParametersAreNonnullByDefault
public class InteractiveToolGui extends GuiElement {
    private GuiTool toolIcon;

    private KeyframeAnimation show;
    private KeyframeAnimation hide;

    private ToolAction toolType;
    private int toolLevel;

    private Player player;
    private int currentSlot;

    public InteractiveToolGui(int x, int y, ToolAction toolType, int toolLevel, Player player) {
        super(x, y, 16, 16);
        opacity = 0;

        this.toolType = toolType;
        this.toolLevel = toolLevel;
        this.player = player;

        toolIcon = new GuiTool(-1, 0, toolType);
        addChild(toolIcon);

        show = new KeyframeAnimation(100, this)
                .applyTo(new Applier.Opacity(0, 1))
                .withDelay(650);
        hide = new KeyframeAnimation(100, this)
                .applyTo(new Applier.Opacity(1, 0));

        updateTint();
        currentSlot = player.getInventory().selected;
    }

    public void updateFadeTime() {
        show = show.withDelay(0);
    }

    private void updateTint() {
        int mainHandLevel = PropertyHelper.getItemToolLevel(player.getMainHandItem(), toolType);
        int offHandLevel = PropertyHelper.getItemToolLevel(player.getOffhandItem(), toolType);

        if (mainHandLevel >= toolLevel || offHandLevel >= toolLevel) {
            toolIcon.update(toolLevel, GuiColors.normal);
        } else if (PropertyHelper.getPlayerToolLevel(player, toolType) >= toolLevel) {
            toolIcon.update(toolLevel, GuiColors.warning);
        } else {
            toolIcon.update(toolLevel, GuiColors.negative);
        }
    }

    public void show() {
        updateTint();
        hide.stop();
        show.start();
    }

    public void hide() {
        show.stop();
        hide.start();
    }

    @Override
    public void draw(PoseStack matrixStack, int refX, int refY, int screenWidth, int screenHeight, int mouseX, int mouseY, float opacity) {
        if (player.getInventory().selected != currentSlot) {
            updateTint();
            currentSlot = player.getInventory().selected;
        }

        super.draw(matrixStack, refX, refY, screenWidth, screenHeight, mouseX, mouseY, opacity);
    }
}
