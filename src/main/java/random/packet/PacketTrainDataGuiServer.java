package random.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.entity.EntityRocket;
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

	public static void openRocketCallerScreenC2S(ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, PACKET_OPEN_ROCKET_CALLER_SCREEN, PacketByteBufs.create());
	}

	public static void launchRocketC2S(MinecraftServer server, ServerPlayerEntity player, PacketByteBuf packet) {
		final BlockPos pos = packet.readBlockPos();
		server.execute(() -> {
			final World world = player.world;
			world.spawnEntity(new EntityRocket(world, player.getX(), player.getY(), player.getZ(), pos));
		});
	}
}
