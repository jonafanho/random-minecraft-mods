package random.mixin;

import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import random.Commands;
import random.Main;

import java.util.Random;

@Mixin(TntEntity.class)
public class ExplosionMixin {

	@Inject(method = "explode", at = @At(value = "HEAD"), cancellable = true)
	private void explode(CallbackInfo ci) {
		final TntEntity tntEntity = ((TntEntity) (Object) this);
		if (Main.EXPLOSION_STRENGTH.containsKey(tntEntity)) {
			final World world = tntEntity.world;
			final BlockPos pos = tntEntity.getBlockPos();
			final int explosionStrength = Main.EXPLOSION_STRENGTH.get(tntEntity);

			Commands.iterateInRadius(pos, explosionStrength, newPos -> {
				if (!world.isAir(newPos) && world.isAir(newPos.up()) && world.getFluidState(newPos).isEmpty() && new Random().nextBoolean()) {
					final FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, newPos.up(), world.getBlockState(newPos));
					fallingBlockEntity.addVelocity((newPos.getX() - pos.getX()) / 4F, 2, (newPos.getZ() - pos.getZ()) / 4F);
				}
			});

//			world.createExplosion(tntEntity, tntEntity.getX(), tntEntity.getBodyY(0.0625), tntEntity.getZ(), Math.abs(explosionStrength), explosionStrength < 0, Explosion.DestructionType.BREAK);

			ci.cancel();
		}
	}
}
