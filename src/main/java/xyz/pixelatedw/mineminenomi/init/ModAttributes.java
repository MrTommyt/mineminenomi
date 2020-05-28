package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.pixelatedw.wypi.APIConfig;

@Mod.EventBusSubscriber(modid = APIConfig.PROJECT_ID)
public class ModAttributes {
    public static final IAttribute FALL_RESISTANCE = (new RangedAttribute(null, APIConfig.PROJECT_ID + ".fallResistance", 0D, -256D, 256D)).setDescription("Fall Resistance");
    public static final IAttribute JUMP_HEIGHT = (new RangedAttribute(null, APIConfig.PROJECT_ID + ".jumpHeight", 1D, -256D, 256D)).setDescription("Jump Height").setShouldWatch(true);

    @SubscribeEvent
    public static void onEntityConstruct(EntityEvent.EntityConstructing e) {
        if (e.getEntity() instanceof LivingEntity) {
            ((LivingEntity) e.getEntity()).getAttributes().registerAttribute(FALL_RESISTANCE);
            ((LivingEntity) e.getEntity()).getAttributes().registerAttribute(JUMP_HEIGHT);
        }
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent e) {
        IAttributeInstance attributeInstance = e.getEntityLiving().getAttribute(FALL_RESISTANCE);
        attributeInstance.setBaseValue(e.getDamageMultiplier());
        e.setDamageMultiplier((float) attributeInstance.getValue());
    }

    @SubscribeEvent
    public static void onJump(LivingJumpEvent e) {
        double value = e.getEntityLiving().getAttribute(JUMP_HEIGHT).getValue();
        e.getEntityLiving().addVelocity(0,  0.1F * (value - 1), 0);
        if(value <= 0)
            e.getEntityLiving().setVelocity(0, e.getEntityLiving().getMotion().y, 0);
    }

}
