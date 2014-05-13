package data;

import java.io.Serializable;

public class Ammunition implements Cloneable, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int damageMod, piercingMod;
	private double price;
	private int rounds;
	private int availability;
	private int availabilityType;//0 legal, 1 restricted, 2 illegal
	private boolean doesStunDamage;
	
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	public void setRounds(int rounds){
		this.rounds=rounds;
	}
	public int getRounds(){
		return this.rounds;
	}
	public void removeRounds(int rounds){
		this.rounds-=rounds;
	}
	public void addRounds(int rounds){
		this.rounds+=rounds;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj!=null && obj instanceof Ammunition){
			return (this.getName()==((Ammunition)obj).getName());
		}
		return false;
	}
	
	@Override
	public String toString() {
		String dmgType="P";
		if (doesStunDamage){
			dmgType="S";
		}
		
		String avType="L";
		if (availabilityType==1){
			avType="R";
		} else if (availabilityType==2){
			avType="F";
		}
		if (this.rounds>0){
			return  rounds*10+" "+name + "( "+damageMod+" "+dmgType+" | "+piercingMod+" ) "+availability+" "+avType+" - "+price+" NuYen";
		} else {
			return  name + "( "+damageMod+" "+dmgType+" | "+piercingMod+" ) "+availability+" "+avType+" - "+price+" NuYen";
		}
	}
	
	public boolean isDoesStunDamage() {
		return doesStunDamage;
	}
	public void setDoesStunDamage(boolean doesStunDamage) {
		this.doesStunDamage = doesStunDamage;
	}
	
	public int getAvailabilityType() {
		return availabilityType;
	}
	public void setAvailabilityType(int availabilityType) {
		this.availabilityType = availabilityType;
	}
	public int getAvailability(){
		return(this.availability);
	}
	public void setAvailability(int availability){
		this.availability=availability;
	}
	
	public Ammunition(){
		this.name=new String();
		this.damageMod=0;
		this.rounds=0;
		this.piercingMod=0;
		this.price=0;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Ammunition(String name, int price, int damageMod, int piercingMod){
		this.name=name;
		this.damageMod=damageMod;
		this.piercingMod=piercingMod;
		this.price=price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDamageMod() {
		return damageMod;
	}

	public void setDamageMod(int damageMod) {
		this.damageMod = damageMod;
	}

	public int getPiercingMod() {
		return piercingMod;
	}

	public void setPiercingMod(int piercingMod) {
		this.piercingMod = piercingMod;
	}
	
	

}
