package random;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import random.entity.*;

public interface EntityTypes {

	EntityType<EntityStoneVillager> STONE_VILLAGER = Registry.register(Registry.ENTITY_TYPE, new Identifier(Main.MOD_ID, "stone_villager"), FabricEntityTypeBuilder.create(SpawnGroup.MISC, EntityStoneVillager::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).trackRangeChunks(10).build());
	EntityType<EntitySnowYeti> SNOW_YETI = Registry.register(Registry.ENTITY_TYPE, new Identifier(Main.MOD_ID, "snow_yeti"), FabricEntityTypeBuilder.create(SpawnGroup.MISC, EntitySnowYeti::new).dimensions(EntityDimensions.fixed(2, 2)).trackRangeChunks(10).build());
	EntityType<EntityRocket> ROCKET = Registry.register(Registry.ENTITY_TYPE, new Identifier(Main.MOD_ID, "rocket"), FabricEntityTypeBuilder.<EntityRocket>create(SpawnGroup.MISC, EntityRocket::new).dimensions(EntityDimensions.fixed(1, 1)).trackRangeChunks(10).build());
	EntityType<EntityHardSnowball> HARD_SNOWBALL = Registry.register(Registry.ENTITY_TYPE, new Identifier(Main.MOD_ID, "hard_snowball"), FabricEntityTypeBuilder.<EntityHardSnowball>create(SpawnGroup.MISC, EntityHardSnowball::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build());
	EntityType<EntityMegaIronGolem> MEGA_IRON_GOLEM = Registry.register(Registry.ENTITY_TYPE, new Identifier(Main.MOD_ID, "mega_iron_golem"), FabricEntityTypeBuilder.create(SpawnGroup.MISC, EntityMegaIronGolem::new).dimensions(EntityDimensions.fixed(2, 2)).trackRangeBlocks(4).trackRangeChunks(10).build());
}
