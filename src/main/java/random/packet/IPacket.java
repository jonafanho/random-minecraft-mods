package random.packet;

import net.minecraft.util.Identifier;
import random.Main;

public interface IPacket {

	Identifier PACKET_OPEN_ITEM_STRUCTURIFIER_SCREEN = new Identifier(Main.MOD_ID, "packet_open_item_structurifier_screen");

	int MAX_PACKET_BYTES = 1048576;
}
