package random.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import random.MainClient;

@Mixin(PlayerEntityRenderer.class)
public class PlayerRendererOffsetMixin {

	@Inject(method = "getPositionOffset(Lnet/minecraft/client/network/AbstractClientPlayerEntity;F)Lnet/minecraft/util/math/Vec3d;", at = @At(value = "RETURN"), cancellable = true)
	public void getRenderOffset(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, CallbackInfoReturnable<Vec3d> callbackInfoReturnable) {
		if (MainClient.isLavaBoss(abstractClientPlayerEntity) || MainClient.isBread(abstractClientPlayerEntity)) {
			callbackInfoReturnable.setReturnValue(new Vec3d(0, -1000, 0));
		}
	}

	@Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "HEAD"))
	public void render(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
		if (MainClient.isLavaBoss(abstractClientPlayerEntity)) {
			matrixStack.push();
			matrixStack.translate(0, 1000, 0);
			new MinecartEntityModel<MinecartEntity>(MinecartEntityModel.getTexturedModelData().createModel()).render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(new Identifier("textures/entity/minecart.png"))), i, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
			matrixStack.pop();
		}
	}
}
