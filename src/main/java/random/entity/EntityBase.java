package random.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.world.World;

public abstract class EntityBase extends Entity {

	private int clientInterpolationSteps;
	private double clientX;
	private double clientY;
	private double clientZ;
	private double speedX;
	private double speedY;
	private double speedZ;

	public EntityBase(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
		clientX = x;
		clientY = y;
		clientZ = z;
		clientInterpolationSteps = interpolationSteps;
		setVelocity(speedX, speedY, speedZ);
	}

	@Override
	public void setVelocityClient(double x, double y, double z) {
		speedX = x;
		speedY = y;
		speedZ = z;
		setVelocity(speedX, speedY, speedZ);
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return new EntitySpawnS2CPacket(this);
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
	}

	protected final void setClientPosition() {
		if (clientInterpolationSteps > 0) {
			final double x = getX() + (clientX - getX()) / clientInterpolationSteps;
			final double y = getY() + (clientY - getY()) / clientInterpolationSteps;
			final double z = getZ() + (clientZ - getZ()) / clientInterpolationSteps;
			--clientInterpolationSteps;
			setPos(x, y, z);
		} else {
			refreshPosition();
		}
	}
}
