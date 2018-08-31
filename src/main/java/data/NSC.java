package data;

import java.util.ArrayList;

public class NSC {
	private Attributes attributes;
	private ArrayList<Skill> skills;
	private ArrayList<Quality> qualities;
	private int physicalDamageMax;
	private int stunDamageMax;
	private ArrayList<Power> Powers;
	
	public NSC(){
		this.attributes=new Attributes();
		this.physicalDamageMax=0;
		this.stunDamageMax=0;
		this.Powers=new ArrayList<Power>();
		this.skills=new ArrayList<Skill>();
		this.qualities=new ArrayList<Quality>();
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public ArrayList<Skill> getSkills() {
		return skills;
	}

	public void setSkills(ArrayList<Skill> skills) {
		this.skills = skills;
	}

	public ArrayList<Quality> getQualities() {
		return qualities;
	}

	public void setQualities(ArrayList<Quality> qualities) {
		this.qualities = qualities;
	}

	public int getPhysicalDamageMax() {
		return physicalDamageMax;
	}

	public void setPhysicalDamageMax(int physicalDamageMax) {
		this.physicalDamageMax = physicalDamageMax;
	}

	public int getStunDamageMax() {
		return stunDamageMax;
	}

	public void setStunDamageMax(int stunDamageMax) {
		this.stunDamageMax = stunDamageMax;
	}

	public ArrayList<Power> getPowers() {
		return Powers;
	}

	public void setPowers(ArrayList<Power> powers) {
		Powers = powers;
	}
	
	

}
