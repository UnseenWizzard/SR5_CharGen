package data;

import java.io.Serializable;
import java.util.ArrayList;

public class SkillGroup implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Skill> skills;
	private boolean isLocked;
	private int value;

	@Override
	public boolean equals(Object obj){
		if (obj!=null && obj instanceof SkillGroup){
			return (this.getName()==((SkillGroup)obj).getName());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	//if locked, not raise-able, till all values have same val.
	public void checkLock(){
		int highest=-1;
		this.isLocked=false;
		for (Skill s:skills){
			if (s.getValue()>highest){
				highest=s.getValue();
			}
		}
		if (highest>this.getValue()){
			for (Skill s:skills){
				if (s.getValue()!=highest){
					this.isLocked=true;
				}
			}
			if (this.isLocked==false){
				this.setValue(highest);
			}
		}
	}
	
	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addSkill(Skill skill) {
		this.skills.add(skill);
	}
	
	public void raiseSkill(Skill skill){
		if (this.skills.contains(skill)){
			int index = this.skills.indexOf(skill);
			this.skills.get(index).setValue(this.skills.get(index).getValue()+1);
			this.checkLock();
		}
	}

	public ArrayList<Skill> getSkills() {
		return skills;
	}



	public void setSkills(ArrayList<Skill> skills) {
		this.skills = skills;
	}



	public SkillGroup() {
		name = new String();
		skills = new ArrayList<Skill>();
	}

}
