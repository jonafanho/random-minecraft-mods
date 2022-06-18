package random.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import random.Commands;

import java.util.Random;

public class EntitySnowYeti extends EntityBossBarBase implements RangedAttackMob {

	public EntitySnowYeti(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {
		final EntityHardSnowball hardSnowball = new EntityHardSnowball(world, this);
		final double x = target.getX() - getX();
		final double y = target.getEyeY() - 1 - hardSnowball.getY();
		final double z = target.getZ() - getZ();
		final double offset = Math.sqrt(x * x + z * z) / 5;
		hardSnowball.setVelocity(x, y + offset, z, 1.6F, 2);
		playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1, 0.4F / (getRandom().nextFloat() * 0.4F + 0.8F));
		world.spawnEntity(hardSnowball);
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		if (!world.isClient) {
			final BlockState blockState = Blocks.SNOW.getDefaultState();
			if (world.getBlockState(getBlockPos()).isAir() && blockState.canPlaceAt(world, getBlockPos())) {
				world.setBlockState(getBlockPos(), blockState);
			}
			if (new Random().nextInt(100) == 0) {
				Commands.iterateInRadius(getBlockPos(), 4, breakPos -> {
					if (breakPos.getY() >= getY()) {
						world.breakBlock(breakPos, false, this);
					}
				});
			}
		}
	}

	@Override
	protected void initGoals() {
		goalSelector.add(1, new ProjectileAttackGoal(this, 1.25, 20, 10));
		goalSelector.add(1, new SwimGoal(this));
		goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
		goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(6, new LookAroundGoal(this));
		targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
		targetSelector.add(2, new RevengeGoal(this));
	}

	public static DefaultAttributeContainer.Builder createSnowYetiAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
	}
}
