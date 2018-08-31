package io.github.unseenwizzard.sr5chargen.data.equipment;

import java.io.Serializable;

public class Gear implements Cloneable, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int rating;
	private double price;
	private String notes;
	private boolean ratingLeveled;
	private int maxRating;
	private int availability;
	private int availabilityType;//0 legal, 1 restricted, 2 illegal
	
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	@Override
	public String toString() {
		String avType="L";
		if (availabilityType==1){
			avType="R";
		} else if (availabilityType==2){
			avType="F";
		}
		return  name + " ( "+ rating + " / " + maxRating + ") "+availability+" "+avType+" - "+price+"NuYen";
	}

	
	public boolean isRatingLeveled() {
		return ratingLeveled;
	}
	public void setRatingLeveled(boolean ratingLeveled) {
		this.ratingLeveled = ratingLeveled;
	}
	public int getMaxRating() {
		return maxRating;
	}
	public void setMaxRating(int maxRating) {
		this.maxRating = maxRating;
	}
	public int getAvailabilityType() {
		return availabilityType;
	}
	public void setAvailabilityType(int availabilityType) {
		this.availabilityType = availabilityType;
	}
	
	public int getAvailability(){
		if (isRatingLeveled()){
			return this.availability*this.rating;
		}
		return(this.availability);
	}
	public void setAvailability(int availability){
		this.availability=availability;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		if (ratingLeveled && rating>maxRating){
			this.rating=maxRating;
		}
		this.rating = rating;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Gear(){
		this.name = new String();
		this.rating = 0;
		this.price=0;
		this.notes = new String();
	}
	
	public double getPrice() {
		if (ratingLeveled){
			return price*rating;
		}
		return price;
	}
	
	public double getBasePrice(){
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Gear(String name, int rating, int price){
		this.name = name;
		this.rating=rating;
		this.price=price;
		this.notes=new String();
	}	
}
