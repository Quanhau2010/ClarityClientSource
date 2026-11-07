package need.clarity.clickgui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public final class ClickGuiScreen extends Screen {
    private final ClickGui gui;
    private final long openedAt;
    private boolean shiftArmed;

    public ClickGuiScreen() {
        super(Component.literal("Clarity"));
        this.openedAt = System.nanoTime();
        this.shiftArmed = false;
        GuiData var1 = GuiData.get();
        this.gui = new ClickGui(var1, new ConfigStore(var1));
        ClickGuiRenderer.resetScene();
    }

    public ClickGui gui() {
        return this.gui;
    }

    private float dx(double arg0) {
        Minecraft var3 = Minecraft.getInstance();
        return ((float) ((arg0 * ((double) var3.getWindow().getGuiScale())) / ((double) ClickGuiRenderer.designScale(this.gui))));
    }

    public void extractRenderState(GuiGraphicsExtractor arg0, int arg1, int arg2, float arg3) {
        Minecraft var5 = null;
        ClickGuiRenderer.render(arg0, this.gui);
        if (this.gui.isClosed()) {
            Minecraft mc = Minecraft.getInstance();
            mc.execute(() -> {
                if (mc.screen == this) {
                    mc.setScreen(null);
                }
            });
        }
    }

    public void extractBackground(GuiGraphicsExtractor arg0, int arg1, int arg2, float arg3) {
    }

    public void mouseMoved(double arg0, double arg1) {
        this.gui.mouseMove(this.dx(arg0), this.dx(arg1));
    }

    public boolean mouseClicked(MouseButtonEvent arg0, boolean arg1) {
        this.gui.mouseDown(this.dx(arg0.x()), this.dx(arg0.y()), arg0.button());
        return true;
    }

    public boolean mouseReleased(MouseButtonEvent arg0) {
        this.gui.mouseUp(this.dx(arg0.x()), this.dx(arg0.y()), arg0.button());
        return true;
    }

    public boolean mouseDragged(MouseButtonEvent arg0, double arg1, double arg2) {
        this.gui.mouseMove(this.dx(arg0.x()), this.dx(arg0.y()));
        return true;
    }

    public boolean mouseScrolled(double arg0, double arg1, double arg2, double arg3) {
        this.gui.scroll(this.dx(arg0), this.dx(arg1), ((float) arg3));
        return true;
    }

    public boolean keyPressed(KeyEvent arg0) {
        if (arg0.key() == 344) {
            if (this.shiftArmed) {
                if ((System.nanoTime() - this.openedAt) < 250000000L) {
                }
            }
            return true;
        }
        this.gui.key(arg0.key(), arg0.modifiers());
        return true;
    }

    public boolean keyReleased(KeyEvent arg0) {
        if (arg0.key() == 344) {
            this.shiftArmed = true;
        }
        return true;
    }

    public boolean charTyped(CharacterEvent arg0) {
        this.gui.chars(arg0.codepointAsString());
        return true;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void onClose() {
        this.gui.close();
    }

}
