package random;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import random.entity.EntityStoneVillager;

public class Main implements ModInitializer {

	public static final String MOD_ID = "random";

	@Override
	public void onInitialize() {
		registerItem("rainbow_wool_helmet", Items.RAINBOW_WOOL_HELMET);
		registerItem("rainbow_wool_chestplate", Items.RAINBOW_WOOL_CHESTPLATE);
		registerItem("rainbow_wool_boots", Items.RAINBOW_WOOL_BOOTS);
		registerItem("rainbow_wool_fish", Items.RAINBOW_WOOL_FISH);
		registerItem("rainbow_wool_axe", Items.RAINBOW_WOOL_AXE);
		registerItem("rainbow_wool_trident", Items.RAINBOW_WOOL_TRIDENT);

		registerBlock("item_structurifier", Blocks.ITEM_STRUCTURIFIER, ItemGroup.MISC);

		registerParticle("rainbow", Particles.RAINBOW);

		FabricDefaultAttributeRegistry.register(EntityTypes.STONE_VILLAGER, EntityStoneVillager.createVillagerAttributes());
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			dispatcher.register(CommandManager.literal("stonevillagers").executes(Commands.STONE_VILLAGERS));
		});
	}

	private static void registerItem(String path, Item item) {
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, path), item);
	}

	private static void registerBlock(String path, Block block) {
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, path), block);
	}

	private static void registerBlock(String path, Block block, ItemGroup itemGroup) {
		registerBlock(path, block);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, path), new BlockItem(block, new Item.Settings().group(itemGroup)));
	}

	private static void registerParticle(String path, DefaultParticleType particle) {
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(MOD_ID, path), particle);
	}
}
