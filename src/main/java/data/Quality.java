package data;

import java.io.Serializable;

/**
 * @author captain
 *
 */
public class Quality implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name, notes;
	private boolean positive, isLeveled;
	private int APcost_bonus, maxLevel, level;
	
	@Override
	public boolean equals(Object obj){
		if (obj!=null && obj instanceof Quality){
			return (this.getName()==((Quality)obj).getName());
		}
		return false;
	}

	public boolean isLeveled() {
		return isLeveled;
	}

	public void setLeveled(boolean isLeveled) {
		this.isLeveled = isLeveled;
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

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	
	@Override
	public String toString() {
		String out = this.name;
		if (this.isLeveled){
			if(this.level>0){
				out=out+" ("+this.level+")";
			} else {
				out=out+" (max."+this.maxLevel+")";
			}
		}
		out=out+" (";
		if (!this.positive){
			out=out+"+";
		} 
		out=out+this.getAPcost_bonus()+")";
		if (!this.notes.isEmpty()){
			out=out+" ["+this.notes+"]";
		}
		return out;
	}
	
	
	
	public Quality(){
		this.name=new String();
		this.notes=new String();
		this.maxLevel=-1;
		this.level=-1;
	}
	
	public Quality(String name, String notes, boolean positive, int APcost_bonus){
		this.name=name;
		this.notes=notes;
		this.positive=positive;
		this.APcost_bonus=APcost_bonus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		if (notes!=null){
			return notes;
		} else {
			return "";
		}
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isPositive() {
		return positive;
	}

	public void setPositive(boolean positive) {
		this.positive = positive;
	}

	public int getAPcost_bonus() {
		int cost=this.APcost_bonus;
		if (this.positive){
			cost*=-1;
		} 
		if (this.isLeveled&&this.level>0){
			cost*=this.level;
		}
		return cost;
	}

	public void setAPcost_bonus(int aPcost_bonus) {
		APcost_bonus = aPcost_bonus;
	}
	
	
	
}
