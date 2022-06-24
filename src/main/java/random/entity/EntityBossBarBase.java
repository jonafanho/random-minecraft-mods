package random.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public abstract class EntityBossBarBase extends HostileEntity {

	private final ServerBossBar bossBar;

	public EntityBossBarBase(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		bossBar = new ServerBossBar(getDisplayName(), BossBar.Color.WHITE, BossBar.Style.PROGRESS);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (hasCustomName()) {
			bossBar.setName(getDisplayName());
		}
	}

	@Override
	public void setCustomName(Text name) {
		super.setCustomName(name);
		bossBar.setName(getDisplayName());
	}

	@Override
	protected void mobTick() {
		super.mobTick();
		bossBar.setPercent(getHealth() / getMaxHealth());
	}

	@Override
	public void onStartedTrackingBy(ServerPlayerEntity player) {
		super.onStartedTrackingBy(player);
		bossBar.addPlayer(player);
	}

	@Override
	public void onStoppedTrackingBy(ServerPlayerEntity player) {
		super.onStoppedTrackingBy(player);
		bossBar.removePlayer(player);
	}
}
