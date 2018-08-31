package io.github.unseenwizzard.sr5chargen.data.equipment.armor;

public class ArmorModification {
	
	private String name, description;
	private int capacityCost,availability;
	private double price;
	private int availabilityType;//0 legal, 1 restricted, 2 illegal
	
	public int getAvailabilityType() {
		return availabilityType;
	}
	public void setAvailabilityType(int availabilityType) {
		this.availabilityType = availabilityType;
	}
	
	public ArmorModification(){
		
	}
	
	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public int getCapacityCost() {
		return capacityCost;
	}



	public void setCapacityCost(int capacityCost) {
		this.capacityCost = capacityCost;
	}



	public int getAvailability(){
		return(this.availability);
	}
	public void setAvailability(int availability){
		this.availability=availability;
	}
}
