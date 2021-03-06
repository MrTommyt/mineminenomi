package xyz.pixelatedw.mineminenomi.entities.projectiles.fishmankarate;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import xyz.pixelatedw.mineminenomi.api.abilities.ExplosionAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.common.WaterExplosionParticleEffect;
import xyz.pixelatedw.wypi.WyHelper;
import xyz.pixelatedw.wypi.abilities.projectiles.AbilityProjectileEntity;

public class MurasameProjectile extends AbilityProjectileEntity
{
	public MurasameProjectile(World world)
	{
		super(FishmanKarateProjectiles.MURASAME, world);
	}

	public MurasameProjectile(EntityType type, World world)
	{
		super(type, world);
	}

	public MurasameProjectile(World world, double x, double y, double z)
	{
		super(FishmanKarateProjectiles.MURASAME, world, x, y, z);
	}

	public MurasameProjectile(World world, LivingEntity player)
	{
		super(FishmanKarateProjectiles.MURASAME, world, player);
		
		this.setDamage(6);
		this.setPassThroughEntities();
		this.setChangeHurtTime(true);

		this.onBlockImpactEvent = this::onBlockImpactEvent;
		this.onTickEvent = this::onTickEvent;
	}
	
	private void onBlockImpactEvent(BlockPos hit)
	{		
		ExplosionAbility explosion = AbilityHelper.newExplosion(this.getThrower(), hit.getX(), hit.getY(), hit.getZ(), 2);
		explosion.setExplosionSound(false);
		explosion.setDamageOwner(false);
		explosion.setDestroyBlocks(true);
		explosion.setFireAfterExplosion(false);
		explosion.setSmokeParticles(new WaterExplosionParticleEffect());
		explosion.setDamageEntities(true);
		explosion.doExplosion();
	}
	
	private void onTickEvent()
	{
		if (!this.world.isRemote)
		{
			for(int i = 0; i < 2; i++)
			{
				double offsetX = WyHelper.randomDouble() / 2;
				double offsetY = WyHelper.randomDouble() / 2;
				double offsetZ = WyHelper.randomDouble() / 2;
	
				((ServerWorld)this.world).spawnParticle(ParticleTypes.FISHING, this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ, 1, 0, 0, 0, -0.1);
				((ServerWorld)this.world).spawnParticle(ParticleTypes.BUBBLE, this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ, 1, 0, 0, 0, 0.1);
			}
		}
	}
}