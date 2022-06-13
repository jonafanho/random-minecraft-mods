package random.packet;

import net.minecraft.util.Identifier;
import random.Main;

public interface IPacket {

	Identifier PACKET_TOGGLE_LAVA_BOSS = new Identifier(Main.MOD_ID, "packet_toggle_lava_boss");
	Identifier PACKET_GENERATE_STRUCTURE = new Identifier(Main.MOD_ID, "packet_generate_structure");

	int MAX_PACKET_BYTES = 1048576;
}
