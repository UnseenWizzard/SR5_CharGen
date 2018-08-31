package io.github.unseenwizzard.sr5chargen.data.character;

import java.io.Serializable;

public class Spell implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Type type;
	private Range range;
	private Duration duration;
	private boolean area;
	private int drain;
	private Damage damage;
	private Set set;
	private SubSet subset[];

	public enum Damage{
		Physical, Stun
	}
	
	public enum Set{
		Combat, Detection, Health, Illusion, Manipulation
	}
	
	public enum SubSet{
		Direct, Indirect, Elemental, 
		Active, Passive, Directional, Area, ExtendedArea, Psychic,
		Essence,
		Obvious, Realistic, SingleSense, MultiSense, 
		Mental, Environmental, Physical, Damaging,
		Anchored, MaterialLink, Minion, Spell, Spotter
	}
	
	public enum Type {
		Physical, Mana,
		Persona, Device, File, Self, Sprite
	}
	public enum Range {
		LineOfSight, Touch
	}
	public enum Duration {
		Instant, Permanent, Sustain
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj instanceof Spell){
			if (((Spell)obj).getName().equals(this.getName())){
				return true;
			}
		}
		return false;
	}
	
	@Override 
	public String toString(){
		return this.getName()+" ["+this.getType()+", "+this.getRange()+", "+this.getDuration()+", Force/Level"+this.getDrain()+"]";
	}
	
	public Spell(){
		this.name= new String();
		this.subset= new SubSet[2];
	}
	
	public Spell(String name, Type type, Range range, Duration duration, boolean area, int drain, Damage damage){
		this.name=name;
		this.type=type;
		this.range=range;
		this.duration=duration;
		this.area=area;
		this.drain=drain;
		this.damage=damage;
		this.subset= new SubSet[2];
	}

	public Set getSet() {
		return set;
	}

	public void setSet(Set set) {
		this.set = set;
	}

	public SubSet[] getSubset() {
		return subset;
	}

	public void setSubset(SubSet[] subset) {
		this.subset = subset;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public boolean isArea() {
		return area;
	}

	public void setArea(boolean area) {
		this.area = area;
	}

	public int getDrain() {
		return drain;
	}

	public void setDrain(int drain) {
		this.drain = drain;
	}

	public Damage getDamage() {
		return damage;
	}

	public void setDamage(Damage damage) {
		this.damage = damage;
	}
	
	

}
