package io.github.unseenwizzard.sr5chargen.data.equipment;

import java.io.Serializable;

public class Vehicle implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int handlingOnRoad, handlingOffRoad, speedOnRoad, speedOffRoad, acceleration, body, armor, pilot, sensor, seats;
	private String notes;
	private double price;
	private int availability;
	private CraftType type;
	private int availabilityType;//0 legal, 1 restricted, 2 illegal
	
	public enum CraftType {
		Groundcraft, Watercraft, Aircraft, Drone, Other
	}
	
	@Override
	public String toString() {
		String avType="L";
		if (availabilityType==1){
			avType="R";
		} else if (availabilityType==2){
			avType="F";
		}
		if (handlingOffRoad>0&&speedOffRoad>0){
			return  name +"["+type+"]" + " ("+ handlingOnRoad+"/"+handlingOffRoad+" | "+acceleration+" | " +speedOnRoad+"/"+speedOffRoad+" | "+pilot+" | "+body+" | "+armor+" | "+sensor+" | "+seats+") "+availability+" "+avType+" - "+price+"NuYen";
		} else {
			return  name +"["+type+"]" + " ("+ handlingOnRoad+" | "+acceleration+" | " +speedOnRoad+" | "+pilot+" | "+body+" | "+armor+" | "+sensor+" | "+seats+") "+availability+" "+avType+" - "+price+"NuYen";

		}
	}
		
	public CraftType getType() {
		return type;
	}
	public void setType(CraftType type) {
		this.type = type;
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
	
	public Vehicle(){
		this.name= new String();
		this.notes=new String();
	}
	
	public Vehicle(String name, String notes, int price, int handling, int speed, int acceleration, int body, int armor, int pilot, int sensor, int seats){
		this.name=name;
		this.notes=notes;
		this.handlingOnRoad=handling;
		this.speedOnRoad=speed;
		this.acceleration=acceleration;
		this.body=body;
		this.armor=armor;
		this.pilot=pilot;
		this.sensor=sensor;
		this.seats=seats;
		this.price=price;
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

	public int getHandling() {
		return handlingOnRoad;
	}

	public void setHandling(int handling) {
		this.handlingOnRoad = handling;
	}
	public int getHandlingOffRoad() {
		return handlingOffRoad;
	}

	public void setHandlingOffRoad(int handling) {
		this.handlingOffRoad = handling;
	}

	public int getSpeed() {
		return speedOnRoad;
	}

	public void setSpeed(int speed) {
		this.speedOnRoad = speed;
	}
	
	public int getSpeedOffRoad() {
		return speedOffRoad;
	}

	public void setSpeedOffRoad(int speed) {
		this.speedOffRoad = speed;
	}

	public int getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(int acceleration) {
		this.acceleration = acceleration;
	}

	public int getBody() {
		return body;
	}

	public void setBody(int body) {
		this.body = body;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getPilot() {
		return pilot;
	}

	public void setPilot(int pilot) {
		this.pilot = pilot;
	}

	public int getSensor() {
		return sensor;
	}

	public void setSensor(int sensor) {
		this.sensor = sensor;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	
}
