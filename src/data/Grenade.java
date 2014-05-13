package data;

import java.io.Serializable;

public class Grenade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int damage, armorpiercing,blast;
	private double price;
	private int availability;
	private int availabilityType;//0 legal, 1 restricted, 2 illegal
	private boolean doesStunDamage;

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

	public Grenade(){
		this.name=new String();
		this.damage=0;
		this.armorpiercing=0;
		this.blast=0;
		this.price=0;
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

	public int getArmorpiercing() {
		return armorpiercing;
	}
	public void setArmorpiercing(int armorpiercing) {
		this.armorpiercing = armorpiercing;
	}
	public int getBlast() {
		return blast;
	}
	public void setBlast(int blast) {
		this.blast = blast;
	}



}

