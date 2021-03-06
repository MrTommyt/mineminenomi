package xyz.pixelatedw.mineminenomi.blocks.tileentities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import xyz.pixelatedw.mineminenomi.init.ModTileEntities;
import xyz.pixelatedw.wypi.WyHelper;

public class CustomSpawnerTileEntity extends TileEntity implements ITickableTileEntity
{
	private EntityType entityToSpawn = EntityType.PIG;
	private int spawnLimit = 5;
	private ArrayList<LivingEntity> spawnedEntities = new ArrayList<LivingEntity>();

	public CustomSpawnerTileEntity()
	{
		super(ModTileEntities.CUSTOM_SPAWNER);
	}
	
	public CustomSpawnerTileEntity setSpawnerMob(EntityType toSpawn)
	{
		this.entityToSpawn = toSpawn;
		return this;
	}

	public CustomSpawnerTileEntity setSpawnerLimit(int limit)
	{
		this.spawnLimit = limit;
		return this;
	}

	@Override
	public void tick()
	{
		if (!this.world.isRemote)
		{
	    	if(this.world.getBlockState(this.pos.down()).getBlock() == Blocks.AIR)
	    		this.world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
			
			boolean flag = false;

			List<PlayerEntity> nearbyEntities = WyHelper.getEntitiesNear(this.getPos(), this.world, 30, PlayerEntity.class);
			
			if (!nearbyEntities.isEmpty())
			{
				LivingEntity e = nearbyEntities.get(0);

				if (e != null && e instanceof PlayerEntity && this.entityToSpawn != null)
				{
					if ((this.spawnedEntities.size() < this.spawnLimit))
					{
						LivingEntity newSpawn = (LivingEntity) this.entityToSpawn.spawn(world, (CompoundNBT)null, (ITextComponent)null, (PlayerEntity)null, pos.up(), SpawnReason.STRUCTURE, false, false);
						if (newSpawn != null)
						{
							newSpawn.setLocationAndAngles(this.pos.getX(), this.pos.getY(), this.pos.getZ(), 0, 0);
							//newSpawn.onInitialSpawn();
							this.world.addEntity(newSpawn);
							this.spawnedEntities.add(newSpawn);
						}
					}
				}
			}
			else
			{
				if (this.spawnedEntities.size() == this.spawnLimit)
				{
					flag = true;
				}
			}

			if (flag)
			{
				for (LivingEntity elb : this.spawnedEntities)
				{
					elb.remove();
				}
				this.spawnedEntities.clear();
				flag = false;
			}
		}
	}

	@Override
	public void read(CompoundNBT nbtTag)
	{
		super.read(nbtTag);
		this.entityToSpawn = EntityType.byKey(nbtTag.getString("entityToSpawn")).orElse(EntityType.PIG);
		this.spawnLimit = nbtTag.getInt("spawnLimit");
	}

	@Override
	public CompoundNBT write(CompoundNBT nbtTag)
	{
		super.write(nbtTag);
		nbtTag.putInt("spawnLimit", this.spawnLimit);
		nbtTag.putString("entityToSpawn", EntityType.getKey(this.entityToSpawn).toString());
		return nbtTag;
	}
}
