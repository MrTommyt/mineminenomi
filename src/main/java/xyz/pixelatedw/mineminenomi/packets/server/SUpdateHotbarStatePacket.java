package xyz.pixelatedw.mineminenomi.packets.server;

import java.io.IOException;
import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.wypi.WyHelper;
import xyz.pixelatedw.wypi.abilities.Ability;
import xyz.pixelatedw.wypi.data.ability.AbilityDataCapability;
import xyz.pixelatedw.wypi.data.ability.IAbilityData;

public class SUpdateHotbarStatePacket
{
	private INBT data;
	private int slotNumber;

	public SUpdateHotbarStatePacket()
	{
	}

	public SUpdateHotbarStatePacket(IAbilityData props, int slotNumber)
	{
		this.data = new CompoundNBT();
		this.data = AbilityDataCapability.INSTANCE.getStorage().writeNBT(AbilityDataCapability.INSTANCE, props, null);

		this.slotNumber = slotNumber;
	}

	public void encode(PacketBuffer buffer)
	{
		buffer.writeCompoundTag((CompoundNBT) this.data);
		buffer.writeInt(this.slotNumber);
	}

	public static SUpdateHotbarStatePacket decode(PacketBuffer buffer)
	{
		SUpdateHotbarStatePacket msg = new SUpdateHotbarStatePacket();
		msg.data = buffer.readCompoundTag();
		msg.slotNumber = buffer.readInt();
		return msg;
	}

	public static void handle(SUpdateHotbarStatePacket message, final Supplier<NetworkEvent.Context> ctx)
	{
		if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT)
		{
			ctx.get().enqueueWork(() ->
			{
				PlayerEntity player = ModMain.PROXY.getPlayer();
				IAbilityData props = AbilityDataCapability.get(player);
				Ability sAbl = null;

				try
				{
					sAbl = (Ability) WyHelper.deserialize(((CompoundNBT) message.data).getByteArray("ability_" + message.slotNumber));
				}
				catch (ClassNotFoundException | IOException e)
				{
					e.printStackTrace();
				}

				if (sAbl == null)
					return;

				for (int i = 0; i < 8; i++)
				{
					if (props.getEquippedAbility(i) == null)
						continue;

					Ability cAbl = props.getEquippedAbility(i);

					if (cAbl == null || !cAbl.equals(sAbl))
						continue;

					cAbl.setState(sAbl.getState());
				}
			});
		}

		ctx.get().setPacketHandled(true);
	}
}