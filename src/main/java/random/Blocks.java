package random;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import random.block.BlockItemStructurifier;

public interface Blocks {

	Block ITEM_STRUCTURIFIER = new BlockItemStructurifier(FabricBlockSettings.of(Material.METAL).strength(2));
}
