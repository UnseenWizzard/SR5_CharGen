package data;

import java.io.Serializable;
import java.util.ArrayList;;

public class RangedWeapon implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int damage, accuracy, armorPiercing, recoil,ammo;
	private AmmoType ammotype;
	private ArrayList<Mode> mode;
	private double price;
	private int availability;
	private boolean doesStunDamage;
private int availabilityType;//0 legal, 1 restricted, 2 illegal

public String getAmmoTypeShorthand(){
	if (this.ammotype.equals(AmmoType.BELTFED)){
		return "bf";
	} else if (this.ammotype.equals(AmmoType.BREAKACTION)){
		return "b";
	} else if (this.ammotype.equals(AmmoType.CLIP)){
		return "c";
	} else if (this.ammotype.equals(AmmoType.CYLINDER)){
		return "cy";
	} else if (this.ammotype.equals(AmmoType.DRUM)){
		return "d";
	} else if (this.ammotype.equals(AmmoType.MAGAZINE)){
		return "m";
	}  else if (this.ammotype.equals(AmmoType.MUZZLELOADER)){
		return "ml";
	}
	return "";
}

public String getModeShorthandString(){
	String modes="";
	for (Mode m:mode){
		if (m.equals(Mode.SINGLESHOT)){
			modes+="SS";
		} else if (m.equals(Mode.SEMIAUTOMATIC)){
			modes+="SA";
		} else if (m.equals(Mode.SEMIAUTOMATICBURST)){
			modes+="SB";
		} else if (m.equals(Mode.BURSTFIRE)){
			modes+="BF";
		} else if (m.equals(Mode.LONGBURST)){
			modes+="LB";
		} else if (m.equals(Mode.FULLAUTO)){
			modes+="FA";
		}
		
		if (mode.indexOf(m)<mode.size()-1){
			modes+="/";
		}
	}
	return modes;
}

@Override
public String toString() {
	String dmgType="P";
	if (doesStunDamage){
		dmgType="S";
	}
	String modes="";
	for (Mode m:mode){
		modes+=m.name();
		if (mode.indexOf(m)<mode.size()-1){
			modes+="/";
		}
	}
	String avType="L";
	if (availabilityType==1){
		avType="R";
	} else if (availabilityType==2){
		avType="F";
	}
	return  name + " ( "+ accuracy + " | " + damage + " "+dmgType+" | " + armorPiercing +" | "+modes+" | "+recoil+" | "+ammo+"("+ammotype.name()+") ) "+availability+" "+avType+" - "+price+"NuYen";
}

	public int getAvailabilityType() {
		return availabilityType;
	}
	public void setAvailabilityType(int availabilityType) {
		this.availabilityType = availabilityType;
	}
	
	
	public boolean isDoesStunDamage() {
		return doesStunDamage;
	}
	public void setDoesStunDamage(boolean doesStunDamage) {
		this.doesStunDamage = doesStunDamage;
	}

	private int realDamage, realAccuracy, realArmorPiercing, realRecoil,realAmmo;
	private int currentAmmoIndex;
	private ArrayList<Ammunition> carriedAmmo;
	private ArrayList<WeaponModification> modifications;
	
	public int getAvailability(){
		return(this.availability);
	}
	public void setAvailability(int availability){
		this.availability=availability;
	}
	
	public RangedWeapon(){
		this.name=new String();
		this.accuracy=0;
		this.ammo=0;
		this.armorPiercing=0;
		this.recoil=0;
		this.damage=0;
		this.price=0;
		this.ammotype=AmmoType.CLIP;
		this.mode=new ArrayList<Mode>();
		this.carriedAmmo=new ArrayList<Ammunition>();
		this.modifications=new ArrayList<WeaponModification>();
	}
	
	public RangedWeapon(String name, int price,int accuracy,int ammo,int armorPiercing,int recoil, int damage, ArrayList<Mode> mode, AmmoType ammotype){
		this.name=name;
		this.accuracy=accuracy;
		this.ammo=ammo;
		this.armorPiercing=armorPiercing;
		this.recoil=recoil;
		this.damage=damage;
		this.mode=mode;
		this.carriedAmmo=new ArrayList<Ammunition>();
		this.modifications=new ArrayList<WeaponModification>();
		this.ammotype=ammotype;
		this.price=price;
	}
	
	public RangedWeapon(String name, int price, int accuracy,int ammo,int armorPiercing,int recoil, int damage, ArrayList<Mode> mode, AmmoType ammotype, ArrayList<Ammunition> carriedAmmo, ArrayList<WeaponModification> mods){
		this.name=name;
		this.accuracy=accuracy;
		this.ammo=ammo;
		this.armorPiercing=armorPiercing;
		this.recoil=recoil;
		this.damage=damage;
		this.mode=mode;
		this.ammotype=ammotype;
		this.carriedAmmo=carriedAmmo;
		this.currentAmmoIndex=0;
		this.modifications=mods;
		this.price=price;
		this.updateRealvalues();
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getArmorPiercing() {
		return armorPiercing;
	}

	public void setArmorPiercing(int armorPiercing) {
		this.armorPiercing = armorPiercing;
	}

	public int getRecoil() {
		return recoil;
	}

	public void setRecoil(int recoil) {
		this.recoil = recoil;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public ArrayList<Mode> getMode() {
		return mode;
	}

	public void setMode(ArrayList<Mode> mode) {
		this.mode = mode;
	}

	public AmmoType getAmmotype() {
		return ammotype;
	}

	public void setAmmotype(AmmoType ammotype) {
		this.ammotype = ammotype;
	}
	
	public void removeMode(Mode mode){
		this.mode.remove(mode);
	}
	public void addMode(Mode mode){
		this.mode.add(mode);
	}
	
	public void removeModification(WeaponModification WeaponMod){
		this.modifications.remove(WeaponMod);
	}
	public void addModification(WeaponModification WeaponMod){
		this.modifications.add(WeaponMod);
	}
	
	public void removeCarriedAmmo(Ammunition ammo){
		boolean change=false;
		if (this.currentAmmoIndex==this.carriedAmmo.indexOf(ammo)){
			this.currentAmmoIndex=0;
			change=true;
		}
		this.carriedAmmo.remove(ammo);
		if(change){
			this.updateRealvalues();
		}
	}
	public void addCarriedAmmo(Ammunition ammo){
		this.carriedAmmo.add(ammo);
	}

	public int getCurrentAmmoIndex() {
		return currentAmmoIndex;
	}

	public void setCurrentAmmoIndex(int currentAmmoIndex) {
		this.currentAmmoIndex = currentAmmoIndex;
		this.updateRealvalues();
	}

	public ArrayList<Ammunition> getCarriedAmmo() {
		return carriedAmmo;
	}

	public void setCarriedAmmo(ArrayList<Ammunition> carriedAmmo) {
		this.carriedAmmo = carriedAmmo;
		this.updateRealvalues();
	}

	public ArrayList<WeaponModification> getModifications() {
		return modifications;
	}

	public void setModifications(ArrayList<WeaponModification> modifications) {
		this.modifications = modifications;
		this.updateRealvalues();
	}

	public int getRealDamage() {
		this.updateRealvalues();
		return realDamage;
	}

	public int getRealAccuracy() {
		this.updateRealvalues();
		return realAccuracy;
	}

	public int getRealArmorPiercing() {
		this.updateRealvalues();
		return realArmorPiercing;
	}

	public int getRealRecoil() {
		this.updateRealvalues();
		return realRecoil;
	}

	public int getRealAmmo() {
		this.updateRealvalues();
		return realAmmo;
	}
	
	public void updateRealvalues(){
		int damageMod=0, 
		accuracyMod=0, 
		armorPiercingMod=0, 
		recoilMod=0,
		ammoMod=0;
		if (!this.carriedAmmo.isEmpty()){
			damageMod+=this.carriedAmmo.get(currentAmmoIndex).getDamageMod();
			armorPiercingMod+=this.carriedAmmo.get(currentAmmoIndex).getPiercingMod();
		}
		for(WeaponModification mod:this.modifications){
			damageMod+=mod.getDamageMod();
			accuracyMod+=mod.getAccuracyMod(); 
			armorPiercingMod+=mod.getArmorPiercingMod(); 
			recoilMod+=mod.getRecoilMod();
			ammoMod+=mod.getAmmoMod();
		}
		this.realAccuracy=this.accuracy+accuracyMod;
		this.realAmmo=this.ammo+ammoMod;
		this.realArmorPiercing=this.armorPiercing+armorPiercingMod;
		this.realDamage=this.damage+damageMod;
		this.realRecoil=this.recoil+recoilMod;
	}

}
