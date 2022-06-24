package random.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import random.packet.PacketTrainDataGuiClient;

public class RocketCallerScreen extends Screen {

	private final int xOffset;
	private final int zOffset;

	public RocketCallerScreen() {
		super(new LiteralText(""));
		final PlayerEntity player = MinecraftClient.getInstance().player;
		xOffset = player == null ? 0 : player.getBlockX();
		zOffset = player == null ? 0 : player.getBlockZ();
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if (client != null) {
			final World world = client.world;
			if (world != null) {
				final Tessellator tessellator = Tessellator.getInstance();
				final BufferBuilder buffer = tessellator.getBuffer();
				RenderSystem.setShader(GameRenderer::getPositionColorShader);
				buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						final int posX = xOffset + x - width / 2;
						final int posZ = zOffset + y - height / 2;
						final int color = world.getBlockState(new BlockPos(posX, world.getTopY(Heightmap.Type.MOTION_BLOCKING, posX, posZ) - 1, posZ)).getBlock().getDefaultMapColor().color;
						drawRectangle(buffer, x, y, x + 1, y + 1, color);
					}
				}

				drawCross(buffer, width / 2, height / 2, 0xFFFFFFFF);
				drawCross(buffer, mouseX, mouseY, 0xFFFF0000);
				tessellator.draw();
			}
		}
		super.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (client != null) {
			final World world = client.world;
			if (world != null) {
				final int posX = (int) Math.floor(mouseX - width / 2F + xOffset);
				final int posZ = (int) Math.floor(mouseY - height / 2F + zOffset);
				PacketTrainDataGuiClient.launchRocketC2S(new BlockPos(posX, world.getTopY(Heightmap.Type.MOTION_BLOCKING, posX, posZ), posZ));
				close();
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	private static void drawRectangle(VertexConsumer vertexConsumer, int x1, int y1, int x2, int y2, int color) {
		final int r = (color >> 16) & 0xFF;
		final int g = (color >> 8) & 0xFF;
		final int b = color & 0xFF;
		vertexConsumer.vertex(x1, y1, 0).color(r, g, b, 0xFF).next();
		vertexConsumer.vertex(x1, y2, 0).color(r, g, b, 0xFF).next();
		vertexConsumer.vertex(x2, y2, 0).color(r, g, b, 0xFF).next();
		vertexConsumer.vertex(x2, y1, 0).color(r, g, b, 0xFF).next();
	}

	private static void drawCross(VertexConsumer vertexConsumer, int x, int y, int color) {
		drawRectangle(vertexConsumer, x - 4, y, x + 5, y + 1, color);
		drawRectangle(vertexConsumer, x, y - 4, x + 1, y + 5, color);
	}
}
