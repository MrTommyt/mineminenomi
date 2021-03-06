package xyz.pixelatedw.mineminenomi.models.entities.zoans;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import xyz.pixelatedw.mineminenomi.api.ZoanMorphModel;

public class ZouHeavyModel<T extends LivingEntity> extends ZoanMorphModel<T> implements IHasArm
{
	public RendererModel body1;
	public RendererModel body2;
	public RendererModel rightleg1;
	public RendererModel rightleg2;
	public RendererModel leftleg1;
	public RendererModel leftleg2;
	public RendererModel rightarm1;
	public RendererModel rightarm2;
	public RendererModel leftarm1;
	public RendererModel leftarm2;
	public RendererModel tail1;
	public RendererModel tail2;
	public RendererModel head;
	public RendererModel rightear;
	public RendererModel leftear;
	public RendererModel snout;
	public RendererModel snout2;
	public RendererModel snout3;
	public RendererModel tuskA1;
	public RendererModel tuskA2;
	public RendererModel tuskB1;
	public RendererModel tuskB2;

	public ZouHeavyModel()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.leftleg1 = new RendererModel(this, 42, 46);
		this.leftleg1.setRotationPoint(5.0F, 11.100000381469727F, 1.0F);
		this.leftleg1.addBox(-2.5F, 0.0F, -2.5F, 5, 7, 5, 0.0F);
		this.setRotateAngle(leftleg1, -0.05235987901687623F, -0.0F, 0.0F);
		this.body1 = new RendererModel(this, 35, 0);
		this.body1.setRotationPoint(0.0F, -2.5999999046325684F, 2.700000047683716F);
		this.body1.addBox(-9.0F, -10.0F, -7.0F, 18, 14, 10, 0.0F);
		this.setRotateAngle(body1, 0.08726646006107329F, 0.0F, 0.0F);
		this.snout = new RendererModel(this, 112, 35);
		this.snout.setRotationPoint(-2.0F, -1.5F, -4.5F);
		this.snout.addBox(0.0F, 0.0F, 0.0F, 4, 7, 4, 0.0F);
		this.setRotateAngle(snout, -0.20943951023931953F, -0.0F, 0.0F);
		this.head = new RendererModel(this, 0, 0);
		this.head.setRotationPoint(0.0F, -12.199999809265137F, -3.0F);
		this.head.addBox(-4.0F, -9.0F, -4.5F, 8, 9, 9, 0.0F);
		this.setRotateAngle(head, 0.05235987901687623F, -0.0F, 0.0F);
		this.tail2 = new RendererModel(this, 119, 0);
		this.tail2.setRotationPoint(0.0F, 5.5F, 0.0F);
		this.tail2.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
		this.leftear = new RendererModel(this, 0, 19);
		this.leftear.mirror = true;
		this.leftear.setRotationPoint(3.5F, -3.8F, -0.0F);
		this.leftear.addBox(0.0F, -6.0F, 0.0F, 6, 8, 1, 0.0F);
		this.setRotateAngle(leftear, -0.23649211364523165F, -1.0306169233026516F, 0.27401669256310973F);
		this.tuskB1 = new RendererModel(this, 15, 19);
		this.tuskB1.setRotationPoint(2.3F, -1.0F, -3.5F);
		this.tuskB1.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(tuskB1, -0.3490658503988659F, -0.17453292519943295F, 0.0F);
		this.rightarm1 = new RendererModel(this, 0, 46);
		this.rightarm1.setRotationPoint(-9.0F, -9.600000381469727F, 1.0F);
		this.rightarm1.addBox(-5.0F, -2.0F, -2.5F, 5, 10, 5, 0.0F);
		this.setRotateAngle(rightarm1, 0.0F, -0.0F, 0.03490658476948738F);
		this.tuskA1 = new RendererModel(this, 15, 19);
		this.tuskA1.setRotationPoint(-2.3F, -1.0F, -3.5F);
		this.tuskA1.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(tuskA1, -0.3490658503988659F, 0.17453292519943295F, 0.0F);
		this.rightleg1 = new RendererModel(this, 42, 46);
		this.rightleg1.setRotationPoint(-5.0F, 11.100000381469727F, 1.0F);
		this.rightleg1.addBox(-2.5F, 0.0F, -2.5F, 5, 7, 5, 0.0F);
		this.setRotateAngle(rightleg1, -0.05235987901687623F, -0.0F, 0.0F);
		this.leftarm1 = new RendererModel(this, 0, 46);
		this.leftarm1.setRotationPoint(9.0F, -9.600000381469727F, 1.0F);
		this.leftarm1.addBox(0.0F, -2.0F, -2.5F, 5, 10, 5, 0.0F);
		this.setRotateAngle(leftarm1, 0.0F, -0.0F, -0.03490658476948738F);
		this.snout2 = new RendererModel(this, 116, 47);
		this.snout2.setRotationPoint(0.5F, 7.0F, 0.5F);
		this.snout2.addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, 0.0F);
		this.setRotateAngle(snout2, 0.20943951023931953F, -0.0F, 0.0F);
		this.rightarm2 = new RendererModel(this, 21, 46);
		this.rightarm2.setRotationPoint(-2.5F, 7.8F, 0.0F);
		this.rightarm2.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
		this.setRotateAngle(rightarm2, 0.0F, -0.0F, -0.03490658503988659F);
		this.tuskB2 = new RendererModel(this, 15, 25);
		this.tuskB2.setRotationPoint(0.0F, 3.8F, 0.0F);
		this.tuskB2.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(tuskB2, -0.17453292519943295F, -0.017453292519943295F, 0.0F);
		this.rightleg2 = new RendererModel(this, 63, 46);
		this.rightleg2.setRotationPoint(0.0F, 6.8F, 0.0F);
		this.rightleg2.addBox(-2.5F, 0.0F, -2.5F, 5, 6, 5, 0.0F);
		this.setRotateAngle(rightleg2, 0.05235987755982988F, -0.0F, 0.0F);
		this.tuskA2 = new RendererModel(this, 15, 25);
		this.tuskA2.setRotationPoint(0.0F, 3.9F, 0.0F);
		this.tuskA2.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(tuskA2, -0.17453292519943295F, 0.017453292519943295F, 0.0F);
		this.leftarm2 = new RendererModel(this, 21, 46);
		this.leftarm2.setRotationPoint(2.5F, 7.8F, 0.0F);
		this.leftarm2.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
		this.setRotateAngle(leftarm2, 0.0F, -0.0F, 0.03490658503988659F);
		this.tail1 = new RendererModel(this, 114, 0);
		this.tail1.setRotationPoint(0.0F, -2.5F, 2.0F);
		this.tail1.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
		this.setRotateAngle(tail1, 0.4363323129985824F, -0.0F, 0.0F);
		this.body2 = new RendererModel(this, 35, 25);
		this.body2.setRotationPoint(0.0F, 11.199999809265137F, 3.0F);
		this.body2.addBox(-8.5F, -10.0F, -7.0F, 17, 10, 10, 0.0F);
		this.snout3 = new RendererModel(this, 120, 57);
		this.snout3.setRotationPoint(0.5F, 5.5F, 0.5F);
		this.snout3.addBox(0.0F, 0.0F, 0.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(snout3, 0.20943951023931953F, -0.0F, 0.0F);
		this.leftleg2 = new RendererModel(this, 63, 46);
		this.leftleg2.setRotationPoint(0.0F, 6.8F, 0.0F);
		this.leftleg2.addBox(-2.5F, 0.0F, -2.5F, 5, 6, 5, 0.0F);
		this.setRotateAngle(leftleg2, 0.05235987755982988F, -0.0F, 0.0F);
		this.rightear = new RendererModel(this, 0, 19);
		this.rightear.setRotationPoint(-3.5F, -3.8F, -1.0F);
		this.rightear.addBox(-6.0F, -6.0F, 0.0F, 6, 8, 1, 0.0F);
		this.setRotateAngle(rightear, -0.23649211364523165F, 1.0306169233026516F, -0.27401669256310973F);
		this.head.addChild(this.snout);
		this.tail1.addChild(this.tail2);
		this.head.addChild(this.leftear);
		this.head.addChild(this.tuskB1);
		this.head.addChild(this.tuskA1);
		this.snout.addChild(this.snout2);
		this.rightarm1.addChild(this.rightarm2);
		this.tuskB1.addChild(this.tuskB2);
		this.rightleg1.addChild(this.rightleg2);
		this.tuskA1.addChild(this.tuskA2);
		this.leftarm1.addChild(this.leftarm2);
		this.body2.addChild(this.tail1);
		this.snout2.addChild(this.snout3);
		this.leftleg1.addChild(this.leftleg2);
		this.head.addChild(this.rightear);
	}

	@Override
	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		this.leftleg1.render(scale);
		this.body1.render(scale);
		this.head.render(scale);
		this.rightarm1.render(scale);
		this.rightleg1.render(scale);
		this.leftarm1.render(scale);
		this.body2.render(scale);
	}

	@Override
	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		// Handles the head movement when following the mouse or when swimming
		this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
		this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
		if (Math.toDegrees(this.head.rotateAngleX) > 35)
			this.head.rotateAngleX = (float) Math.toRadians(35);
		if (Math.toDegrees(this.head.rotateAngleX) < -45)
			this.head.rotateAngleX = (float) Math.toRadians(-45);
		
		// Hanldes the arm and leg movement
		float f = 1.0F;
		this.rightarm1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.8F * limbSwingAmount * 0.5F / f;
		this.leftarm1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount * 0.5F / f;
		this.rightleg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount / f;
		this.leftleg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.7F * limbSwingAmount / f;
		if(entity.isSprinting())
			this.tail1.rotateAngleX = 1.3F + MathHelper.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
		
		// Handles the punch and item use animations of the model
		this.swingProgress = entity.swingProgress;
		if (this.swingProgress > 0)
		{
			this.body1.rotateAngleY = MathHelper.sin(MathHelper.sqrt(this.swingProgress) * ((float) Math.PI * 2F)) * 0.2F;
			this.body2.rotateAngleY = MathHelper.sin(MathHelper.sqrt(this.swingProgress) * ((float) Math.PI * 2F)) * 0.2F;
			this.rightarm1.rotationPointZ = MathHelper.sin(this.body2.rotateAngleY) * 8.0F;
			this.rightarm1.rotationPointX = -MathHelper.cos(this.body2.rotateAngleY) * 8.0F;
			this.rightarm1.rotateAngleY += this.body2.rotateAngleY;
			this.leftarm1.rotateAngleY += this.body2.rotateAngleY;
			this.leftarm1.rotateAngleX += this.body2.rotateAngleY;
			float f1 = 1.0F - this.swingProgress;
			f1 = f1 * f1;
			f1 = f1 * f1;
			f1 = 1.0F - f1;
			float f2 = MathHelper.sin(f1 * (float) Math.PI);
			float f3 = MathHelper.sin(this.swingProgress * (float) Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
			this.rightarm1.rotateAngleX = (float) (this.rightarm1.rotateAngleX - (f2 * 1.2D + f3));
			this.rightarm1.rotateAngleY += this.body1.rotateAngleY * 2.0F;
			this.rightarm1.rotateAngleZ += MathHelper.sin(this.swingProgress * (float) Math.PI) * -0.4F;
		}

		// Handles the rotations and positions of individual cubes when sneaking or not
		if (entity.isSneaking())
		{
			this.body1.rotateAngleX = 0.5F;
			this.body1.rotationPointZ -= 4F;
			this.body2.rotateAngleX = 0.5F;
			this.body2.rotationPointZ += 2F;
			this.body2.rotationPointY -= 3F;
			this.rightarm1.rotateAngleX += 0.4F;
			this.rightarm1.rotationPointZ -= 8.5F;
			this.rightarm1.rotationPointY += 1F;
			this.leftarm1.rotateAngleX += 0.4F;
			this.leftarm1.rotationPointZ -= 8.5F;
			this.leftarm1.rotationPointY += 1F;
			this.rightleg1.rotationPointZ = 1.5F;
			this.leftleg1.rotationPointZ = 1.5F;
			this.rightleg1.rotationPointY = 9.0F;
			this.leftleg1.rotationPointY = 9.0F;
			this.head.rotationPointZ -= 7F;
			this.head.rotationPointY += 3F;
		}
	}
	
	public void setRotateAngle(RendererModel model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public RendererModel getHandRenderer()
	{
		GL11.glScaled(1.2, 1.2, 1);
		GL11.glTranslated(-0.1, -0.25, 0.05);
		GL11.glRotated(-5, 1, 0, 0);
		GL11.glRotated(1, 0, 0, 1);
		return this.rightarm2;
	}

	@Override
	public RendererModel getArmRenderer()
	{
		return this.rightarm1;
	}

	@Override
	public void postRenderArm(float scale, HandSide side)
	{
		
	}
}
