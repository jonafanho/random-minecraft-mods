package random.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import random.Particles;

public class ItemRainbowWoolAxe extends AxeItem {

	public ItemRainbowWoolAxe(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (target.world instanceof ServerWorld) {
			((ServerWorld) target.world).spawnParticles(Particles.RAINBOW, target.getX(), target.getY() + 1, target.getZ(), 100, 0, 1, 0, 0.1);
		}
		target.addVelocity(0, 0.5, 0);
		return super.postHit(stack, target, attacker);
	}
}
