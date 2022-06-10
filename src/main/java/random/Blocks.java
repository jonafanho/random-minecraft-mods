package random;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.TntBlock;
import random.block.BlockBigExplosive;
import random.block.BlockFakeTnt;
import random.block.BlockItemStructurifier;
import random.block.BlockNuke;

public interface Blocks {

	Block ITEM_STRUCTURIFIER = new BlockItemStructurifier(FabricBlockSettings.of(Material.METAL));
	Block LAVA_CUBE = new TntBlock(FabricBlockSettings.of(Material.METAL));
	Block CLASSIC_EXPLOSIVE = new TntBlock(FabricBlockSettings.of(Material.METAL));
	Block BIG_EXPLOSIVE = new BlockBigExplosive(FabricBlockSettings.of(Material.METAL));
	Block NUKE = new BlockNuke(FabricBlockSettings.of(Material.METAL));
	Block FAKE_TNT = new BlockFakeTnt(FabricBlockSettings.of(Material.METAL));
	Block YELLOW_TNT = new Block(FabricBlockSettings.of(Material.METAL));
	Block IRON_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.METAL));
	Block GOLDEN_MOSSY_COBBLESTONE = new Block(FabricBlockSettings.of(Material.METAL));
	Block LILY_PAD_DECO = new Block(FabricBlockSettings.of(Material.METAL));
}
