package random.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import random.Fluids;
import random.Items;

public abstract class FluidBreadLiquid extends WaterFluid {

	@Override
	public Fluid getStill() {
		return Fluids.BREAD_LIQUID_STILL;
	}

	@Override
	public Fluid getFlowing() {
		return Fluids.BREAD_LIQUID_FLOWING;
	}

	@Override
	public Item getBucketItem() {
		return Items.BUCKET_BREAD_LIQUID;
	}

	@Override
	public BlockState toBlockState(FluidState fluidState) {
		return Fluids.BREAD_LIQUID.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
	}

	@Override
	public boolean matchesType(Fluid fluid) {
		return fluid == Fluids.BREAD_LIQUID_STILL || fluid == Fluids.BREAD_LIQUID_FLOWING;
	}

	public static class Flowing extends FluidBreadLiquid {

		@Override
		protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
			super.appendProperties(builder);
			builder.add(LEVEL);
		}

		@Override
		public int getLevel(FluidState fluidState) {
			return fluidState.get(LEVEL);
		}

		@Override
		public boolean isStill(FluidState fluidState) {
			return false;
		}
	}

	public static class Still extends FluidBreadLiquid {

		@Override
		public int getLevel(FluidState fluidState) {
			return 8;
		}

		@Override
		public boolean isStill(FluidState fluidState) {
			return true;
		}
	}
}
