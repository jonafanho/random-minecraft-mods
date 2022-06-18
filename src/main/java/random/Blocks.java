package random;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.Material;
import random.block.BlockExplosive;
import random.block.BlockFakeTnt;
import random.block.BlockIronEndPortal;
import random.block.BlockItemStructurifier;

public interface Blocks {

	Block ITEM_STRUCTURIFIER = new BlockItemStructurifier(FabricBlockSettings.of(Material.METAL));
	Block LAVA_CUBE = new Block(FabricBlockSettings.of(Material.METAL));
	Block CLASSIC_EXPLOSIVE = new BlockExplosive(FabricBlockSettings.of(Material.METAL), 4, false, false);
	Block BIG_EXPLOSIVE = new BlockExplosive(FabricBlockSettings.of(Material.METAL), 16, false, true);
	Block NUKE = new BlockExplosive(FabricBlockSettings.of(Material.METAL), 40, true, true);
	Block FAKE_TNT = new BlockFakeTnt(FabricBlockSettings.of(Material.METAL));
	Block YELLOW_TNT = new Block(FabricBlockSettings.of(Material.METAL));
	Block IRON_END_PORTAL_FRAME = new EndPortalFrameBlock(FabricBlockSettings.of(Material.METAL));
	Block IRON_END_PORTAL = new BlockIronEndPortal(FabricBlockSettings.of(Material.METAL));
	Block IRON_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.METAL));
	Block GOLDEN_MOSSY_COBBLESTONE = new Block(FabricBlockSettings.of(Material.METAL));
	Block LILY_PAD_DECO = new Block(FabricBlockSettings.of(Material.METAL));
}
