package random.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import random.Main;

public class BlockExplosive extends TntBlock {

	private final int strength;
	private final boolean hasFire;

	public BlockExplosive(Settings settings, int strength, boolean hasFire) {
		super(settings);
		this.strength = strength;
		this.hasFire = hasFire;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);
		if (!world.isClient()) {
			final TntEntity tntEntity = new TntEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, null);
			world.spawnEntity(tntEntity);
			world.playSound(null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1, 1);
			world.emitGameEvent(null, GameEvent.PRIME_FUSE, pos);
			Main.EXPLOSION_STRENGTH.put(tntEntity, strength * (hasFire ? -1 : 1));
			world.removeBlock(pos, false);
		}
	}
}
