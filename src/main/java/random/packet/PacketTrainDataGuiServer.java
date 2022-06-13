package random.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import random.screen.ItemStructurifierScreenHandler;

public class PacketTrainDataGuiServer implements IPacket {

	public static void toggleLavaBossS2C(ServerPlayerEntity player, boolean isLavaBoss) {
		final PacketByteBuf packet = PacketByteBufs.create();
		packet.writeUuid(player.getUuid());
		packet.writeBoolean(isLavaBoss);
		ServerPlayNetworking.send(player, PACKET_TOGGLE_LAVA_BOSS, packet);
	}

	public static void generateStructureC2S(MinecraftServer server, ServerPlayerEntity player, PacketByteBuf packet) {
		server.execute(() -> {
			if (player.currentScreenHandler instanceof ItemStructurifierScreenHandler) {
				((ItemStructurifierScreenHandler) player.currentScreenHandler).generate();
			}
		});
	}
}
