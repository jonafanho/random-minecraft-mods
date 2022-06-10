package random;

import com.mojang.brigadier.Command;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import random.entity.EntityStoneVillager;

import java.util.function.Consumer;

public interface Commands {

	Command<ServerCommandSource> STONE_VILLAGERS = context -> {
		final ServerCommandSource source = context.getSource();
		final ServerWorld world = source.getWorld();
		final ServerPlayerEntity player = source.getPlayer();
		for (int x = -1; x <= 1; x += 2) {
			for (int z = -1; z <= 1; z += 2) {
				final EntityStoneVillager stoneVillager = new EntityStoneVillager(EntityTypes.STONE_VILLAGER, world);
				stoneVillager.setPos(player.getX() + x, player.getY(), player.getZ() + z);
				world.spawnEntity(stoneVillager);
			}
		}
		return 0;
	};
	Command<ServerCommandSource> LAVA_BOSS = context -> {
		final ServerCommandSource source = context.getSource();
		Main.toggleLavaBoss(source.getWorld(), source.getPlayer());
		return 0;
	};
	Command<ServerCommandSource> LAVA_STONE = context -> {
		final ServerCommandSource source = context.getSource();
		final ServerWorld world = source.getWorld();
		iterateInRadius(source.getPlayer().getBlockPos(), 30, pos -> {
			if (world.getBlockState(pos).isFullCube(world, pos)) {
				world.setBlockState(pos, Blocks.LAVA_CUBE.getDefaultState());
			}
		});
		return 0;
	};
	Command<ServerCommandSource> BREAD_LIQUID = context -> {
		final ServerCommandSource source = context.getSource();
		final ServerWorld world = source.getWorld();
		iterateInRadius(source.getPlayer().getBlockPos(), 10, pos -> {
			final Fluid fluid = world.getFluidState(pos).getFluid();
			if (fluid instanceof WaterFluid.Still) {
				world.setBlockState(pos, Fluids.BREAD_LIQUID_STILL.getDefaultState().getBlockState());
			} else if (fluid instanceof WaterFluid.Flowing) {
				world.setBlockState(pos, Fluids.BREAD_LIQUID_FLOWING.getDefaultState().getBlockState());
			}
		});
		return 0;
	};
	Command<ServerCommandSource> FAKE_TNT = context -> {
		return 0;
	};
	Command<ServerCommandSource> TNT_2 = context -> {
		return 0;
	};
	Command<ServerCommandSource> OAK_YETI = context -> {
		return 0;
	};
	Command<ServerCommandSource> IRON_END = context -> {
		return 0;
	};
	Command<ServerCommandSource> IRON_BOSS = context -> {
		return 0;
	};
	Command<ServerCommandSource> IRON_BRICKS = context -> {
		return 0;
	};
	Command<ServerCommandSource> GOLD_MOSS = context -> {
		return 0;
	};
	Command<ServerCommandSource> LILY_PAD_BOSS = context -> {
		return 0;
	};
	Command<ServerCommandSource> FROGS = context -> {
		return 0;
	};
	Command<ServerCommandSource> GREEN_PAD = context -> {
		return 0;
	};
	Command<ServerCommandSource> DIAMOND_GUARDIAN = context -> {
		return 0;
	};
	Command<ServerCommandSource> DIAMOND_PORTAL = context -> {
		return 0;
	};
	Command<ServerCommandSource> DIRT_PENGUIN = context -> {
		return 0;
	};

	static void iterateInRadius(BlockPos pos, int radius, Consumer<BlockPos> consumer) {
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					final BlockPos newPos = pos.add(x, y, z);
					if (pos.getSquaredDistance(newPos) <= radius * radius) {
						consumer.accept(newPos);
					}
				}
			}
		}
	}
}
