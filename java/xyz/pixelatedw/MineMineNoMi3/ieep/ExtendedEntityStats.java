package xyz.pixelatedw.MineMineNoMi3.ieep;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityManager;

public class ExtendedEntityStats implements IExtendedEntityProperties 
{

	public final static String EXT_PROP_NAME = "IEEPShared";
	private final EntityLivingBase entity;

	private boolean isInCombatMode = false;
	
	private int doriki, dorikiCmd, bounty, bountyCmd, belly, bellyCmd, extol, extolCmd, cola = 100, maxCola = 100, hakiTimer = 0, ultraCola = 0, gear = 1;
	private String akumaNoMiUsed = "N/A", faction = "N/A", race = "N/A", fightStyle = "N/A", crew = "N/A", zoanPoint = "N/A";
	private boolean isLogia, hasShadow = true, hasHeart = true, firstTime = true, hasHakiActive = false, hasBusoHakiActive = false, hasKenHakiActive = false, kilo = false, hasYamiPower = false, hasColaBackpack = false;
	
	private String[] hotbarAbilities = new String[8];
	private String[] devilFruitAbilities = new String[256];
	private String[] racialAbilities = new String[16];
	private String[] hakiAbilities = new String[3];
	
	private String tempPreviousAbility = "N/A";
	
	public ExtendedEntityStats(EntityLivingBase entity) 
	{
		this.entity = entity;	
		for(int i = 0; i < hotbarAbilities.length; i++) { hotbarAbilities[i] = "n/a"; }
		for(int i = 0; i < devilFruitAbilities.length; i++) { devilFruitAbilities[i] = "n/a"; }
		for(int i = 0; i < racialAbilities.length; i++) { racialAbilities[i] = "n/a"; }
		for(int i = 0; i < hakiAbilities.length; i++) { hakiAbilities[i] = "n/a"; }
	}
	
	public static final void register(EntityLivingBase entity) 
	{
		entity.registerExtendedProperties(ExtendedEntityStats.EXT_PROP_NAME, new ExtendedEntityStats(entity));
	}

	public static final ExtendedEntityStats get(EntityLivingBase entity) 
	{
		return (ExtendedEntityStats) entity.getExtendedProperties(EXT_PROP_NAME);
	}

	public void saveNBTData(NBTTagCompound compound) 
	{
		NBTTagCompound props = new NBTTagCompound();

		props.setInteger("Doriki", this.doriki);
		props.setInteger("DorikiCmd", this.dorikiCmd);
		props.setInteger("Bounty", this.bounty);
		props.setInteger("BountyCmd", this.bountyCmd);
		props.setInteger("Belly", this.belly);
		props.setInteger("BellyCmd", this.bellyCmd);
		props.setInteger("Extol", this.extol);
		props.setInteger("ExtolCmd", this.extolCmd);
		props.setInteger("Cola", this.cola);
		props.setInteger("MaxCola", this.maxCola);	
		props.setInteger("UltraCola", this.ultraCola);
		props.setInteger("Gear", this.gear);
		
		props.setString("AkumaNoMi", this.akumaNoMiUsed);
		props.setString("Faction", this.faction);
		props.setString("Race", this.race);
		props.setString("FightStyle", this.fightStyle);
		props.setString("Crew", this.crew);
		props.setString("ZoanPoint", this.zoanPoint);
		
		props.setBoolean("isLogia", this.isLogia);
		props.setBoolean("hasShadow", this.hasShadow);
		props.setBoolean("hasHeart", this.hasHeart);
		props.setBoolean("firstTime", this.firstTime);
		props.setBoolean("hasKiloActive", this.kilo);
		props.setBoolean("hasHakiActive", this.hasHakiActive);
		props.setBoolean("hasBusoHakiActive", this.hasBusoHakiActive);
		props.setBoolean("hasKenHakiActive", this.hasKenHakiActive);
		props.setBoolean("hasYamiPower", this.hasYamiPower);
		props.setBoolean("hasColaBackpack", this.hasColaBackpack);
		
		props.setBoolean("isInCombatMode", this.isInCombatMode);		
		
		for(int i = 0; i < hotbarAbilities.length; i++)
		{
			props.setString("ability" + i, hotbarAbilities[i]);
		}
		for(int i = 0; i < devilFruitAbilities.length; i++)
		{
			props.setString("available_DevilFruitAbilities" + i, devilFruitAbilities[i]);
		}
		for(int i = 0; i < racialAbilities.length; i++)
		{
			props.setString("available_RacialAbilities" + i, racialAbilities[i]);
		}
		for(int i = 0; i < hakiAbilities.length; i++)
		{
			props.setString("available_HakiAbilities" + i, hakiAbilities[i]);
		}
		
		compound.setTag(EXT_PROP_NAME, props);
	}

	public void loadNBTData(NBTTagCompound compound) 
	{
		NBTTagCompound props = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);

		this.doriki = props.getInteger("Doriki");
		this.dorikiCmd = props.getInteger("DorikiCmd");
		this.bounty = props.getInteger("Bounty");
		this.bountyCmd = props.getInteger("BountyCmd");
		this.belly = props.getInteger("Belly");
		this.bellyCmd = props.getInteger("BellyCmd");
		this.extol = props.getInteger("Extol");
		this.extolCmd = props.getInteger("ExtolCmd");
		this.cola = props.getInteger("Cola");
		this.maxCola = props.getInteger("MaxCola");
		this.ultraCola = props.getInteger("UltraCola");
		this.gear = props.getInteger("Gear");
		
		this.akumaNoMiUsed = props.getString("AkumaNoMi");
		this.faction = props.getString("Faction");
		this.race = props.getString("Race");
		this.fightStyle = props.getString("FightStyle");
		this.crew = props.getString("Crew");
		this.zoanPoint = props.getString("ZoanPoint");
		
		this.isLogia = props.getBoolean("isLogia");
		this.hasShadow = props.getBoolean("hasShadow");
		this.hasHeart = props.getBoolean("hasHeart");
		this.firstTime = props.getBoolean("firstTime");
		this.kilo = props.getBoolean("hasKiloActive");
		this.hasHakiActive = props.getBoolean("hasHakiActive");
		this.hasBusoHakiActive = props.getBoolean("hasBusoHakiActive");
		this.hasKenHakiActive = props.getBoolean("hasKenHakiActive");
		this.hasYamiPower = props.getBoolean("hasYamiPower");
		this.hasColaBackpack = props.getBoolean("hasColaBackpack");
		
		this.isInCombatMode = props.getBoolean("isInCombatMode");
		
		for(int i = 0; i < hotbarAbilities.length; i++)
		{
			this.hotbarAbilities[i] = props.getString("ability" + i);
		}
		for(int i = 0; i < devilFruitAbilities.length; i++)
		{
			devilFruitAbilities[i] = props.getString("available_DevilFruitAbilities" + i);
		}
		for(int i = 0; i < racialAbilities.length; i++)
		{
			racialAbilities[i] = props.getString("available_RacialAbilities" + i);
		}
		for(int i = 0; i < hakiAbilities.length; i++)
		{
			this.hakiAbilities[i] = props.getString("available_HakiAbilities" + i);
		}
	}

	public void resetNBTData(NBTTagCompound compound) 
	{
		NBTTagCompound props = new NBTTagCompound();

		props.setInteger("Doriki", 0);
		props.setInteger("DorikiCmd", 0);
		props.setInteger("Bounty", 0);
		props.setInteger("BountyCmd", 0);
		props.setInteger("Belly", 0);
		props.setInteger("BellyCmd", 0);
		props.setInteger("Extol", 0);
		props.setInteger("ExtolCmd", 0);
		props.setInteger("Cola", 0);
		props.setInteger("MaxCola", 0);	
		props.setInteger("UltraCola",0);
		props.setInteger("Gear", 0);
		
		props.setString("AkumaNoMi", "N/A");
		props.setString("Faction", "N/A");
		props.setString("Race", "N/A");
		props.setString("FightStyle", "N/A");
		props.setString("Crew", "N/A");
		props.setString("ZoanPoint", "N/A");
		
		props.setBoolean("isLogia", false);
		props.setBoolean("hasShadow", true);
		props.setBoolean("hasHeart", true);
		props.setBoolean("firstTime", false);
		props.setBoolean("hasKiloActive", false);
		props.setBoolean("hasHakiActive", false);
		props.setBoolean("hasBusoHakiActive", false);
		props.setBoolean("hasKenHakiActive", false);
		props.setBoolean("hasYamiPower", false);
		props.setBoolean("hasColaBackpack", false);
		
		props.setBoolean("isInCombatMode", false);		

		for(int i = 0; i < hotbarAbilities.length; i++)
		{
			props.setString("ability" + i, "n/a");
		}
		for(int i = 0; i < devilFruitAbilities.length; i++)
		{
			props.setString("available_DevilFruitAbilities" + i, "n/a");
		}
		for(int i = 0; i < racialAbilities.length; i++)
		{
			props.setString("available_RacialAbilities" + i, "n/a");
		}
		for(int i = 0; i < hakiAbilities.length; i++)
		{
			props.setString("available_HakiAbilities" + i, "n/a");
		}
		
		compound.setTag(EXT_PROP_NAME, props);
	}
	
	public void init(Entity entity, World world) {}
	
	// #REGION AVAILABLE DEVIL FRUITS ABILITIES
	int devilFruitIndex = 0;
	
	public void addDevilFruitAbility(Ability abl)
	{
		if(this.devilFruitAbilities[devilFruitIndex] == null || this.devilFruitAbilities[devilFruitIndex].equals("n/a"))
			this.devilFruitAbilities[devilFruitIndex] = WyHelper.getFancyName(abl.getAttribute().getAttributeName());
		else
		{
			if(devilFruitIndex < this.devilFruitAbilities.length)
				devilFruitIndex++;
			else
				devilFruitIndex = 0;
			this.addDevilFruitAbility(abl);
		}
	}
	
	public void removeDevilFruitAbility(Ability abl)
	{
		if(this.devilFruitAbilities[devilFruitIndex] != null || this.devilFruitAbilities[devilFruitIndex].equals("n/a"))
			this.devilFruitAbilities[devilFruitIndex] = "n/a";
		else
		{
			if(devilFruitIndex < 16)
				devilFruitIndex++;
			else
				devilFruitIndex = 0;
			this.removeDevilFruitAbility(abl);			
		}	
	}
	public boolean hasDevilFruitAbility(Ability abl)
	{
		for(String s : this.devilFruitAbilities)
		{
			if(s.equals(WyHelper.getFancyName(abl.getAttribute().getAttributeName())))
				return true;					
		}	
		return false;
	}
	public String[] getDevilFruitAbilities()
	{ 
		return this.devilFruitAbilities; 
	}
	public void clearDevilFruitAbilities()
	{
		for(int j = 0; j < this.devilFruitAbilities.length; j++)
			this.devilFruitAbilities[j] = "n/a";
		devilFruitIndex = 0;
	}
	//	#END REGION
	
	// #REGION AVAILABLE RACIAL ABILITIES
	int racialIndex = 0;
	
	public void addRacialAbility(Ability abl)
	{
		if(this.racialAbilities[racialIndex] == null || this.racialAbilities[racialIndex].equals("n/a"))
			this.racialAbilities[racialIndex] = WyHelper.getFancyName(abl.getAttribute().getAttributeName());
		else
		{
			if(racialIndex < 16)
				racialIndex++;
			else
				racialIndex = 0;
			this.addRacialAbility(abl);
		}
	}
	
	public boolean hasRacialAbility(Ability abl)
	{	
		for(String s : this.racialAbilities)
		{
			if(s.equals(WyHelper.getFancyName(abl.getAttribute().getAttributeName())))
				return true;					
		}	
		return false;
	}
	
	public void removeRacialAbility(Ability abl)
	{
		if(this.racialAbilities[racialIndex] != null || !this.racialAbilities[racialIndex].equals("n/a"))
			this.racialAbilities[racialIndex] = "n/a";
		else
		{
			if(racialIndex < 16)
				racialIndex++;
			else
				racialIndex = 0;
			this.removeRacialAbility(abl);			
		}
	}
	
	public String[] getRacialAbilities()
	{ 
		return this.racialAbilities; 
	}
	
	public void clearRacialAbilities()
	{
		for(int j = 0; j < this.racialAbilities.length; j++)
			this.racialAbilities[j] = "n/a";
		racialIndex = 0;
	}
	//	#END REGION
	
	// #REGION AVAILABLE HAKI ABILITIES
	int hakiIndex = 0;
	
	public void addHakiAbility(Ability abl)
	{
		if(this.hakiAbilities[hakiIndex] == null || this.hakiAbilities[hakiIndex].equals("n/a"))
			this.hakiAbilities[hakiIndex] = WyHelper.getFancyName(abl.getAttribute().getAttributeName());
		else
		{
			if(hakiIndex < 3)
				hakiIndex++;
			else
				hakiIndex = 0;
			this.addHakiAbility(abl);
		}
	}
	public boolean hasHakiAbility(Ability abl)
	{
		for(String s : this.hakiAbilities)
		{
			if(s.equals(WyHelper.getFancyName(abl.getAttribute().getAttributeName())))
				return true;					
		}	
		return false;
	}
	public void removeHakiAbility(Ability abl)
	{
		if(this.hakiAbilities[hakiIndex] != null || !this.hakiAbilities[hakiIndex].equals("n/a"))
			this.hakiAbilities[hakiIndex] = "n/a";
		else
		{
			if(hakiIndex < 16)
				hakiIndex++;
			else
				hakiIndex = 0;
			this.removeHakiAbility(abl);			
		}
	}
	public String[] getHakiAbilities()
	{ 
		return this.hakiAbilities; 
	}
	public void clearHakiAbilities()
	{
		for(int j = 0; j < this.hakiAbilities.length; j++)
			this.hakiAbilities[j] = "n/a";
		hakiIndex = 0;
	}
	//	#END REGION
	
	public void setAbilityInSlot(int slot, Ability abl)
	{
		if(abl != null)
			this.hotbarAbilities[slot] = WyHelper.getFancyName(abl.getAttribute().getAttributeName());
		else
			this.hotbarAbilities[slot] = "n/a";
	}
	public Ability getAbilityFromSlot(int slot)
	{
		return AbilityManager.instance().getAbilityByName(this.hotbarAbilities[slot]);
	}
	public int countAbilitiesInHotbar()
	{
		return this.hotbarAbilities.length;
	}
	public void clearHotbar()
	{
		for(int j = 0; j < this.hotbarAbilities.length; j++)
			this.hotbarAbilities[j] = "n/a";
	}
	
	
	public void setCombatMode(boolean value) { this.isInCombatMode = value; }
	public boolean isInCombatMode() { return this.isInCombatMode; }
	
	public int getDoriki() {return doriki;}
	public void alterDoriki(int i)
	{
		if(doriki + i < 0) doriki = 0;
		else doriki = doriki + i;
	}
	public void setDoriki(int i) {doriki = i;}
	
	public int getDorikiFromCommand() {return dorikiCmd;}
	public void alterDorikiFromCommand(int i)
	{
		if(dorikiCmd + i < 0) dorikiCmd = 0;
		else dorikiCmd = dorikiCmd + i;
	}
	public void setDorikiFromCommand(int i) {dorikiCmd = i;}
	
	public int getExtol() {return this.extol;}
	public void alterExtol(int i)
	{
		if(extol + i < 0) extol = 0;
		else extol = extol + i;
	}
	public void setExtol(int i) {this.extol = i;}
	
	public int getExtolFromCommand() {return this.extolCmd;}
	public void alterExtolFromCommand(int i)
	{
		if(extolCmd + i < 0) extolCmd = 0;
		else extolCmd = extolCmd + i;
	}
	public void setExtolFromCommand(int i) {this.extolCmd = i;}
	
	public int getBelly() {return this.belly;}
	public void alterBelly(int i)
	{
		if(belly + i < 0) belly = 0;
		else belly = belly + i;
	}
	public void setBelly(int i)	{this.belly = i;}
	
	public int getBellyFromCommand() {return this.bellyCmd;}
	public void alterBellyFromCommand(int i)
	{
		if(bellyCmd + i < 0) bellyCmd = 0;
		else bellyCmd = bellyCmd + i;
	}
	public void setBellyFromCommand(int i)	{this.bellyCmd = i;}
	
	public int getBounty() {return this.bounty;}
	public void alterBounty(int i)
	{
		if(bounty + i < 0) bounty = 0;
		else bounty = bounty + i;
	}
	public void setBounty(int i) {this.bounty = i;}

	public int getBountyFromCommand() {return this.bountyCmd;}
	public void alterBountyFromCommand(int i)
	{
		if(bountyCmd + i < 0) bountyCmd = 0;
		else bountyCmd = bountyCmd + i;
	}
	public void setBountyFromCommand(int i) {this.bountyCmd = i;}
	
	public int getCola() {return this.cola;}
	public void alterCola(int i)
	{
		if(cola + i < 0) cola = 0;
		else if(cola + i > getMaxCola()) cola = getMaxCola();
		else cola = cola + i;
	}
	public void setCola(int i) {this.cola = i;}
	
	public int getUltraColaConsumed() { return this.ultraCola; }
	public void setUltraCola(int i) { this.ultraCola = i; }
	public void addUltraCola() { this.ultraCola++; }
	
	public int getMaxCola() { return this.maxCola; }
	public void setMaxCola(int maxCola) { this.maxCola = maxCola; }
	
	public boolean isLogia() {return this.isLogia;}
	public void setIsLogia(boolean i) {this.isLogia = i;}
	
	public String getUsedFruit() {return this.akumaNoMiUsed;}
	public void setUsedFruit(String name) {this.akumaNoMiUsed = name;}
	
	public boolean hasHeart() {return this.hasHeart;}
	public void setHasHeart(boolean b) {this.hasHeart = b;}
	
	public boolean hasShadow() {return this.hasShadow;}
	public void setHasShadow(boolean b) {this.hasShadow = b;}
	
	public void setGear(int i) {this.gear = i;}
	public int getGear() {return this.gear;}
	
	public String getFightStyle() {return this.fightStyle;}
	public void setFightStyle(String i) {this.fightStyle = i;}
	
	public String getRace() {return this.race;}
	public void setRace(String i) {this.race = i;}
	
	public String getFaction() {return this.faction;}
	public void setFaction(String i) {this.faction = i;}
	
	public String getCrew() {return this.crew;}
	public void setCrew(String crewName) {this.crew = crewName;}
	
	public String getZoanPoint() {return this.zoanPoint;}
	public void setZoanPoint(String i) {this.zoanPoint = i;}
	
	public boolean isFirstTime() {return this.firstTime;}
	public void setFirstTime(boolean firstTime) {this.firstTime = firstTime;}
	public void firstTimePass() {this.firstTime = false;}
	
	public boolean hasBusoHakiActive() { return hasBusoHakiActive; }
	public void triggerBusoHaki(boolean isBusoHakiActive) { this.hasBusoHakiActive = isBusoHakiActive; }
	public boolean hasKenHakiActive() { return hasKenHakiActive; }
	public void triggerKenHaki(boolean isKenHakiActive) { this.hasKenHakiActive = isKenHakiActive; }
	public boolean hasHakiActive() { return hasHakiActive; }
	public void triggerActiveHaki(boolean isHakiActive) { this.hasHakiActive = isHakiActive; }
	public int getHakiTimer() { return hakiTimer; }
	public void addHakiTimer() { hakiTimer++; }
	public void decHakiTimer() { hakiTimer--; }
	public void resetHakiTimer() { hakiTimer = 0; }

	public void setKilo(boolean kilo) { this.kilo = kilo; }
	public boolean getKilo() { return kilo; } 
	
	public void setYamiPower(boolean bool) { this.hasYamiPower = bool; }
	public boolean hasYamiPower() { return hasYamiPower; } 
	
	public void setColaBackpack(boolean bool) { this.hasColaBackpack = bool; }
	public boolean hasColaBackpack() { return hasColaBackpack; }
	
	public void setTempPreviousAbility(String temp) { this.tempPreviousAbility = temp; }
	public String getTempPreviousAbility() { return this.tempPreviousAbility; }
}