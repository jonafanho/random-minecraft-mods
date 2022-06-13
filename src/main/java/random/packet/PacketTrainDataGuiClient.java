package random.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

public class PacketTrainDataGuiClient implements IPacket {

	public static void generateStructureC2S() {
		final PacketByteBuf packet = PacketByteBufs.create();
		ClientPlayNetworking.send(PACKET_GENERATE_STRUCTURE, packet);
	}
}
