package random.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.BlockEntityTypes;
import random.Main;

public class BlockIronEndPortal extends EndPortalBlock {

	public BlockIronEndPortal(Settings settings) {
		super(settings);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (world instanceof ServerWorld) {
			final BlockPos newPos = Main.getIronEnd((ServerWorld) world);
			if (newPos == null) {
				if (entity instanceof PlayerEntity) {
					((PlayerEntity) entity).sendMessage(new TranslatableText("gui.random.iron_end_portal_cannot_send"), true);
				}
			} else {
				entity.teleport(newPos.getX(), newPos.getY(), newPos.getZ());
			}
		}
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new BlockEntity(pos, state);
	}

	public static class BlockEntity extends EndPortalBlockEntity {

		public BlockEntity(BlockPos pos, BlockState state) {
			super(BlockEntityTypes.IRON_END_PORTAL, pos, state);
		}
	}
}
