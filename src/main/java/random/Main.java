package random;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.boss.BossBarManager;
import net.minecraft.entity.boss.CommandBossBar;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import random.block.BlockExplosive;
import random.entity.EntitySnowYeti;
import random.entity.EntityStoneVillager;
import random.fluid.FluidBreadLiquid;
import random.packet.IPacket;
import random.packet.PacketTrainDataGuiServer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main implements ModInitializer, IPacket {

	private static CommandBossBar lavaBossBar;

	public static final Map<Item, Item> RECIPES = new HashMap<>();
	public static final Map<TntEntity, BlockExplosive.ExplosionDetails> EXPLOSIONS = new HashMap<>();

	private static final Set<ServerPlayerEntity> LAVA_BOSSES = new HashSet<>();
	private static final Set<ServerPlayerEntity> BREADS = new HashSet<>();
	private static final Map<ItemEntity, Integer> ITEM_ENTITY_COOL_DOWNS = new HashMap<>();
	private static final Map<ServerWorld, BlockPos> IRON_ENDS = new HashMap<>();

	static {
		RECIPES.put(net.minecraft.block.Blocks.WHITE_WOOL.asItem(), Items.STRUCTURE_WHITE_WOOL);
		RECIPES.put(net.minecraft.block.Blocks.COBBLESTONE.asItem(), Items.STRUCTURE_COBBLESTONE);
		RECIPES.put(net.minecraft.item.Items.LAVA_BUCKET, Items.STRUCTURE_LAVA);
		RECIPES.put(net.minecraft.item.Items.BREAD, Items.STRUCTURE_BREAD);
		RECIPES.put(net.minecraft.block.Blocks.TNT.asItem(), Items.STRUCTURE_TNT);
		RECIPES.put(net.minecraft.block.Blocks.OAK_LOG.asItem(), Items.STRUCTURE_OAK_LOG);
		RECIPES.put(net.minecraft.block.Blocks.IRON_ORE.asItem(), Items.STRUCTURE_IRON_ORE);
		RECIPES.put(net.minecraft.item.Items.GOLD_INGOT, Items.STRUCTURE_GOLD_INGOT);
		RECIPES.put(net.minecraft.block.Blocks.LILY_PAD.asItem(), Items.STRUCTURE_LILY_PAD);
		RECIPES.put(net.minecraft.item.Items.DIAMOND, Items.STRUCTURE_DIAMOND);
		RECIPES.put(net.minecraft.item.Items.DIAMOND_LEGGINGS, Items.STRUCTURE_DIAMOND_LEGGINGS);
		RECIPES.put(net.minecraft.block.Blocks.DIRT.asItem(), Items.STRUCTURE_DIRT);
	}

	public static final String MOD_ID = "random";

	@Override
	public void onInitialize() {
		registerItem("structure_white_wool", Items.STRUCTURE_WHITE_WOOL);
		registerItem("structure_cobblestone", Items.STRUCTURE_COBBLESTONE);
		registerItem("structure_lava", Items.STRUCTURE_LAVA);
		registerItem("structure_bread", Items.STRUCTURE_BREAD);
		registerItem("structure_tnt", Items.STRUCTURE_TNT);
		registerItem("structure_oak_log", Items.STRUCTURE_OAK_LOG);
		registerItem("structure_iron_ore", Items.STRUCTURE_IRON_ORE);
		registerItem("structure_gold_ingot", Items.STRUCTURE_GOLD_INGOT);
		registerItem("structure_lily_pad", Items.STRUCTURE_LILY_PAD);
		registerItem("structure_diamond", Items.STRUCTURE_DIAMOND);
		registerItem("structure_diamond_leggings", Items.STRUCTURE_DIAMOND_LEGGINGS);
		registerItem("structure_dirt", Items.STRUCTURE_DIRT);
		registerItem("rainbow_wool_helmet", Items.RAINBOW_WOOL_HELMET);
		registerItem("rainbow_wool_chestplate", Items.RAINBOW_WOOL_CHESTPLATE);
		registerItem("rainbow_wool_boots", Items.RAINBOW_WOOL_BOOTS);
		registerItem("rainbow_wool_fish", Items.RAINBOW_WOOL_FISH);
		registerItem("rainbow_wool_axe", Items.RAINBOW_WOOL_AXE);
		registerItem("rainbow_wool_trident", Items.RAINBOW_WOOL_TRIDENT);
		registerItem("fire", Items.FIRE);
		registerItem("bucket_bread_liquid", Items.BUCKET_BREAD_LIQUID);
		registerItem("rocket_caller", Items.ROCKET_CALLER);
		registerItem("new_ender_eye", Items.NEW_ENDER_EYE);
		registerItem("winter_coat", Items.WINTER_COAT);
		registerItem("ice_pants", Items.ICE_PANTS);

		registerBlock("item_structurifier", Blocks.ITEM_STRUCTURIFIER, ItemGroup.MISC);
		registerBlock("lava_cube", Blocks.LAVA_CUBE, ItemGroup.MISC);
		registerBlock("classic_explosive", Blocks.CLASSIC_EXPLOSIVE, ItemGroup.MISC);
		registerBlock("big_explosive", Blocks.BIG_EXPLOSIVE, ItemGroup.MISC);
		registerBlock("nuke", Blocks.NUKE, ItemGroup.MISC);
		registerBlock("fake_tnt", Blocks.FAKE_TNT, ItemGroup.MISC);
		registerBlock("yellow_tnt", Blocks.YELLOW_TNT, ItemGroup.MISC);
		registerBlock("iron_end_portal_frame", Blocks.IRON_END_PORTAL_FRAME, ItemGroup.MISC);
		registerBlock("iron_end_portal", Blocks.IRON_END_PORTAL);
		registerBlock("iron_stone_bricks", Blocks.IRON_STONE_BRICKS, ItemGroup.MISC);
		registerBlock("golden_mossy_cobblestone", Blocks.GOLDEN_MOSSY_COBBLESTONE, ItemGroup.MISC);
		registerBlock("lily_pad_deco", Blocks.LILY_PAD_DECO, ItemGroup.MISC);

		registerBlock("bread_liquid", Fluids.BREAD_LIQUID);
		registerFluid("bread_liquid_still", Fluids.BREAD_LIQUID_STILL);
		registerFluid("bread_liquid_flow", Fluids.BREAD_LIQUID_FLOWING);

		registerBlockEntityType("iron_end_portal", BlockEntityTypes.IRON_END_PORTAL);

		registerParticle("rainbow", Particles.RAINBOW);

		FabricDefaultAttributeRegistry.register(EntityTypes.STONE_VILLAGER, EntityStoneVillager.createVillagerAttributes());
		FabricDefaultAttributeRegistry.register(EntityTypes.SNOW_YETI, EntitySnowYeti.createSnowYetiAttributes());

		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			dispatcher.register(CommandManager.literal("stonevillagers").executes(Commands.STONE_VILLAGERS));
			dispatcher.register(CommandManager.literal("lavaboss").executes(Commands.LAVA_BOSS));
			dispatcher.register(CommandManager.literal("lavastone").executes(Commands.LAVA_STONE));
			dispatcher.register(CommandManager.literal("breadliquid").executes(Commands.BREAD_LIQUID));
			dispatcher.register(CommandManager.literal("faketnt").executes(Commands.FAKE_TNT));
			dispatcher.register(CommandManager.literal("tnt2").executes(Commands.TNT_2));
			dispatcher.register(CommandManager.literal("oakyeti").executes(Commands.OAK_YETI));
			dispatcher.register(CommandManager.literal("ironend").executes(Commands.IRON_END));
			dispatcher.register(CommandManager.literal("ironboss").executes(Commands.IRON_BOSS));
			dispatcher.register(CommandManager.literal("ironbricks").executes(Commands.IRON_BRICKS));
			dispatcher.register(CommandManager.literal("goldmoss").executes(Commands.GOLD_MOSS));
			dispatcher.register(CommandManager.literal("lilypadboss").executes(Commands.LILY_PAD_BOSS));
			dispatcher.register(CommandManager.literal("frogs").executes(Commands.FROGS));
			dispatcher.register(CommandManager.literal("greenpad").executes(Commands.GREEN_PAD));
			dispatcher.register(CommandManager.literal("diamondguardian").executes(Commands.DIAMOND_GUARDIAN));
			dispatcher.register(CommandManager.literal("diamondportal").executes(Commands.DIAMOND_PORTAL));
			dispatcher.register(CommandManager.literal("dirtpenguin").executes(Commands.DIRT_PENGUIN));
		});

		ServerPlayNetworking.registerGlobalReceiver(PACKET_GENERATE_STRUCTURE, (minecraftServer, player, handler, packet, sender) -> PacketTrainDataGuiServer.generateStructureC2S(minecraftServer, player, packet));

		ServerTickEvents.END_WORLD_TICK.register(world -> {
			world.getPlayers().forEach(player -> {
				if (LAVA_BOSSES.contains(player)) {
					world.spawnParticles(ParticleTypes.FLAME, player.getX(), player.getY(), player.getZ(), 10, 1, 1, 1, 0.01);
					final BlockPos pos = player.getBlockPos();
					if (world.getBlockState(pos.down()).hasSolidTopSurface(world, pos.down(), player)) {
						world.setBlockState(pos, net.minecraft.block.Blocks.FIRE.getDefaultState());
					}
				}

				final Fluid fluid1 = world.getFluidState(player.getBlockPos()).getFluid();
				final Fluid fluid2 = world.getFluidState(player.getBlockPos().up()).getFluid();
				if (fluid1 instanceof FluidBreadLiquid || fluid2 instanceof FluidBreadLiquid) {
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 10, 0, false, false, true));
				}
			});

			world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), itemEntity -> RECIPES.containsKey(itemEntity.getStack().getItem())).forEach(itemEntity -> {
				if (!ITEM_ENTITY_COOL_DOWNS.containsKey(itemEntity)) {
					ITEM_ENTITY_COOL_DOWNS.put(itemEntity, 40);
				}
				if (ITEM_ENTITY_COOL_DOWNS.get(itemEntity) <= 0) {
					world.setBlockState(itemEntity.getBlockPos(), net.minecraft.block.Blocks.IRON_BLOCK.getDefaultState());
					itemEntity.kill();
					ITEM_ENTITY_COOL_DOWNS.remove(itemEntity);
				} else {
					ITEM_ENTITY_COOL_DOWNS.put(itemEntity, ITEM_ENTITY_COOL_DOWNS.get(itemEntity) - 1);
				}
			});
		});
	}

	public static void toggleLavaBoss(ServerWorld world, ServerPlayerEntity player) {
		final boolean isLavaBoss = !LAVA_BOSSES.contains(player);
		if (isLavaBoss) {
			LAVA_BOSSES.add(player);
		} else {
			LAVA_BOSSES.remove(player);
		}

		PacketTrainDataGuiServer.toggleLavaBossS2C(player, isLavaBoss);

		final BossBarManager bossBarManager = world.getServer().getBossBarManager();
		if (lavaBossBar != null) {
			lavaBossBar.clearPlayers();
		}
		if (isLavaBoss) {
			lavaBossBar = bossBarManager.add(new Identifier(MOD_ID, "lava_boss_bar"), new TranslatableText("gui.random.lava_boss_bar"));
			lavaBossBar.addPlayers(world.getPlayers());
			lavaBossBar.setValue(lavaBossBar.getMaxValue());
		} else if (lavaBossBar != null) {
			bossBarManager.remove(lavaBossBar);
		}
	}

	public static BlockPos getIronEnd(ServerWorld world) {
		return IRON_ENDS.getOrDefault(world, null);
	}

	public static void setIronEnd(ServerWorld world, BlockPos pos) {
		IRON_ENDS.put(world, pos);
	}

	private static void registerItem(String path, Item item) {
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, path), item);
	}

	private static void registerBlock(String path, Block block) {
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, path), block);
	}

	private static void registerBlock(String path, Block block, ItemGroup itemGroup) {
		registerBlock(path, block);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, path), new BlockItem(block, new Item.Settings().group(itemGroup)));
	}

	private static void registerBlockEntityType(String path, BlockEntityType<? extends BlockEntity> blockEntityType) {
		Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, path), blockEntityType);
	}

	private static void registerFluid(String path, Fluid fluid) {
		Registry.register(Registry.FLUID, new Identifier(MOD_ID, path), fluid);
	}

	private static void registerParticle(String path, DefaultParticleType particle) {
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(MOD_ID, path), particle);
	}
}
