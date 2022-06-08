package random.item;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.world.World;

public class ItemRainbowWoolTrident extends TridentItem {

	public ItemRainbowWoolTrident(Settings settings) {
		super(settings);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		stack.getEnchantments().clear();
		stack.addEnchantment(Enchantments.LOYALTY, 1);
		super.inventoryTick(stack, world, entity, slot, selected);
	}
}
