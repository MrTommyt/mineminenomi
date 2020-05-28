package xyz.pixelatedw.mineminenomi.init;

import org.apache.commons.lang3.tuple.ImmutablePair;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.common.Mod;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruArtsPickaxeAbility;
import xyz.pixelatedw.mineminenomi.api.AbilityItemTier;
import xyz.pixelatedw.mineminenomi.items.*;
import xyz.pixelatedw.mineminenomi.items.weapons.AbilityPickaxeItem;
import xyz.pixelatedw.wypi.APIConfig;
import xyz.pixelatedw.wypi.WyRegistry;
import xyz.pixelatedw.wypi.json.models.JSONPredicateObject;
import xyz.pixelatedw.wypi.json.models.item.JSONModelSimple3DItem;
import xyz.pixelatedw.wypi.json.models.item.JSONModelSword;

@Mod.EventBusSubscriber(modid = APIConfig.PROJECT_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems
{
	// Random stuff
	public static final Item CHARACTER_CREATOR = new CharacterCreatorItem();
	public static final Item KAIROSEKI = new Item(new Properties().group(ModCreativeTabs.MISC));
	public static final Item DENSE_KAIROSEKI = new Item(new Properties().group(ModCreativeTabs.MISC));
	public static final Item BLACK_METAL = new Item(new Properties().group(ModCreativeTabs.MISC));
	public static final Item SHADOW = new ShadowItem(new Properties().group(ModCreativeTabs.MISC));
	public static final Item HEART = new HeartItem();
	public static final Item BELLY_POUCH = new BellyPouchItem();
	public static final Item KEY = new Item(new Properties().group(ModCreativeTabs.MISC));
	public static final Item VIVRE_CARD = new VivreCardItem();
	public static final Item BUBBLY_CORAL = new BubblyCoralItem();
	public static final Item DORU_PICKAXE = new AbilityPickaxeItem(DoruDoruArtsPickaxeAbility.INSTANCE, AbilityItemTier.DORU, 1, -2.8f);
    public static final Item COLOR_PALETTE = new ColorPaletteItem(new Item.Properties().group(ModCreativeTabs.MISC));
	public static final Item WATERING_CAN = new WateringCanItem();
	
	// Devil Fruit Boxes
	public static final Item TIER_1_BOX = new AkumaNoMiBoxItem(1);
	public static final Item TIER_2_BOX = new AkumaNoMiBoxItem(2);
	public static final Item TIER_3_BOX = new AkumaNoMiBoxItem(3);

	// Food
	public static final Item SEA_KING_MEAT = new SeaKingMeatItem();
	public static final Item COLA = new ColaItem();
	public static final Item ULTRA_COLA = new UltraColaItem();
	public static final Item SAKE_BOTTLE = new SakeBottleItem();
	public static final Item SAKE_CUP = new SakeCupItem();

	// Ammo
	public static final Item BULLET = new Item(new Properties().group(ModCreativeTabs.MISC));
	public static final Item KAIROSEKI_BULLET = new Item(new Properties().group(ModCreativeTabs.MISC));
	public static final Item KUJA_ARROW = new Item(new Properties().group(ModCreativeTabs.MISC));
	public static final Item POP_GREEN = new Item(new Properties().group(ModCreativeTabs.MISC));

	// JSON Predicates
	public static final JSONPredicateObject FILLED_PREDICATE = new JSONPredicateObject("filled", new ImmutablePair("filled", 1));
	
	// JSON 3D Custom Models
	public static final JSONModelSimple3DItem FILLED_CUP_MODEL = new JSONModelSimple3DItem("sake_cup", FILLED_PREDICATE).setThirdPersonRotations(0, 0, 0);

	static
	{
		WyRegistry.registerItem(KAIROSEKI, "Kairoseki");
		WyRegistry.registerItem(DENSE_KAIROSEKI, "Dense Kairoseki");
		WyRegistry.registerItem(BLACK_METAL, "Black Metal");
		WyRegistry.registerItem(SHADOW, "Shadow");
		WyRegistry.registerItem(KEY, "Key");
		WyRegistry.registerItem(CHARACTER_CREATOR, "Character Creator");
		WyRegistry.registerItem(BELLY_POUCH, "Belly Pouch");
		WyRegistry.registerItem(SEA_KING_MEAT, "Sea King Meat");
		WyRegistry.registerItem(COLA, "Cola");
		WyRegistry.registerItem(ULTRA_COLA, "Ultra Cola");
		WyRegistry.registerItem(HEART, "Heart");
		WyRegistry.registerItem(BULLET, "Bullet");
		WyRegistry.registerItem(KAIROSEKI_BULLET, "Kairoseki Bullet");
		WyRegistry.registerItem(KUJA_ARROW, "Kuja Arrow");
		WyRegistry.registerItem(POP_GREEN, "Pop Green");
		WyRegistry.registerItem(TIER_1_BOX, "Wooden Box");
		WyRegistry.registerItem(TIER_2_BOX, "Iron Box");
		WyRegistry.registerItem(TIER_3_BOX, "Golden Box");
		WyRegistry.registerItem(BUBBLY_CORAL, "Bubbly Coral");
		WyRegistry.registerItem(VIVRE_CARD, "Vivre Card");
		WyRegistry.registerItem(COLOR_PALETTE, "Color Palette");
		WyRegistry.registerItem(DORU_PICKAXE, "Doru Doru Arts: Pickaxe", new JSONModelSword("Doru Doru Arts: Pickaxe"));
		WyRegistry.registerItem(WATERING_CAN, "Watering Can");
		WyRegistry.registerItem(SAKE_BOTTLE, "Sake Bottle");
		WyRegistry.registerItem(SAKE_CUP, "Sake Cup", FILLED_CUP_MODEL);
	}
}
