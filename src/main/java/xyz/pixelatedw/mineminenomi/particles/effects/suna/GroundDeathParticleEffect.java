package xyz.pixelatedw.mineminenomi.particles.effects.suna;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticle;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.wypi.WyHelper;

public class GroundDeathParticleEffect extends ParticleEffect
{

	@Override
	public void spawn(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ)
	{		
		double t = 0;
		double x, y, z;

		while(t < 2)
		{
			t += 0.1 * Math.PI;
			
			for(double theta = 0; theta <= 4 * Math.PI; theta += Math.PI / 32)
			{
				x = t * Math.cos(theta);
				y = WyHelper.randomDouble();
				z = t * Math.sin(theta);
										
				motionX = x / 2 + WyHelper.randomDouble();
				motionY = 0;
				motionZ = z / 2 + WyHelper.randomDouble();

				SimpleParticle cp = new SimpleParticle(world, ModResources.SUNA2,
						posX + (x * 1.25), 
						posY + 0.5 + y,
						posZ + (z * 1.25), 
						motionX,
						motionY, 
						motionZ)
						.setParticleAge(10).setParticleScale(3F);
				Minecraft.getInstance().particles.addEffect(cp);
			}
		}
	}

}