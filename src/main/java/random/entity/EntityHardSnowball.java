package random.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import random.EntityTypes;

public class EntityHardSnowball extends ThrownItemEntity {

	public EntityHardSnowball(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}

	public EntityHardSnowball(World world, LivingEntity owner) {
		super(EntityTypes.HARD_SNOWBALL, owner, world);
	}

	@Override
	protected Item getDefaultItem() {
		return Items.SNOWBALL;
	}

	private ParticleEffect getParticleParameters() {
		final ItemStack itemStack = getItem();
		return itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
	}

	@Override
	public void handleStatus(byte status) {
		if (status == 3) {
			final ParticleEffect particleEffect = getParticleParameters();
			for (int i = 0; i < 8; ++i) {
				world.addParticle(particleEffect, getX(), getY(), getZ(), 0, 0, 0);
			}
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		final Entity entity = entityHitResult.getEntity();
		entity.damage(DamageSource.thrownProjectile(this, getOwner()), 2);
		entity.setFrozenTicks(160);
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!world.isClient) {
			world.sendEntityStatus(this, (byte) 3);
			discard();
		}
	}
}
