package data;

import java.io.Serializable;

public class LifeStyle implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	int monthlyCost, capitalDice, capitalMod;
	
	public LifeStyle(String name,int cost,int dice,int mod){
		this.name=name;
		this.monthlyCost=cost;
		this.capitalDice=dice;
		this.capitalMod=mod;
	}
	
	@Override 
	public String toString(){
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMonthlyCost() {
		return monthlyCost;
	}

	public void setMonthlyCost(int monthlyCost) {
		this.monthlyCost = monthlyCost;
	}

	public int getCapitalDice() {
		return capitalDice;
	}

	public void setCapitalDice(int capitalDice) {
		this.capitalDice = capitalDice;
	}

	public int getCapitalMod() {
		return capitalMod;
	}

	public void setCapitalMod(int capitalMod) {
		this.capitalMod = capitalMod;
	}
	
}
