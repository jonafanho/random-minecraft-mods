package random;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import random.packet.IPacket;
import random.packet.PacketTrainDataGuiClient;
import random.screen.ItemStructurifierScreen;

public class MainClient implements ClientModInitializer, IPacket {

	@Override
	public void onInitializeClient() {
		ScreenRegistry.register(ScreenHandlers.ITEM_STRUCTURIFIER_SCREEN_HANDLER, ItemStructurifierScreen::new);
		ClientPlayNetworking.registerGlobalReceiver(PACKET_OPEN_ITEM_STRUCTURIFIER_SCREEN, (minecraftClient, handler, packet, sender) -> PacketTrainDataGuiClient.openItemStructurifierScreenS2C(minecraftClient, packet));
	}
}
