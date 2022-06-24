package random.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.util.Identifier;
import random.entity.EntityRocket;

public class RenderRocket extends EntityRenderer<EntityRocket> {

	public RenderRocket(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	@Override
	public void render(EntityRocket entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		new MinecartEntityModel<MinecartEntity>(MinecartEntityModel.getTexturedModelData().createModel()).render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(new Identifier("textures/entity/minecart.png"))), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
	}

	@Override
	public Identifier getTexture(EntityRocket entity) {
		return null;
	}
}
