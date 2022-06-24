package random.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class EntityMegaIronGolem extends EntityBossBarBase {

	public EntityMegaIronGolem(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public boolean tryAttack(Entity target) {
		world.sendEntityStatus(this, (byte) 4);
		final boolean attacked = target.damage(DamageSource.mob(this), 0.1F);
		if (attacked) {
			target.setVelocity(target.getVelocity().add(0, 4, 0));
			applyDamageEffects(this, target);
			if (target instanceof LivingEntity) {
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 256, false, false, false));
			}
		}
		playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1, 1);
		return attacked;
	}

	@Override
	protected void initGoals() {
		goalSelector.add(1, new AttackGoal(this));
		goalSelector.add(1, new SwimGoal(this));
		goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
		goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(6, new LookAroundGoal(this));
		targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
		targetSelector.add(2, new RevengeGoal(this));
	}

	public static DefaultAttributeContainer.Builder createMegaIronGolemAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 60).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
	}
}
