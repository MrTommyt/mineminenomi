package xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainMod;
import xyz.pixelatedw.MineMineNoMi3.api.EnumParticleTypes;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityAttribute;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityProjectile;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.GasuProjectiles.GasRobe;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.GasuProjectiles.Gastille;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;

public class YukiProjectiles 
{
	
	public static ArrayList<Object[]> abilitiesClassesArray = new ArrayList();
	
	static
	{
		abilitiesClassesArray.add(new Object[] {YukiRabi.class, ListAttributes.YUKIRABI});
	}
	
	public static class YukiRabi extends AbilityProjectile
	{
		public YukiRabi(World world)
		{super(world);}
		
		public YukiRabi(World world, double x, double y, double z)
		{super(world, x, y, z);}
		
		public YukiRabi(World world, EntityLivingBase player, AbilityAttribute attr) 
		{		
			super(world, player, attr);		
		}	
		
		public void onUpdate()
		{	
			for (int i = 0; i < 1; i++)
			{
				double offsetX = (new Random().nextInt(4) + 2.0D - 2.0D) / 2.0D;
				double offsetY = (new Random().nextInt(4) + 2.0D - 2.0D) / 2.0D;
				double offsetZ = (new Random().nextInt(4) + 2.0D - 2.0D) / 2.0D;
		      
				MainMod.proxy.spawnCustomParticles(this, ID.PARTICLE_NAME_YUKI, this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ, 0.0, 0.0, 0.0);
			}
			
			super.onUpdate();
		}
	}
	

}
