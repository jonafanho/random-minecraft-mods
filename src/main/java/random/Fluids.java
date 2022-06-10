package random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import random.fluid.FluidBreadLiquid;

public interface Fluids {

	Fluid BREAD_LIQUID_STILL = new FluidBreadLiquid.Still();
	Fluid BREAD_LIQUID_FLOWING = new FluidBreadLiquid.Flowing();
	FluidBlock BREAD_LIQUID = new FluidBlock((FlowableFluid) BREAD_LIQUID_STILL, AbstractBlock.Settings.copy(Blocks.WATER));
}
