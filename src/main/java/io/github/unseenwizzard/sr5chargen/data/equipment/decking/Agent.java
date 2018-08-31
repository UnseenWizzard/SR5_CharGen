package io.github.unseenwizzard.sr5chargen.data.equipment.decking;

public class Agent extends Program {
	
	private int level,maxLevel;
	
	public Agent(){
		super();
		this.level=0;
	}
	
	public Agent(String name, String description, int level, int price){
		super(name,description,price);
		this.level=level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int level) {
		this.maxLevel = level;
	}
	
	@Override
	public double getPrice(){
		return (super.getPrice())*this.level;
	}
	
	public double getBasePrice(){
		return super.getPrice();
	}
	
	@Override
	public int getAvailability(){
		return (super.getAvailability())*this.level;
	}
	
	public int getBaseAvailability(){
		return super.getAvailability();
	}

	
}
