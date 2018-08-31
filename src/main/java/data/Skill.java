package data;

import java.io.Serializable;

public class Skill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name, notes;
	private int value;
	private Attribute attribute;
	private boolean knowledge;
	private Skill specialisation;

	@Override
	public boolean equals(Object obj){
		if (obj!=null && obj instanceof Skill){
			return (this.getName()==((Skill)obj).getName());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public enum Attribute{
		BODY, AGILITY, REACTION, STRENGTH, WILLPOWER, LOGIC, INTUITION, CHARISMA, MAGIC, RESONANCE
	}
	
	public String getAttributeShorthand(){
		if (this.getAttribute().equals(Attribute.BODY)){
			return "(B)";
		} else if (this.getAttribute().equals(Attribute.AGILITY)){
			return "(A)";
		} else if (this.getAttribute().equals(Attribute.REACTION)){
			return "(R)";
		} else if (this.getAttribute().equals(Attribute.STRENGTH)){
			return "(S)";
		} else if (this.getAttribute().equals(Attribute.WILLPOWER)){
			return "(W)";
		} else if (this.getAttribute().equals(Attribute.LOGIC)){
			return "(L)";
		} else if (this.getAttribute().equals(Attribute.INTUITION)){
			return "(I)";
		} else if (this.getAttribute().equals(Attribute.CHARISMA)){
			return "(C)";
		} else if (this.getAttribute().equals(Attribute.MAGIC)){
			return "(Mag)";
		} else if (this.getAttribute().equals(Attribute.RESONANCE)){
			return "(Res)";
		} 
		return "none";
	}
	
	public Skill (){
		this.name=new String();
		this.notes=new String();
	}
	
	public Skill (String name, String notes, int value, Attribute attribute, boolean knowledge){
		this.name=name;
		this.notes=notes;
		this.value=value;
		this.attribute=attribute;
		this.knowledge=knowledge;
	}
	
	public Skill (String name, String notes, int value, Attribute attribute, Skill specialisation, boolean knowledge){
		this.name=name;
		this.notes=notes;
		this.value=value;
		this.attribute=attribute;
		this.knowledge=knowledge;
		this.specialisation=specialisation;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isKnowledge() {
		return knowledge;
	}

	public void setKnowledge(boolean knowledge) {
		this.knowledge = knowledge;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public Skill getSpecialisation() {
		return specialisation;
	}

	public void setSpecialisation(Skill specialisation) {
		this.specialisation = specialisation;
	}
	
	

}
