package random;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import random.block.BlockIronEndPortal;

public interface BlockEntityTypes {

	BlockEntityType<BlockIronEndPortal.BlockEntity> IRON_END_PORTAL = FabricBlockEntityTypeBuilder.create(BlockIronEndPortal.BlockEntity::new, Blocks.IRON_END_PORTAL).build(null);
}
