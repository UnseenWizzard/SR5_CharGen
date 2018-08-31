package io.github.unseenwizzard.sr5chargen.data.equipment.personal;

import io.github.unseenwizzard.sr5chargen.data.equipment.Gear;

public class Augmentation extends Gear {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double capacity;
	private double essenceLoss;
	private boolean canBeBuildIn, isLeveled;
	private Grade grade;
	private double essenceMultiplier, cosMultiplier;
	private int availabilityModifier;
	
	@Override
	public boolean equals(Object obj){
		if (obj!=null && obj instanceof Augmentation){
			return (this.getName()==((Augmentation)obj).getName());
		}
		return false;
	}
	
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
		if (this.grade==Grade.Standard){
			this.essenceMultiplier=1.0;
			this.availabilityModifier=0;
			this.cosMultiplier=1.0;
		} else if (this.grade==Grade.Alphaware){
			this.essenceMultiplier=0.8;
			this.availabilityModifier=2;
			this.cosMultiplier=1.2;
		} else if (this.grade==Grade.Betaware){
			this.essenceMultiplier=0.7;
			this.availabilityModifier=4;
			this.cosMultiplier=1.5;
		} else if (this.grade==Grade.Deltaware){
			this.essenceMultiplier=0.5;
			this.availabilityModifier=8;
			this.cosMultiplier=2.5;
		} else if (this.grade==Grade.Used){
			this.essenceMultiplier=1.25;
			this.availabilityModifier=-4;
			this.cosMultiplier=0.75;
		} 
	}

	public double getEssenceMultiplier() {
		return essenceMultiplier;
	}

	public double getCosMultiplier() {
		return cosMultiplier;
	}

	public int getAvailabilityModifier() {
		return availabilityModifier;
	}

	public enum Grade{
		Standard, Alphaware, Betaware, Deltaware, Used
	}
	
	public boolean isLeveled() {
		return isLeveled;
	}

	public void setLeveled(boolean isLeveled) {
		this.isLeveled = isLeveled;
	}

	public Augmentation(){
		super();
		this.essenceLoss=0;
		this.capacity=0;
		this.canBeBuildIn=false;
		this.setGrade(Grade.Standard);
	}
	
	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public boolean canBeBuildIn() {
		return canBeBuildIn;
	}

	public void setcanBeBuildIn(boolean canBeBuildIn) {
		this.canBeBuildIn = canBeBuildIn;
	}

	public Augmentation(String name, int rating, int essenceLoss, int price){
		super(name,rating,price);
		this.essenceLoss=essenceLoss;
		this.setGrade(Grade.Standard);
	}

	public double getEssenceLoss() {
		return essenceLoss*essenceMultiplier;
	}
	public double getBaseEssenceLoss() {
		return essenceLoss;
	}
	@Override
	public int getAvailability() {
		return super.getAvailability()+this.availabilityModifier;
	}
	public int getBaseAvailability() {
		return super.getAvailability();
	}
	@Override
	public double getPrice() {
		return super.getPrice()*this.cosMultiplier;
	}
	public double getBasePrice() {
		return super.getPrice();
	}

	public void setEssenceLoss(double essenceLoss) {
		this.essenceLoss = essenceLoss;
	}
	
	

}
