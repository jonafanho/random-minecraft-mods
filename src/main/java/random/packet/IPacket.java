package random.packet;

import net.minecraft.util.Identifier;
import random.Main;

public interface IPacket {

	Identifier PACKET_TOGGLE_LAVA_BOSS = new Identifier(Main.MOD_ID, "packet_toggle_lava_boss");
	Identifier PACKET_GENERATE_STRUCTURE = new Identifier(Main.MOD_ID, "packet_generate_structure");
	Identifier PACKET_OPEN_ROCKET_CALLER_SCREEN = new Identifier(Main.MOD_ID, "packet_open_rocket_caller_screen");
	Identifier PACKET_LAUNCH_ROCKET = new Identifier(Main.MOD_ID, "packet_launch_rocket");

	int MAX_PACKET_BYTES = 1048576;
}
