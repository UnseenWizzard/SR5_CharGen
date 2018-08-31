package io.github.unseenwizzard.sr5chargen.data.equipment.decking;

import java.io.Serializable;

public class Deck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String model;
	private int attributeArray[];
	private int deviceRating;
	//attack, sleaze, dataProcessing, firewall
	
	private int simultaniousPrograms;
	private double price;
	private int availability;
	private int availabilityType;//0 legal, 1 restricted, 2 illegal
	
	@Override
	public String toString() {
		String avType="L";
		if (availabilityType==1){
			avType="R";
		} else if (availabilityType==2){
			avType="F";
		}
		return  model + " ("+ deviceRating + ") ["+attributeArray[0]+"|"+attributeArray[1]+"|"+attributeArray[2]+"|"+attributeArray[3]+"] ("+ simultaniousPrograms + ") "+availability+" "+avType+" - "+price+"NuYen";
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
	
	public Deck (){
		this.simultaniousPrograms=0;
		this.model=new String();
		//this.attack=0;
		//this.sleaze=0;
		this.deviceRating=0;
		//this.dataProcessing=0;
		//this.firewall=0;
		this.attributeArray=new int[4];
		this.price=0;
		
	}
	
	public Deck (String model, int price, /*int attack, int sleaze,*/ int deviceRating/*,int dataProcessing,int firewall*/){
		this.simultaniousPrograms=0;
		this.model=model;
		this.attributeArray=new int[4];
		//this.attack=attack;
		//this.sleaze=sleaze;
		this.deviceRating=deviceRating;
		//this.dataProcessing=dataProcessing;
		//this.firewall=firewall;
		this.price=price;
		
	}

	public int[] getAttributeArray() {
		return attributeArray;
	}
	public void setAttributeArray(int[] attributeArray) {
		this.attributeArray = attributeArray;
	}
	public int getSimultaniousPrograms() {
		return simultaniousPrograms;
	}
	public void setSimultaniousPrograms(int simultaniousPrograms) {
		this.simultaniousPrograms = simultaniousPrograms;
	}
	public int getDeviceRating() {
		return deviceRating;
	}
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	/*public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getSleaze() {
		return sleaze;
	}

	public void setSleaze(int sleaze) {
		this.sleaze = sleaze;
	}

	public int getDeviceRating() {
		return deviceRating;
	}*/

	public void setDeviceRating(int deviceRating) {
		this.deviceRating = deviceRating;
	}

	/*public int getDataProcessing() {
		return dataProcessing;
	}

	public void setDataProcessing(int dataProcessing) {
		this.dataProcessing = dataProcessing;
	}

	public int getFirewall() {
		return firewall;
	}

	public void setFirewall(int firewall) {
		this.firewall = firewall;
	}*/	
	
}
