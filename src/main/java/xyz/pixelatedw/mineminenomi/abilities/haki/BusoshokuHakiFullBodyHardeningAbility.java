package xyz.pixelatedw.mineminenomi.abilities.haki;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.wypi.APIConfig.AbilityCategory;
import xyz.pixelatedw.wypi.abilities.PunchAbility;

public class BusoshokuHakiFullBodyHardeningAbility extends PunchAbility
{
	public static final BusoshokuHakiFullBodyHardeningAbility INSTANCE = new BusoshokuHakiFullBodyHardeningAbility();

	public BusoshokuHakiFullBodyHardeningAbility()
	{
		super("Busoshoku Haki: Full Body Hardening", AbilityCategory.HAKI);

		this.duringContinuityEvent = this::duringContinuity;
		this.onEndContinuityEvent = this::onEndContinuityEvent;
	}
	
	// Overriding this method for haki punches since we want the ability to remain active after the punch
	public float hitEntity(PlayerEntity player, LivingEntity target)
	{
		IEntityStats props = EntityStatsCapability.get(player);
		
		float dorikiPower = props.getDoriki() / 1000;
		float hakiPower = HakiHelper.getBusoHakiExp() / 4;
		float finalPower = dorikiPower + hakiPower;
		
		return finalPower;
	}

	private void duringContinuity(PlayerEntity player, int passiveTimer)
	{
		HakiHelper.checkForHakiOveruse(player, passiveTimer);
	}

	private boolean onEndContinuityEvent(PlayerEntity player)
	{
		int cooldown = (int) Math.round(this.continueTime / 20.0);
		this.setMaxCooldown(cooldown);
		return true;
	}
}