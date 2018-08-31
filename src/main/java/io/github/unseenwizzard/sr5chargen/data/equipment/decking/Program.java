package io.github.unseenwizzard.sr5chargen.data.equipment.decking;

import java.io.Serializable;

public class Program implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private double price;
	private int availability;
private int availabilityType;//0 legal, 1 restricted, 2 illegal
	
	@Override
	public String toString(){
		return name;
	}

	@Override
	public boolean equals(Object obj){
		if (obj!=null && obj instanceof Program){
			System.out.println("is Program and not null");
			System.out.println((this.getName().equals(((Program)obj).getName()))+" "+this.getName()+" "+((Program)obj).getName());
			
			return (this.getName().equals(((Program)obj).getName()));
		}
		System.out.println("no prog or null");
		return false;
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
	
	public Program(){
		this.name=new String();
		this.description=new String();
		this.price=0;
	}
	
	public Program(String name, String description,int price){
		this.name=name;
		this.description=description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
