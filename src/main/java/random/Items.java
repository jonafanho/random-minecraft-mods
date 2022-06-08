package random;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import random.item.ItemRainbowWoolAxe;
import random.item.ItemRainbowWoolBoots;
import random.item.ItemRainbowWoolTrident;

public interface Items {

	Item RAINBOW_WOOL_HELMET = new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.HEAD, (new Item.Settings()).group(ItemGroup.COMBAT));
	Item RAINBOW_WOOL_CHESTPLATE = new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.CHEST, (new Item.Settings()).group(ItemGroup.COMBAT));
	Item RAINBOW_WOOL_BOOTS = new ItemRainbowWoolBoots(ArmorMaterials.IRON, EquipmentSlot.FEET, (new Item.Settings()).group(ItemGroup.COMBAT));
	Item RAINBOW_WOOL_FISH = new Item((new Item.Settings()).group(ItemGroup.FOOD).food(FoodComponents.COOKED_SALMON));
	Item RAINBOW_WOOL_AXE = new ItemRainbowWoolAxe(ToolMaterials.IRON, 6.0F, -3.1F, (new Item.Settings()).group(ItemGroup.TOOLS));
	Item RAINBOW_WOOL_TRIDENT = new ItemRainbowWoolTrident((new Item.Settings()).maxDamage(250).group(ItemGroup.COMBAT));
}
