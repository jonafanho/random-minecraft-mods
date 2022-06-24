package random.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import random.EntityTypes;
import random.entity.EntityRocket;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class EntitySpawnMixin {

	@Shadow
	private ClientWorld world;

	@Inject(method = "onEntitySpawn", at = @At("RETURN"))
	private void injectMethod(EntitySpawnS2CPacket packet, CallbackInfo ci) {
		final double x = packet.getX();
		final double y = packet.getY();
		final double z = packet.getZ();
		final EntityType<?> entityType = packet.getEntityTypeId();

		final Entity entity;
		if (entityType == EntityTypes.ROCKET) {
			entity = new EntityRocket(world, x, y, z, null);
		} else {
			entity = null;
		}

		if (entity != null) {
			entity.onSpawnPacket(packet);
			world.addEntity(packet.getId(), entity);
		}
	}
}
