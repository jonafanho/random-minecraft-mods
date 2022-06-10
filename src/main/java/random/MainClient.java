package random;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import random.packet.IPacket;
import random.screen.ItemStructurifierScreen;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MainClient implements ClientModInitializer, IPacket {

	private static final Set<UUID> LAVA_BOSSES = new HashSet<>();
	private static final Set<UUID> BREADS = new HashSet<>();

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(EntityTypes.STONE_VILLAGER, VillagerEntityRenderer::new);

		ScreenRegistry.register(ScreenHandlers.ITEM_STRUCTURIFIER_SCREEN_HANDLER, ItemStructurifierScreen::new);
		ClientPlayNetworking.registerGlobalReceiver(PACKET_TOGGLE_LAVA_BOSS, (minecraftClient, handler, packet, sender) -> toggleLavaBossS2C(minecraftClient, packet));

		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
			registry.register(new Identifier(Main.MOD_ID, "particle/rainbow"));
		});

		ParticleFactoryRegistry.getInstance().register(Particles.RAINBOW, FlameParticle.Factory::new);

		FluidRenderHandlerRegistry.INSTANCE.register(Fluids.BREAD_LIQUID_STILL, Fluids.BREAD_LIQUID_FLOWING, new SimpleFluidRenderHandler(new Identifier("block/water_still"), new Identifier("block/water_flow"), 0xFFFFFF));
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), Fluids.BREAD_LIQUID_STILL, Fluids.BREAD_LIQUID_FLOWING);
	}

	public static boolean isLavaBoss(AbstractClientPlayerEntity player) {
		return LAVA_BOSSES.contains(player.getUuid());
	}

	public static boolean isBread(AbstractClientPlayerEntity player) {
		return BREADS.contains(player.getUuid());
	}

	private static void toggleLavaBossS2C(MinecraftClient minecraftClient, PacketByteBuf packet) {
		final UUID uuid = packet.readUuid();
		final boolean isLavaBoss = packet.readBoolean();
		minecraftClient.execute(() -> {
			if (isLavaBoss) {
				LAVA_BOSSES.add(uuid);
			} else {
				LAVA_BOSSES.remove(uuid);
			}
		});
	}
}
