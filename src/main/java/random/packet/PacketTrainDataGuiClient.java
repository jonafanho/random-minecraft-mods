package random.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import random.screen.RocketCallerScreen;

public class PacketTrainDataGuiClient implements IPacket {

	public static void generateStructureC2S() {
		final PacketByteBuf packet = PacketByteBufs.create();
		ClientPlayNetworking.send(PACKET_GENERATE_STRUCTURE, packet);
	}

	public static void openRocketCallerScreenS2C(MinecraftClient minecraftClient) {
		minecraftClient.execute(() -> {
			if (!(minecraftClient.currentScreen instanceof RocketCallerScreen)) {
				minecraftClient.setScreen(new RocketCallerScreen());
			}
		});
	}

	public static void launchRocketC2S(BlockPos pos) {
		final PacketByteBuf packet = PacketByteBufs.create();
		packet.writeBlockPos(pos);
		ClientPlayNetworking.send(PACKET_LAUNCH_ROCKET, packet);
	}
}
