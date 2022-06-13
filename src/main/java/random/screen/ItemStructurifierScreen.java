package random.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import random.packet.PacketTrainDataGuiClient;

public class ItemStructurifierScreen extends HandledScreen<ItemStructurifierScreenHandler> {

	private int delay;
	private final ButtonWidget buttonGenerate;
	private static final Identifier TEXTURE = new Identifier("textures/gui/container/stonecutter.png");

	public ItemStructurifierScreen(ItemStructurifierScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		buttonGenerate = new ButtonWidget(0, 0, 80, 20, new TranslatableText("gui.random.generate"), button -> {
			delay = 20;
			button.active = false;
		});
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShaderTexture(0, TEXTURE);
		drawTexture(matrices, (width - backgroundWidth) / 2, (height - backgroundHeight) / 2, 0, 0, backgroundWidth, backgroundHeight);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	@Override
	protected void handledScreenTick() {
		if (delay > 0) {
			delay--;
			if (delay == 0) {
				PacketTrainDataGuiClient.generateStructureC2S();
				buttonGenerate.active = true;
			}
		}
	}

	@Override
	protected void init() {
		super.init();
		titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
		buttonGenerate.x = width / 2 - 40;
		buttonGenerate.y = height / 2 - 52;
		addDrawableChild(buttonGenerate);
	}
}
