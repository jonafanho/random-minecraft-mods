package random.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import random.packet.PacketTrainDataGuiServer;

public class ItemRocketCaller extends Item {

	public ItemRocketCaller(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (!world.isClient) {
			PacketTrainDataGuiServer.openRocketCallerScreenC2S((ServerPlayerEntity) player);
		}
		return super.use(world, player, hand);
	}
}
