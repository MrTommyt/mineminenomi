package xyz.pixelatedw.mineminenomi.events.passives;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.wypi.APIConfig;
import xyz.pixelatedw.wypi.data.ability.AbilityDataCapability;
import xyz.pixelatedw.wypi.data.ability.IAbilityData;

@Mod.EventBusSubscriber(modid = APIConfig.PROJECT_ID)
public class MaguPassiveEvents
{

	@SubscribeEvent
	public static void onEntityUpdate(LivingUpdateEvent event)
	{
		if (!(event.getEntityLiving() instanceof PlayerEntity))
			return;	
		
		PlayerEntity player = (PlayerEntity) event.getEntityLiving();
		IDevilFruit devilFruitProps = DevilFruitCapability.get(player);

		if (!devilFruitProps.getDevilFruit().equals("magu_magu"))
			return;

		Vec3d vec3d = player.getMotion();
		IAbilityData abilityProps = AbilityDataCapability.get(player);
		
		if (player.isInLava() && !player.abilities.isFlying)
			player.setMotion(vec3d.mul(1.9F, 1.2F, 1.9F));
	}
	
	@SubscribeEvent
	public static void onEntityAttackEvent(LivingAttackEvent event)
	{
		if (!(event.getEntityLiving() instanceof PlayerEntity))
			return;

		PlayerEntity player = (PlayerEntity) event.getEntityLiving();
		IDevilFruit devilFruitProps = DevilFruitCapability.get(player);
		DamageSource damageSource = event.getSource();

		if (devilFruitProps.getDevilFruit().equalsIgnoreCase("magu_magu") && (damageSource.equals(DamageSource.IN_FIRE) || damageSource.equals(DamageSource.ON_FIRE) || damageSource.equals(DamageSource.LAVA)))
		{
			player.extinguish();
			event.setCanceled(true);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onEntityBurning(RenderBlockOverlayEvent event)
	{
		PlayerEntity player = Minecraft.getInstance().player;
		IDevilFruit devilFruitProps = DevilFruitCapability.get(player);

		if (!devilFruitProps.getDevilFruit().equals("magu_magu"))
			return;

		if(player.isBurning() && player.areEyesInFluid(FluidTags.LAVA))
		{
			event.setCanceled(true);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onEntityInLava(EntityViewRenderEvent.FogDensity event)
	{
		PlayerEntity player = Minecraft.getInstance().player;
		IDevilFruit devilFruitProps = DevilFruitCapability.get(player);

		if (!devilFruitProps.getDevilFruit().equals("magu_magu"))
			return;
		
		if(player.areEyesInFluid(FluidTags.LAVA))
		{
			event.setCanceled(true);
			GlStateManager.fogMode(GlStateManager.FogMode.EXP);
			event.setDensity(0.1F);
		}
	}
}
