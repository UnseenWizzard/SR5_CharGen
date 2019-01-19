package io.github.unseenwizzard.sr5chargen.data.character;

import java.io.Serializable;

public class PersData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Metatype metatype;
	private String name, ethnicity;
	private int height, weight, age;
	private Sex sex;
	private int streetCred, karma, totalKarma, notoriety, publicAwareness, extraStreetCred;
	private LifeStyle lifestyle;
	
	
	
	public LifeStyle getLifestyle() {
		return lifestyle;
	}

	public void setLifestyle(LifeStyle lifestyle) {
		this.lifestyle = lifestyle;
	}

	public PersData(Metatype metatype, String name, Sex sex){
		this.metatype=metatype;
		this.name=name;
		this.sex=sex;
		this.ethnicity=new String();
		this.age=25;
		this.height=180;
		this.weight=80;
		this.streetCred=0;
		this.karma=25;
		this.totalKarma=0;
		this.notoriety=0;
		this.publicAwareness=0;
	}

	public Metatype getMetatype() {
		return metatype;
	}

	public void setMetatype(Metatype metatype) {
		this.metatype = metatype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public int getStreetCred() {
		double tK = (double)this.getTotalKarma();
		tK/=10;
		Math.floor(tK);
		this.streetCred=(int)tK;
		return streetCred+extraStreetCred;
	}

	public void setStreetCred(int streetCred) {
		this.streetCred = streetCred;
	}
	public void addExtraStreetCred(int streetCred){
		this.extraStreetCred+=streetCred;
	}

	public int getKarma() {
		return karma;
	}

	public void setKarma(int karma) {
		this.karma = karma;
	}

	public void addKarma(int karma){
		this.karma+=karma;
		this.totalKarma+=karma;
	}

	public int getTotalKarma() {
		return totalKarma;
	}

	public void setTotalKarma(int totalKarma) {
		this.totalKarma = totalKarma;
	}

	public int getNotoriety() {
		return notoriety;
	}

	/*public void setNotoriety(int notoriety) {
		this.notoriety = notoriety;
	}*/
	public void addNotoriety(int notoriety){
		this.notoriety+=notoriety;
	}
	public void lowerNotoriety(int by){
		if (by*2<=this.getStreetCred()){
			this.notoriety-=by;
			this.streetCred-=by*2;
		}
	}

	public int getPublicAwareness() {
		return publicAwareness;
	}

	/*public void setPublicAwareness(int publicAwareness) {
		this.publicAwareness = publicAwareness;
	}*/
	public void addPublicAwareness(int pa){
		this.publicAwareness+=pa;
	}
	
	
}
