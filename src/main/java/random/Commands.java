package random;

import com.mojang.brigadier.Command;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.world.World;
import random.entity.EntityStoneVillager;

public interface Commands {

	Command<ServerCommandSource> STONE_VILLAGERS = context -> {
		final ServerCommandSource source = context.getSource();
		final World world = source.getWorld();
		final PlayerEntity player = source.getPlayer();
		for (int x = -1; x <= 1; x += 2) {
			for (int z = -1; z <= 1; z += 2) {
				final EntityStoneVillager stoneVillager = new EntityStoneVillager(EntityTypes.STONE_VILLAGER, world);
				stoneVillager.setPos(player.getX() + x, player.getY(), player.getZ() + z);
				world.spawnEntity(stoneVillager);
			}
		}
		return 0;
	};
}
