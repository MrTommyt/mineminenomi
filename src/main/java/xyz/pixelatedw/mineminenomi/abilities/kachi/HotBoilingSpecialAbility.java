package xyz.pixelatedw.mineminenomi.abilities.kachi;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.wypi.APIConfig.AbilityCategory;
import xyz.pixelatedw.wypi.abilities.PunchAbility;

public class HotBoilingSpecialAbility extends PunchAbility
{
	public static final HotBoilingSpecialAbility INSTANCE = new HotBoilingSpecialAbility();

	public HotBoilingSpecialAbility()
	{
		super("Hot Boiling Special", AbilityCategory.DEVIL_FRUIT);
		this.setMaxCooldown(5);
		
		this.onHitEntityEvent = this::onHitEntityEvent;
	}
	
	private float onHitEntityEvent(PlayerEntity player, LivingEntity target)
	{
		target.setFire(200);
		return 10;
	}
}