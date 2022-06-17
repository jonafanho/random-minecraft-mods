package random.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Random;

public class EntityStoneVillager extends VillagerEntity {

	private static final Item[] ITEMS = {
			Items.IRON_INGOT,
			Blocks.IRON_ORE.asItem(),
			Items.GOLD_INGOT,
			Blocks.GOLD_ORE.asItem(),
			Items.REDSTONE,
			Blocks.REDSTONE_ORE.asItem(),
			Items.LAPIS_LAZULI,
			Blocks.LAPIS_ORE.asItem(),
			Items.DIAMOND,
			Blocks.DIAMOND_ORE.asItem(),
	};

	public EntityStoneVillager(EntityType<? extends VillagerEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		final ItemEntity itemEntity = new ItemEntity(player.world, getX(), getY() + 1, getZ(), new ItemStack(ITEMS[new Random().nextInt(ITEMS.length)]));
		itemEntity.setVelocity(player.getPos().subtract(getPos()).multiply(0.1));
		world.spawnEntity(itemEntity);
		return ActionResult.SUCCESS;
	}
}
