package random;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import random.entity.EntityStoneVillager;

public interface EntityTypes {

	EntityType<EntityStoneVillager> STONE_VILLAGER = Registry.register(Registry.ENTITY_TYPE, new Identifier(Main.MOD_ID, "stone_villager"), FabricEntityTypeBuilder.create(SpawnGroup.MISC, EntityStoneVillager::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).build());
}
