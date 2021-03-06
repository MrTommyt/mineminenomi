package xyz.pixelatedw.mineminenomi.abilities.baku;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.pixelatedw.mineminenomi.entities.projectiles.baku.BeroCannonProjectile;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.wypi.APIConfig.AbilityCategory;
import xyz.pixelatedw.wypi.WyHelper;
import xyz.pixelatedw.wypi.abilities.ChargeableAbility;

public class BakuTsuihoAbility extends ChargeableAbility
{
	public static final BakuTsuihoAbility INSTANCE = new BakuTsuihoAbility();
	
	private List<ItemStack> projectiles = new ArrayList<ItemStack>();
	private List<Block> loadedProjectiles = new ArrayList<Block>();
	private int limit = 0;
	
	public BakuTsuihoAbility()
	{
		super("Baku Tsuiho", AbilityCategory.DEVIL_FRUIT);
		this.setMaxCooldown(8);
		this.setMaxChargeTime(4);
		this.setCancelable();
		this.setDescription("Allows the user to charge multiple blocks in their mouth and shoot them all at the same time");

		this.onStartChargingEvent = this::onStartChargingEvent;
		this.duringChargingEvent = this::duringChargingEvent;
		this.onEndChargingEvent = this::onEndChargingEvent;
		this.duringCooldownEvent = this::duringCooldownEvent;
	}
	
	private void duringCooldownEvent(PlayerEntity player, int cooldown)
	{
		int projectileSpace = 2;
		if(this.limit > 0 && (this.cooldown - 10) % 2 == 0)
		{
			BeroCannonProjectile proj = new BeroCannonProjectile(player.world, player);
			proj.setLocationAndAngles(
				player.posX + WyHelper.randomWithRange(-projectileSpace, projectileSpace) + WyHelper.randomDouble(), 
				(player.posY + 0.3) + WyHelper.randomWithRange(0, projectileSpace) + WyHelper.randomDouble(), 
				player.posZ + WyHelper.randomWithRange(-projectileSpace, projectileSpace) + WyHelper.randomDouble(),
				0, 0);
			player.world.addEntity(proj);
			proj.shoot(player, player.rotationPitch, player.rotationYaw, 0, 3f, 3f);
			this.limit--;
		}
	}
	
	private boolean onEndChargingEvent(PlayerEntity player)
	{		
		this.limit = 1 + this.loadedProjectiles.size();

		return true;
	}
	
	private void duringChargingEvent(PlayerEntity player, int chargeTime)
	{
		if (!this.projectiles.isEmpty())
		{
			if (chargeTime % 10 == 0)
			{
				ItemStack stack = this.projectiles.stream().findAny().orElse(null);
				if (stack != null)
				{
					if (stack.getCount() > 1)
						stack.shrink(1);
					else
					{
						player.inventory.deleteStack(stack);
						this.projectiles.remove(stack);
					}
					this.loadedProjectiles.add(((BlockItem) stack.getItem()).getBlock());
				}
			}
		}
	}
	
	private boolean onStartChargingEvent(PlayerEntity player)
	{
		this.loadedProjectiles.clear();
		this.projectiles.clear();
		
		for (ItemStack item : player.inventory.mainInventory)
		{
			if (item != null && item.getItem() instanceof BlockItem && BakuMunchAbility.GRIEF_RULE.getApprovedBlocks().stream().anyMatch(p -> p == ((BlockItem) item.getItem()).getBlock()))
				this.projectiles.add(item);
		}

		if (!this.projectiles.isEmpty())
			return true;

		WyHelper.sendMsgToPlayer(player, new TranslationTextComponent(ModI18n.ABILITY_MESSAGE_NOT_ENOUGH_BLOCKS).getFormattedText());
		return false;
	}
}
