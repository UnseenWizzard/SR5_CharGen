	package data;

	import java.io.Serializable;
import java.util.ArrayList;

	public class MeeleWeapon implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String name;
		private int damage, reach, accuracy,AP;
		private boolean dependsOnStrength, doesStunDamage;
		private int realDamage, realAccuracy, realReach;
		private ArrayList<WeaponModification> modifications;
		private double price;
		private int availability;
		private int availabilityType;//0 legal, 1 restricted, 2 illegal
		
		
		@Override
		public String toString() {
			String dmgType="P";
			if (doesStunDamage){
				dmgType="S";
			}
			String str = "";
			if (dependsOnStrength){
				str = "STR+";
			}
			String avType="L";
			if (availabilityType==1){
				avType="R";
			} else if (availabilityType==2){
				avType="F";
			}
			return  name + " ( "+ accuracy + " | " + reach + " | " +str+ damage + " "+dmgType+" | " + AP +" ) "+availability+" "+avType+" - "+price+"NuYen";
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
		public boolean isDependsOnStrength() {
			return dependsOnStrength;
		}
		public void setDependsOnStrength(boolean dependsOnStrength) {
			this.dependsOnStrength = dependsOnStrength;
		}
		public int getReach() {
			return reach;
		}
		public void setReach(int reach) {
			this.reach = reach;
		}
		
		public int getAvailability(){
			return(this.availability);
		}
		public void setAvailability(int availability){
			this.availability=availability;
		}
		
		public MeeleWeapon(){
			this.name=new String();
			this.accuracy=0;
			this.reach=0;
			this.damage=0;
			this.price=0;
			this.modifications=new ArrayList<WeaponModification>();
		}
		
		public MeeleWeapon(String name, int price, int accuracy,int reach,int damage){
			this.name=name;
			this.accuracy=accuracy;
			this.reach=reach;
			this.damage=damage;
			this.price=price;
			this.modifications=new ArrayList<WeaponModification>();
		}
		
		public MeeleWeapon(String name, int price, int accuracy,int reach,int damage, ArrayList<WeaponModification> mods){
			this.name=name;
			this.accuracy=accuracy;
			this.reach=reach;
			this.damage=damage;
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
		
		public void removeModification(WeaponModification WeaponMod){
			this.modifications.remove(WeaponMod);
			this.updateRealvalues();
		}
		public void addModification(WeaponModification WeaponMod){
			this.modifications.add(WeaponMod);
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
			return realDamage;
		}

		public int getRealAccuracy() {
			return realAccuracy;
		}
		
		public int getRealReach() {
			return realReach;
		}

		public void updateRealvalues(){
			int damageMod=0, 
			accuracyMod=0, 
			reachMod=0;
			for(int index=0;index<this.modifications.size();index++){
				damageMod+=this.modifications.get(index).getDamageMod();
				accuracyMod+=this.modifications.get(index).getAccuracyMod(); 
				reachMod+=this.modifications.get(index).getReachMod(); 
			}
			this.realAccuracy=this.accuracy+accuracyMod;
			this.realDamage=this.damage+damageMod;
			this.realReach=this.reach+reachMod;
		}

		public int getAP() {
			return AP;
		}

		public void setAP(int aP) {
			this.AP = aP;
		}

	}
