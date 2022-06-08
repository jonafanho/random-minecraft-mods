package random;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import random.packet.IPacket;
import random.packet.PacketTrainDataGuiClient;
import random.screen.ItemStructurifierScreen;

public class MainClient implements ClientModInitializer, IPacket {

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(EntityTypes.STONE_VILLAGER, VillagerEntityRenderer::new);

		ScreenRegistry.register(ScreenHandlers.ITEM_STRUCTURIFIER_SCREEN_HANDLER, ItemStructurifierScreen::new);
		ClientPlayNetworking.registerGlobalReceiver(PACKET_OPEN_ITEM_STRUCTURIFIER_SCREEN, (minecraftClient, handler, packet, sender) -> PacketTrainDataGuiClient.openItemStructurifierScreenS2C(minecraftClient, packet));

		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
			registry.register(new Identifier(Main.MOD_ID, "particle/rainbow"));
		});

		ParticleFactoryRegistry.getInstance().register(Particles.RAINBOW, FlameParticle.Factory::new);
	}
}
