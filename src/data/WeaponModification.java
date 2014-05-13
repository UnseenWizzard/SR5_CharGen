package data;

public class WeaponModification extends Gear{
	
	private int damageMod, accuracyMod, armorPiercingMod, recoilMod,ammoMod,reachMod;
	private MountPoint mountPoint;
	
	public enum MountPoint{
		Under, Top, UnderORTop, Barrel, None
	}
	
	

	public MountPoint getMountPoint() {
		return mountPoint;
	}

	public void setMountPoint(MountPoint mountPoint) {
		this.mountPoint = mountPoint;
	}

	public WeaponModification(){
		super();
		this.damageMod=0;
		this.accuracyMod=0;
		this.armorPiercingMod=0;
		this.recoilMod=0;
		this.ammoMod=0;
		this.reachMod=0;
	}
	
	public WeaponModification(String name, String description, int damageMod,int accuracyMod,int armorPiercingMod, int recoilMod,int ammoMod, int price){
		super(name,0,price);
		this.damageMod=damageMod;
		this.accuracyMod=accuracyMod;
		this.armorPiercingMod=armorPiercingMod;
		this.recoilMod=recoilMod;
		this.ammoMod=ammoMod;;
	}

	public int getDamageMod() {
		return damageMod;
	}

	public void setDamageMod(int damageMod) {
		this.damageMod = damageMod;
	}

	public int getAccuracyMod() {
		return accuracyMod;
	}

	public void setAccuracyMod(int accuracyMod) {
		this.accuracyMod = accuracyMod;
	}

	public int getArmorPiercingMod() {
		return armorPiercingMod;
	}

	public void setArmorPiercingMod(int armorPiercingMod) {
		this.armorPiercingMod = armorPiercingMod;
	}

	public int getRecoilMod() {
		return recoilMod;
	}

	public void setRecoilMod(int recoilMod) {
		this.recoilMod = recoilMod;
	}

	public int getAmmoMod() {
		return ammoMod;
	}

	public void setAmmoMod(int ammoMod) {
		this.ammoMod = ammoMod;
	}

	public int getReachMod() {
		return reachMod;
	}
	
	public void setReachMod(int reachMod) {
		this.reachMod = reachMod;
	}
	
	
}
