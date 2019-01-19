package io.github.unseenwizzard.sr5chargen.data.character;

import io.github.unseenwizzard.sr5chargen.data.*;
import io.github.unseenwizzard.sr5chargen.data.equipment.Gear;
import io.github.unseenwizzard.sr5chargen.data.equipment.Vehicle;
import io.github.unseenwizzard.sr5chargen.data.equipment.armor.Armor;
import io.github.unseenwizzard.sr5chargen.data.equipment.decking.Deck;
import io.github.unseenwizzard.sr5chargen.data.equipment.decking.Program;
import io.github.unseenwizzard.sr5chargen.data.equipment.personal.Augmentation;
import io.github.unseenwizzard.sr5chargen.data.equipment.personal.ID;
import io.github.unseenwizzard.sr5chargen.data.equipment.weapon.Ammunition;
import io.github.unseenwizzard.sr5chargen.data.equipment.weapon.Grenade;
import io.github.unseenwizzard.sr5chargen.data.equipment.weapon.MeeleWeapon;
import io.github.unseenwizzard.sr5chargen.data.equipment.weapon.RangedWeapon;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Character implements Serializable, Comparable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 42L;
	
	private PersData personalData;
	private Attributes attributes;
	private ArrayList<Skill> skills;
	private ArrayList<SkillGroup> skillgroups;
	private SkillGroup chosenAspectedSkillgroup;
	private ArrayList<Quality> qualities;
	private int physicalDamageMax;
	private int stunDamageMax;
	private int damageOverflow;
	private ArrayList<Contact> contacts;
	private ArrayList<ID> fakeIDs;
	private ArrayList<Program> programs;
	private double money;
	private ArrayList<RangedWeapon> rangedWeapons;
	private ArrayList<Ammunition> ammunition;
	private ArrayList<Grenade> grenades;
	private ArrayList<MeeleWeapon> meeleWeapons;
	private ArrayList<Armor> armor;
	private ArrayList<Augmentation> augmentations;
	private ArrayList<Gear> gear;
	private ArrayList<Deck> cyberdecks;
	private ArrayList<Vehicle> vehicles;
	private ArrayList<Spell> spells, preparations, rituals, complexForms;
	private ArrayList<Power> adeptPowers, otherPowers;
	private Magical magicalness;
	private long lastOpened;
	private byte[] charPicData;
	
	public void openedChar(){
		this.lastOpened=System.currentTimeMillis();
	}
	
	public long getLastOpened(){
		return this.lastOpened;
	}
	
	public BufferedImage getCharPic(){
		try {
			return (ImageIO.read(new ByteArrayInputStream(charPicData)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public byte[] getCharPicData(){
		return this.charPicData;
	}
	
	public void setCharPicData(byte[] pic){
		this.charPicData=pic;
	}
	
	public int getDamageOverflow() {
		return damageOverflow;
	}
	public void setDamageOverflow(int damageOverflow) {
		this.damageOverflow = damageOverflow;
	}
	public SkillGroup getChosenAspectedSkillGroup(){
		return this.chosenAspectedSkillgroup;
	}
	public void setChosenAspectedSkillGroup(SkillGroup sg){
		this.chosenAspectedSkillgroup=sg;
	}
	
	public Magical getMagicalness(){
		return this.magicalness;
	}
	public void setMagicalness(Magical magicalness){
		this.magicalness=magicalness;
	}

	public Character() {
		this.attributes = new Attributes();
		this.physicalDamageMax=0;
		this.stunDamageMax=0;
		this.money=0;
		this.skills= new ArrayList<>();
		this.skillgroups= new ArrayList<>();
		this.qualities= new ArrayList<>();
		this.contacts=new ArrayList<>();
		this.fakeIDs=new ArrayList<>();
		this.rangedWeapons=new ArrayList<>();
		this.grenades=new ArrayList<>();
		this.ammunition=new ArrayList<>();
		this.meeleWeapons=new ArrayList<>();
		this.armor=new ArrayList<>();
		this.augmentations=new ArrayList<>();
		this.gear=new ArrayList<>();
		this.cyberdecks=new ArrayList<>();
		this.vehicles=new ArrayList<>();
		this.programs=new ArrayList<>();
		this.spells=new ArrayList<>();
		this.preparations=new ArrayList<>();
		this.rituals=new ArrayList<>();
		this.complexForms=new ArrayList<>();
		this.adeptPowers= new ArrayList<>();
		this.otherPowers= new ArrayList<>();
	}

	public Character(Metatype type, String name, Sex sex){
		this();
		this.personalData=new PersData(type, name, sex);
	}

	public void removeSkillGroup(SkillGroup skillgroup){
		this.skillgroups.remove(skillgroup);
	}
	public void addSkillGroup(SkillGroup skillgroup){
		this.skillgroups.add(skillgroup);
	}
	
	public ArrayList<SkillGroup> getSkillGroups(){
		return this.skillgroups;
	}
	
	public void removeAmmunition(Ammunition ammunition){
		this.ammunition.remove(ammunition);
	}
	public void addAmmunition(Ammunition ammunition){
		this.ammunition.add(ammunition);
	}
	
	public ArrayList<Ammunition> getAmmunition(){
		return this.ammunition;
	}
	
	public void removeSkill(Skill skill){
		this.skills.remove(skill);
	}
	public void addSkill(Skill skill){
		this.skills.add(skill);
	}
	
	public void removeGrenade(Grenade grenade){
		this.grenades.remove(grenade);
	}
	public void addGrenade(Grenade grenade){
		this.grenades.add(grenade);
	}
	
	public void removeQuality(Quality quality){
		this.qualities.remove(quality);
	}
	public void addQuality(Quality quality){
		this.qualities.add(quality);
	}
	
	public void removeContact(Contact contact){
		this.contacts.remove(contact);
	}
	public void addContact(Contact contact){
		this.contacts.add(contact);
	}
	
	public void removeFakeID(ID fakeID){
		this.fakeIDs.remove(fakeID);
	}
	public void addFakeID(ID fakeID){
		this.fakeIDs.add(fakeID);
	}
	
	public void removeRangedWeapon(RangedWeapon rangedWeapon){
		this.rangedWeapons.remove(rangedWeapon);
	}
	public void addRangedWeapon(RangedWeapon rangedWeapon){
		this.rangedWeapons.add(rangedWeapon);
	}
	
	public void removeMeeleWeapon(MeeleWeapon meeleWeapon){
		this.meeleWeapons.remove(meeleWeapon);
	}
	public void addMeeleWeapon(MeeleWeapon meeleWeapon){
		this.meeleWeapons.add(meeleWeapon);
	}
	
	public void removeArmor(Armor armor){
		this.armor.remove(armor);
	}
	public void addArmor(Armor armor){
		this.armor.add(armor);
	}
	
	public void removeAugmentation(Augmentation augmentation){
		this.augmentations.remove(augmentation);
	}
	public void addAugmentation(Augmentation augmentation){
		this.augmentations.add(augmentation);
	}
	
	public void removeGear(Gear gear){
		this.gear.remove(gear);
	}
	public void addGear(Gear gear){
		this.gear.add(gear);
	}
	
	public void removeDeck(Deck cyberdeck){
		this.cyberdecks.remove(cyberdeck);
	}
	public void addDeck(Deck cyberdeck){
		this.cyberdecks.add(cyberdeck);
	}
	
	public void removeVehicle(Vehicle vehicle){
		this.vehicles.remove(vehicle);
	}
	public void addVehicle(Vehicle vehicle){
		this.vehicles.add(vehicle);
	}
	
	public void removeSpellPrepRitualForm(Spell spell, boolean isSpell,boolean isPreperation,boolean isRitual,boolean isComplexForm){
		if (isSpell){
			this.spells.remove(spell);
		}else if (isPreperation){
			this.preparations.remove(spell);
		}else if (isRitual){
			this.rituals.remove(spell);
		}else if (isComplexForm){
			this.complexForms.remove(spell);
		}
	}
	public void addSpellPrepRitualForm(Spell spell, boolean isSpell,boolean isPreperation,boolean isRitual,boolean isComplexForm){
		if (isSpell){
			this.spells.add(spell);
		}else if (isPreperation){
			this.preparations.add(spell);
		}else if (isRitual){
			this.rituals.add(spell);
		}else if (isComplexForm){
			this.complexForms.add(spell);
		}
	}
	
	public void removePower(Power power, boolean isAdept,boolean isOther){
		if (isAdept){
			this.adeptPowers.remove(power);
		}else if (isOther){
			this.otherPowers.remove(power);
		}
	}
	public void addPower(Power power, boolean isAdept,boolean isOther){
		if (isAdept){
			this.adeptPowers.add(power);
		}else if (isOther){
			this.otherPowers.add(power);
		}
	}
	
	 
	public PersData getPersonalData() {
		return personalData;
	}

	public void setPersonalData(PersData personalData) {
		this.personalData = personalData;
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

	public ArrayList<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<Contact> contacts) {
		this.contacts = contacts;
	}

	public ArrayList<ID> getFakeIDs() {
		return fakeIDs;
	}

	public void setFakeIDs(ArrayList<ID> fakeIDs) {
		this.fakeIDs = fakeIDs;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public ArrayList<RangedWeapon> getRangedWeapons() {
		return rangedWeapons;
	}

	public void setRangedWeapons(ArrayList<RangedWeapon> rangedWeapons) {
		this.rangedWeapons = rangedWeapons;
	}

	public ArrayList<MeeleWeapon> getMeeleWeapons() {
		return meeleWeapons;
	}

	public void setMeeleWeapons(ArrayList<MeeleWeapon> meeleWeapons) {
		this.meeleWeapons = meeleWeapons;
	}

	public ArrayList<Armor> getArmor() {
		return armor;
	}

	public void setArmor(ArrayList<Armor> armor) {
		this.armor = armor;
	}

	public ArrayList<Augmentation> getAugmentations() {
		return augmentations;
	}

	public void setAugmentations(ArrayList<Augmentation> augmentations) {
		this.augmentations = augmentations;
	}

	public ArrayList<Gear> getGear() {
		return gear;
	}

	public void setGear(ArrayList<Gear> gear) {
		this.gear = gear;
	}

	public ArrayList<Deck> getCyberdecks() {
		return cyberdecks;
	}

	public void setCyberdecks(ArrayList<Deck> cyberdecks) {
		this.cyberdecks = cyberdecks;
	}

	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(ArrayList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public ArrayList<Spell> getSpells() {
		return spells;
	}

	public void setSpells(ArrayList<Spell> spells) {
		this.spells = spells;
	}

	public ArrayList<Spell> getPreparations() {
		return preparations;
	}

	public void setPreparations(ArrayList<Spell> preparations) {
		this.preparations = preparations;
	}

	public ArrayList<Spell> getRituals() {
		return rituals;
	}

	public void setRituals(ArrayList<Spell> rituals) {
		this.rituals = rituals;
	}

	public ArrayList<Spell> getComplexForms() {
		return complexForms;
	}

	public void setComplexForms(ArrayList<Spell> complexForms) {
		this.complexForms = complexForms;
	}

	public ArrayList<Power> getAdeptPowers() {
		return adeptPowers;
	}

	public void setAdeptPowers(ArrayList<Power> adeptPowers) {
		this.adeptPowers = adeptPowers;
	}

	public ArrayList<Power> getOtherPowers() {
		return otherPowers;
	}

	public void setOtherPowers(ArrayList<Power> otherPowers) {
		this.otherPowers = otherPowers;
	}
	
	public ArrayList<Program> getPrograms() {
		return programs;
	}

	public void setPrograms(ArrayList<Program> programs) {
		this.programs = programs;
	}
	
	public void addProgram(Program program){
		this.programs.add(program);
	}
	
	public void removeProgram(Program program){
		this.programs.remove(program);
	}

	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof Character){
			long date = ((Character)arg0).getLastOpened();
			if (this.getLastOpened()<date){
				return 1;
			} else {
				return -1;
			}
		}
		return 0;
	}
	
	
	
	
}
