package random.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import random.Blocks;

public class ItemNewEnderEye extends Item {

	private static final BlockPattern PATTERN = BlockPatternBuilder.start().aisle("?vvv?", ">???<", ">???<", ">???<", "?^^^?")
			.where('?', CachedBlockPosition.matchesBlockState(BlockStatePredicate.ANY))
			.where('^', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.IRON_END_PORTAL_FRAME).with(EndPortalFrameBlock.EYE, object -> (boolean) object).with(EndPortalFrameBlock.FACING, object -> object == Direction.SOUTH)))
			.where('>', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.IRON_END_PORTAL_FRAME).with(EndPortalFrameBlock.EYE, object -> (boolean) object).with(EndPortalFrameBlock.FACING, object -> object == Direction.WEST)))
			.where('v', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.IRON_END_PORTAL_FRAME).with(EndPortalFrameBlock.EYE, object -> (boolean) object).with(EndPortalFrameBlock.FACING, object -> object == Direction.NORTH)))
			.where('<', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.IRON_END_PORTAL_FRAME).with(EndPortalFrameBlock.EYE, object -> (boolean) object).with(EndPortalFrameBlock.FACING, object -> object == Direction.EAST)))
			.build();

	public ItemNewEnderEye(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		final World world = context.getWorld();
		if (world.isClient) {
			return ActionResult.SUCCESS;
		}

		final BlockPos blockPos = context.getBlockPos();
		final BlockState blockState = world.getBlockState(blockPos);

		if (!(blockState.getBlock() instanceof EndPortalFrameBlock) || blockState.get(EndPortalFrameBlock.EYE)) {
			return ActionResult.PASS;
		}

		final BlockState blockState2 = blockState.with(EndPortalFrameBlock.EYE, true);
		Block.pushEntitiesUpBeforeBlockChange(blockState, blockState2, world, blockPos);
		world.setBlockState(blockPos, blockState2, Block.NOTIFY_LISTENERS);
		world.updateComparators(blockPos, Blocks.IRON_END_PORTAL_FRAME);
		context.getStack().decrement(1);

		final BlockPattern.Result result = PATTERN.searchAround(world, blockPos);
		if (result != null) {
			final BlockPos blockPos2 = result.getFrontTopLeft().add(-3, 0, -3);
			for (int x = 0; x < 3; ++x) {
				for (int z = 0; z < 3; ++z) {
					world.setBlockState(blockPos2.add(x, 0, z), Blocks.IRON_END_PORTAL.getDefaultState(), Block.NOTIFY_LISTENERS);
				}
			}
			world.syncGlobalEvent(WorldEvents.END_PORTAL_OPENED, blockPos2.add(1, 0, 1), 0);
		}

		return ActionResult.CONSUME;
	}
}
