package xyz.pixelatedw.MineMineNoMi3.abilities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.SwordsmanProjectiles;
import xyz.pixelatedw.MineMineNoMi3.items.weapons.ItemCoreWeapon;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketPlayer;

public class SwordsmanAbilities 
{
	public static Ability SHISHISHISONSON = new ShiShishiSonson();
	public static Ability SANBYAKUROKUJUPOUNDHO = new SanbyakurokujuPoundHo();
	public static Ability YAKKODORI = new Yakkodori();
	public static Ability SANZENSEKAI = new SanzenSekai();
	
	public static Ability[] abilitiesArray = new Ability[] {SHISHISHISONSON, SANBYAKUROKUJUPOUNDHO, YAKKODORI, SANZENSEKAI};
	
	public static class SanzenSekai extends Ability
	{
		public SanzenSekai() 
		{
			super(ListAttributes.SANZENSEKAI); 
		}
			
		public void use(EntityPlayer player)
		{
			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemCoreWeapon)
			{

				/** FIXME New Spherical particles around the player */
								
				if(!this.isOnCooldown)
				{
					for(EntityLivingBase e : WyHelper.getEntitiesNear(player, 2.5))
					{
						e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) player), 12);
						
						e.addPotionEffect(new PotionEffect(Potion.weakness.id, 10 * 20, 1, true));
					}
					
					Minecraft.getMinecraft().thePlayer.swingItem();
				}
				super.use(player);
			}
			else
				WyHelper.sendMsgToPlayer(player, "You need a sword to use this ability !");
		}
	}
	
	public static class Yakkodori extends Ability
	{
		public Yakkodori() 
		{
			super(ListAttributes.YAKKODORI); 
		}
			
		public void use(EntityPlayer player)
		{
			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemCoreWeapon)
			{
				this.projectile = new SwordsmanProjectiles.Yakkodori(player.worldObj, player, ListAttributes.YAKKODORI);
				if(!this.isOnCooldown)
					Minecraft.getMinecraft().thePlayer.swingItem();
				super.use(player);
			}
			else
				WyHelper.sendMsgToPlayer(player, "You need a sword to use this ability !");
		}
	}
	
	public static class ShiShishiSonson extends Ability
	{
		public ShiShishiSonson() 
		{
			super(ListAttributes.SHISHISHISONSON); 
		}
			
		public void use(EntityPlayer player)
		{
			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemCoreWeapon)
			{
				if(!this.isOnCooldown)
				{
					double mX = (double)(-MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * 0.4);
					double mZ = (double)(MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * 0.4);
						
					double f2 = MathHelper.sqrt_double(mX * mX + player.motionY * player.motionY + mZ * mZ);
					mX /= (double)f2;
					mZ /= (double)f2;
					mX += player.worldObj.rand.nextGaussian() * 0.007499999832361937D * 1.0;
					mZ += player.worldObj.rand.nextGaussian() * 0.007499999832361937D * 1.0;
					mX *= 3;
					mZ *= 3;
				
					motion("=", mX, player.motionY, mZ, player);
					
					Minecraft.getMinecraft().thePlayer.swingItem();
				}
				
				super.use(player);
			}
			else
				WyHelper.sendMsgToPlayer(player, "You need a sword to use this ability !");
		}
		
	    public void duringCooldown(EntityPlayer player, int currentCooldown)
	    {
			if(currentCooldown > 4 * 20)
			{
				for(EntityLivingBase e : WyHelper.getEntitiesNear(player, 1.6))
					e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) player), 8);
			}
	    }
	}
	
	public static class SanbyakurokujuPoundHo extends Ability
	{
		public SanbyakurokujuPoundHo() 
		{
			super(ListAttributes.SANBYAKUROKUJUPOUNDHO); 
		}
			
		public void use(EntityPlayer player)
		{
			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemCoreWeapon)
			{
				this.projectile = new SwordsmanProjectiles.SanbyakurokujuPoundHo(player.worldObj, player, ListAttributes.SANBYAKUROKUJUPOUNDHO);
				if(!this.isOnCooldown)
					Minecraft.getMinecraft().thePlayer.swingItem();
				super.use(player);
			}
			else
				WyHelper.sendMsgToPlayer(player, "You need a sword to use this ability !");
		}
	}
	
	private static void motion(String c, double x, double y, double z, EntityPlayer p)
	{
		WyNetworkHelper.sendTo(new PacketPlayer("motion" + c, x, y, z), (EntityPlayerMP) p);
	}
}
