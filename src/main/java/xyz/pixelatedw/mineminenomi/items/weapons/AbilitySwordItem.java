package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.pixelatedw.wypi.APIConfig.AbilityCategory;
import xyz.pixelatedw.wypi.abilities.Ability;
import xyz.pixelatedw.wypi.abilities.ItemAbility;
import xyz.pixelatedw.wypi.data.ability.AbilityDataCapability;
import xyz.pixelatedw.wypi.data.ability.IAbilityData;

public class AbilitySwordItem extends CoreSwordItem
{
	private Ability ability = null;

	public AbilitySwordItem(Ability ability, int damage)
	{
		super(new Properties().defaultMaxDamage(10), damage);
		this.ability = ability;
	}

	@Override
	public void inventoryTick(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		super.inventoryTick(itemStack, world, entity, itemSlot, isSelected);
		if (entity instanceof PlayerEntity && this.ability != null)
		{
			PlayerEntity owner = (PlayerEntity) entity;
			IAbilityData abilityDataProps = AbilityDataCapability.get(owner);

			for(Ability ability : abilityDataProps.getEquippedAbilities(AbilityCategory.ALL))
			{
				if(ability == null || !(ability instanceof ItemAbility) || this.ability.equals(ability))
					continue;

				if(!(ability instanceof ItemAbility) || !ability.isContinuous() || !((ItemAbility) ability).canBeActive(owner))
					owner.inventory.deleteStack(itemStack);
			}
		}
	}

	@Override
	public boolean isEnchantable(ItemStack stack)
	{
		return false;
	}
}
