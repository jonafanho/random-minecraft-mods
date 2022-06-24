package random.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import random.EntityTypes;

public class EntityRocket extends EntityBase {

	private final BlockPos goalPos;

	public EntityRocket(EntityType<EntityRocket> entityType, World world) {
		this(entityType, world, null);
	}

	public EntityRocket(World world, double x, double y, double z, BlockPos goalPos) {
		this(EntityTypes.ROCKET, world, goalPos);
		setPosition(x, y, z);
		prevX = x;
		prevY = y;
		prevZ = z;
	}

	private EntityRocket(EntityType<?> entityType, World world, BlockPos goalPos) {
		super(entityType, world);
		this.goalPos = goalPos;
	}

	@Override
	public void tick() {
		if (world.isClient) {
			setClientPosition();
		} else {
			if (goalPos != null) {
				final Vec3i vec3i = goalPos.subtract(getBlockPos());
				final Vec3d vec3d = new Vec3d(vec3i.getX(), vec3i.getY(), vec3i.getZ());
				if (vec3d.lengthSquared() < 4) {
					world.createExplosion(null, goalPos.getX(), goalPos.getY(), goalPos.getZ(), 50, Explosion.DestructionType.BREAK);
					kill();
				} else {
					setPosition(getPos().add(vec3d.normalize()));
				}
			}
		}
	}

	@Override
	protected void initDataTracker() {
	}
}
