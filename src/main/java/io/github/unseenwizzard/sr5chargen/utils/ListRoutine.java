package io.github.unseenwizzard.sr5chargen.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import io.github.unseenwizzard.sr5chargen.data.character.*;
import io.github.unseenwizzard.sr5chargen.data.character.Spell.Damage;
import io.github.unseenwizzard.sr5chargen.data.character.Spell.Duration;
import io.github.unseenwizzard.sr5chargen.data.character.Spell.Range;
import io.github.unseenwizzard.sr5chargen.data.character.Spell.Set;
import io.github.unseenwizzard.sr5chargen.data.character.Spell.Type;
import io.github.unseenwizzard.sr5chargen.data.equipment.Vehicle;
import io.github.unseenwizzard.sr5chargen.data.equipment.Vehicle.CraftType;
import io.github.unseenwizzard.sr5chargen.data.equipment.Gear;
import io.github.unseenwizzard.sr5chargen.data.equipment.armor.Armor;
import io.github.unseenwizzard.sr5chargen.data.equipment.decking.Agent;
import io.github.unseenwizzard.sr5chargen.data.equipment.decking.Deck;
import io.github.unseenwizzard.sr5chargen.data.equipment.decking.Program;
import io.github.unseenwizzard.sr5chargen.data.equipment.personal.Augmentation;
import io.github.unseenwizzard.sr5chargen.data.equipment.weapon.*;

public class ListRoutine {
	ArrayList<Skill> skillList;
	ArrayList<SkillGroup> skillGroupList;
	ArrayList<Quality> qualityList;
	ArrayList<Spell> spellList;
	ArrayList<Spell> ritualList;
	ArrayList<Spell> complexFormList;
	ArrayList<Program> programList;
	ArrayList<Power> powerList;
	ArrayList<Agent> agentList;
	ArrayList<Ammunition> ammuList;
	ArrayList<Armor> armorList;
	ArrayList<Augmentation> augmentList;
	ArrayList<Deck> deckList;
	ArrayList<Gear> gearList;
	ArrayList<MeeleWeapon> meeleWpList;
	ArrayList<RangedWeapon> rangedWpList;
	ArrayList<WeaponModification> wpModList;
	ArrayList<Vehicle> vehicleList;
	ArrayList<Grenade> grenadeList;
	XStream xstream = new XStream(new StaxDriver());
	
	public ListRoutine() {
		skillList=new ArrayList<Skill>();
		skillGroupList=new ArrayList<SkillGroup>();
		qualityList=new ArrayList<Quality>();
		spellList=new ArrayList<Spell>();
		ritualList=new ArrayList<Spell>();
		complexFormList=new ArrayList<Spell>();
		programList=new ArrayList<Program>();
		powerList=new ArrayList<Power>();
		agentList=new ArrayList<Agent>();
		ammuList=new ArrayList<Ammunition>();
		armorList=new ArrayList<Armor>();
		augmentList=new ArrayList<Augmentation>();
		deckList=new ArrayList<Deck>();
		gearList=new ArrayList<Gear>();
		meeleWpList=new ArrayList<MeeleWeapon>();
		rangedWpList=new ArrayList<RangedWeapon>();
		wpModList=new ArrayList<WeaponModification>();
		grenadeList=new ArrayList<Grenade>();
		vehicleList=new ArrayList<Vehicle>();
		
		xstream.alias("Skill", Skill.class);
		xstream.alias("SkillGroup", SkillGroup.class);
		xstream.alias("LinkedAttribute", Skill.Attribute.class);
		xstream.alias("Quality",Quality.class);
		xstream.alias("Spell",Spell.class);
		xstream.alias("SpellType-ComplexFormTarget",Spell.Type.class);
		xstream.alias("SpellRange",Spell.Range.class);
		xstream.alias("SpellDuration",Spell.Duration.class);
		xstream.alias("SpellSet", Spell.Set.class);
		xstream.alias("SpellSubSet", Spell.SubSet.class);
		xstream.alias("Program", Program.class);
		xstream.alias("Power", Power.class);
		xstream.alias("Agent", Agent.class);
		xstream.alias("Ammunition", Ammunition.class);
		xstream.alias("Armor", Armor.class);
		xstream.alias("Augmentation", Augmentation.class);
		xstream.alias("AugmentGrade", Augmentation.Grade.class);
		xstream.alias("Deck", Deck.class);
		xstream.alias("Gear", Gear.class);
		xstream.alias("MeeleWeapon", MeeleWeapon.class);
		xstream.alias("RangeWeapon", RangedWeapon.class);
		xstream.alias("Ammunitiontype", AmmoType.class);
		xstream.alias("FireMode", Mode.class);
		xstream.alias("Vehicle", Vehicle.class);
		xstream.alias("CraftType", Vehicle.CraftType.class);
		xstream.alias("WeaponModification", WeaponModification.class);
		xstream.alias("ModificiationMountPoint", WeaponModification.MountPoint.class);
		xstream.alias("Grenade", Grenade.class);
	}
	
	public void skillInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadSkillList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveSkillList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addSkill();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printSkillList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	
	public void addSkill(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Skill newSkill = new Skill();
			System.out.println("Adding new Skill to List\nPlease insert skill name:\n");
			newSkill.setName(input.readLine());
			System.out.println("Please insert linked attibute:\n");
			String s = input.readLine();
			if (s.equals("bod")){
				newSkill.setAttribute(Skill.Attribute.BODY);
			} else if (s.equals("agi")){
				newSkill.setAttribute(Skill.Attribute.AGILITY);
			} else if (s.equals("rea")){
				newSkill.setAttribute(Skill.Attribute.REACTION);
			} else if (s.equals("str")){
				newSkill.setAttribute(Skill.Attribute.STRENGTH);
			} else if (s.equals("wil")){
				newSkill.setAttribute(Skill.Attribute.WILLPOWER);
			} else if (s.equals("log")){
				newSkill.setAttribute(Skill.Attribute.LOGIC);
			} else if (s.equals("int")){
				newSkill.setAttribute(Skill.Attribute.INTUITION);
			} else if (s.equals("cha")){
				newSkill.setAttribute(Skill.Attribute.CHARISMA);
			} else if (s.equals("mag")){
				newSkill.setAttribute(Skill.Attribute.MAGIC);
			} else if (s.equals("res")){
				newSkill.setAttribute(Skill.Attribute.RESONANCE);
			} 
			System.out.println("Is this a knowledge skill?\n");
			s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				newSkill.setKnowledge(true);
			} else {
				newSkill.setKnowledge(false);
			}
			System.out.println("Do you wish to enter a skill description?\n");
			s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				System.out.println("Please insert description:\n");
				newSkill.setNotes(input.readLine());
			}
			skillList.add(newSkill);
			System.out.println("Skill '"+newSkill.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding Skill!");
		}
	}
	
	public void printSkillList(){
		for (Skill s:this.skillList){
			System.out.println(this.skillList.indexOf(s)+": "+s.getName()+"/"+s.getAttribute()+"\n");
		}
	}
	
	public void loadSkillList() {
		try{
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/skillList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
					pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/skillList.xml");
			File load = new File(pathText);
			this.skillList = (ArrayList<Skill>)xstream.fromXML(load);	
		} catch (Exception e){
				e.printStackTrace();
		}
		
	}
	
	public void saveSkillList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/skillList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
					pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/skillList.xml");
			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.skillList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}
	
	
	public void skillgroupInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadSkillGroupList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveSkillGroupList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addSkillGroup(SLR);
				} else if (s.equals("print")||s.equals("p")){
					SLR.printSkillGroupList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	
	
	public void addSkillGroup(ListRoutine SLR){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			SkillGroup newSkillGroup = new SkillGroup();
			System.out.println("Adding new SkillGroup to List\nPlease insert skillgroup name:\n");
			newSkillGroup.setName(input.readLine());
			boolean quit = false;
			String index;
			SLR.loadSkillList();
			SLR.printSkillList();
			try {
				while (!quit){
					System.out.println("Please insert index of skill to add, or type d(one) when done.");
					index = input.readLine();
					if (index.equals("d")||index.equals("done")){
						quit=true;
					} else if (Integer.parseInt(index)<SLR.skillList.size()){
						newSkillGroup.addSkill(SLR.skillList.get(Integer.parseInt(index)));
					}
				}
			} catch (IOException e){
				System.err.println("Failure processing input!");
			}
			skillGroupList.add(newSkillGroup);
			System.out.println("SkillGroup '"+newSkillGroup.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding SkillGroup!");
		}
	}
	
	public void printSkillGroupList(){
		for (SkillGroup sg:this.skillGroupList){
			System.out.println(sg.getName());
			for (Skill s:sg.getSkills()){
				System.out.println("\t"+s.getName());
			}
		}
	}
	
	public void loadSkillGroupList() {
		try{
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/skillGroupList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
					pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/skillGroupList.xml");
			File load = new File(pathText);
			this.skillGroupList = (ArrayList<SkillGroup>)xstream.fromXML(load);	
		} catch (Exception e){
				e.printStackTrace();
		}
		//File load = new File("data/skillGroupList.xml");
		//this.skillGroupList = (ArrayList<SkillGroup>)xstream.fromXML(load);	
	}
	
	public void saveSkillGroupList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/skillGroupList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
					pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/skillGroupList.xml");
			saveFile=new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.skillGroupList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}
	
	
	
	public void qualityInputLoop(ListRoutine SLR){
		
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadQualityList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveQualityList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addQuality();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printQualityList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	
	public void addQuality(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Quality newQual = new Quality();
			System.out.println("Adding new Quality to List\nPlease insert quality name:\n");
			newQual.setName(input.readLine());
			System.out.println("Is this a leveled quality?\n");
			String s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				newQual.setLeveled(true);
				System.out.println("Please enter maximum level\n");
				newQual.setMaxLevel(Integer.parseInt(input.readLine()));
			} else {
				newQual.setLeveled(false);
				newQual.setMaxLevel(-1);
			}
			System.out.println("Please insert AP cost/bonus: (per Level)\n");
			newQual.setAPcost_bonus(Integer.parseInt(input.readLine()));
			System.out.println("Is this a positve quality (as opposed to a handicap)?\n");
			s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				newQual.setPositive(true);
			} else {
				newQual.setPositive(false);
			}
			System.out.println("Do you wish to enter a quality description?\n");
			s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				System.out.println("Please insert description:\n");
				newQual.setNotes(input.readLine());
			}
			qualityList.add(newQual);
			System.out.println("Quality '"+newQual.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding quality!");
		}
	}
	
	public void printQualityList(){
		for (Quality s:this.qualityList){
			if (s.isPositive()){
				System.out.println(s.getName()+"(-"+s.getAPcost_bonus()+")\n");
			} else {
				System.out.println(s.getName()+"(+"+s.getAPcost_bonus()+")\n");
			}
		}
	}
	
	public void loadQualityList() {
		try{
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/qualityList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
					pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/qualityList.xml");
			File load = new File(pathText);
			this.qualityList = (ArrayList<Quality>)xstream.fromXML(load);	
		} catch (Exception e){
				e.printStackTrace();
		}
	}
	
	public void saveQualityList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/skillList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
					pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/qualityList.xml");
			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.qualityList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}

	
	
	public void spellInputLoop(ListRoutine SLR){
		
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadSpellList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveSpellList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addSpell();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printSpellList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	
	public void addSpell(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Spell newSpell = new Spell();
			System.out.println("Adding new Spell to List\nPlease enter Spell name:\n");
			newSpell.setName(input.readLine());
			System.out.println("Please enter spell Set (c)ombat/(d)etection/(h)ealth/(i)llusion/(m)anipulation):\n");
			String s=input.readLine();
			if (s.equals("c")||s.equals("combat")){
				newSpell.setSet(Set.Combat);
			} else if(s.equals("d")||s.equals("detection")){
				newSpell.setSet(Set.Detection);
			} else if(s.equals("h")||s.equals("health")){
				newSpell.setSet(Set.Health);
			} else if(s.equals("i")||s.equals("illusion")){
				newSpell.setSet(Set.Illusion);
			} else if(s.equals("m")||s.equals("manipulation")){
				newSpell.setSet(Set.Manipulation);
			}
			Spell.SubSet subset[]=new Spell.SubSet[2];
			int num=-1;
			System.out.println("Please enter index of first subset:\n");
			if (newSpell.getSet()==Set.Combat){
				System.out.println("1. "+Spell.SubSet.Direct+"\n2. "+Spell.SubSet.Indirect+"\n3. None");
				num=Integer.parseInt(input.readLine());
				System.out.println(num);
				if (num==1){
					subset[0]=Spell.SubSet.Direct;
				} else if (num==2){
					subset[0]=Spell.SubSet.Indirect;
				} else {
					subset[0]=null;
				}
				System.out.println("Please enter index of second subset:\n");
				System.out.println("1. "+Spell.SubSet.Elemental+"\n2. None");
				num=Integer.parseInt(input.readLine());
				System.out.println(num);
				if (num==1){
					subset[1]=Spell.SubSet.Elemental;
				} else {
					subset[1]=null;
				}
				System.out.println("Please enter spell damage (p)hysical, (s)tun:\n");
				s=input.readLine();
				if (s.equals("p")||s.equals("physical")){
					newSpell.setDamage(Damage.Physical);
				} else if(s.equals("s")||s.equals("stun")){
					newSpell.setDamage(Damage.Stun);
				}
			} else if (newSpell.getSet()==Set.Detection){
				System.out.println("1. "+Spell.SubSet.Active+"\n2. "+Spell.SubSet.Passive+"\n3. None");
				num=Integer.parseInt(input.readLine());
				if (num==1){
					subset[0]=Spell.SubSet.Active;
				} else if (num==2){
					subset[0]=Spell.SubSet.Passive;
				} else {
					subset[0]=null;
				}
				System.out.println("Please enter index of second subset:\n");
				System.out.println("1. "+Spell.SubSet.Directional+"\n2. "+Spell.SubSet.Area+"\n3. "+Spell.SubSet.ExtendedArea+"\n4. "+Spell.SubSet.Psychic+"\n5. None");
				num=Integer.parseInt(input.readLine());
				if (num==1){
					subset[1]=Spell.SubSet.Directional;
				} else if (num==2){
					subset[1]=Spell.SubSet.Area;
				} else if (num==3){
					subset[1]=Spell.SubSet.ExtendedArea;
				} else if (num==4){
					subset[1]=Spell.SubSet.Psychic;
				} else {
					subset[1]=null;
				}
			}  else if (newSpell.getSet()==Set.Health){
				System.out.println("1. "+Spell.SubSet.Essence+"\n2. None");
				num=Integer.parseInt(input.readLine());
				if (num==1){
					subset[0]=Spell.SubSet.Essence;
				} else {
					subset[0]=null;
				}
				subset[1]=null;
			} else if (newSpell.getSet()==Set.Illusion){
				System.out.println("1. "+Spell.SubSet.Obvious+"\n2. "+Spell.SubSet.Realistic+"\n3. None");
				num=Integer.parseInt(input.readLine());
				if (num==1){
					subset[0]=Spell.SubSet.Obvious;
				} else if (num==2){
					subset[0]=Spell.SubSet.Realistic;
				} else {
					subset[0]=null;
				}
				System.out.println("Please enter index of second subset:\n");
				System.out.println("1. "+Spell.SubSet.SingleSense+"\n2. "+Spell.SubSet.MultiSense+"\n3. None");
				num=Integer.parseInt(input.readLine());
				if (num==1){
					subset[1]=Spell.SubSet.SingleSense;
				} else if (num==2){
					subset[1]=Spell.SubSet.MultiSense;
				}else {
					subset[1]=null;
				}
			} else if (newSpell.getSet()==Set.Manipulation){
				System.out.println("1. "+Spell.SubSet.Mental+"\n2. "+Spell.SubSet.Environmental+"\n3. "+Spell.SubSet.Physical+"\n4. None");
				num=Integer.parseInt(input.readLine());
				if (num==1){
					subset[0]=Spell.SubSet.Mental;
				} else if (num==2){
					subset[0]=Spell.SubSet.Environmental;
				} else if (num==3){
					subset[0]=Spell.SubSet.Physical;
				} else {
					subset[0]=null;
				}
				System.out.println("Please enter index of second subset:\n");
				System.out.println("1. "+Spell.SubSet.Damaging+"\n2. None");
				num=Integer.parseInt(input.readLine());
				if (num==1){
					subset[1]=Spell.SubSet.Damaging;
				} else {
					subset[1]=null;
				}
			}
			newSpell.setSubset(subset);
			System.out.println("Please enter spell Type (p/m):\n");
			s=input.readLine();
			if (s.equals("p")||s.equals("physical")){
				newSpell.setType(Type.Physical);
			} else if(s.equals("m")||s.equals("mana")){
				newSpell.setType(Type.Mana);
			}
			System.out.println("Please enter spell Range (los/t):\n");
			s=input.readLine();
			if (s.equals("los")||s.equals("lineofsight")){
				newSpell.setRange(Range.LineOfSight);
			} else if(s.equals("t")||s.equals("touch")){
				newSpell.setRange(Range.Touch);
			}
			System.out.println("Is this a spell with area effect?\n");
			s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				newSpell.setArea(true);
			} else {
				newSpell.setArea(false);
			}
			System.out.println("Please enter spell Duration (i/p/s):\n");
			s=input.readLine();
			if (s.equals("i")||s.equals("instant")){
				newSpell.setDuration(Duration.Instant);
			} else if(s.equals("p")||s.equals("permanent")){
				newSpell.setDuration(Duration.Permanent);
			} else if(s.equals("s")||s.equals("sustain")){
				newSpell.setDuration(Duration.Sustain);
			}
			
			System.out.println("Please enter spell drain (modifactor of power) :\n");
			newSpell.setDrain(Integer.parseInt(input.readLine()));
			
			spellList.add(newSpell);
			System.out.println("Spell '"+newSpell.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding Spell!");
		}
	}
	
	public void printSpellList(){
		for (Spell s:this.spellList){
			System.out.println(s.getName()+"["+s.getSet()+" ("+s.getSubset()[0]+", "+s.getSubset()[1]+") ]"+"(type: "+s.getType()+", range: "+s.getRange()+", duration: "+s.getDuration()+", area: "+s.isArea()+", drain: Force"+s.getDrain()+", dmg: "+s.getDamage()+"\n");
		}
	}
	
	public void loadSpellList() {
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/spellList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/spellList.xml");

			File load = new File(pathText);
			this.spellList = (ArrayList<Spell>)xstream.fromXML(load);	
		} catch(Exception e){
			
		}
	}
	
	public void saveSpellList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/spellList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/spellList.xml");
			
			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.spellList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}

	
	public void ritualInputLoop(ListRoutine SLR){
		
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadRitualList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveRitualList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addRitual();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printRitualList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	
	public void addRitual(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Spell newSpell = new Spell();
			System.out.println("Adding new Ritual to List\nPlease enter Ritual name:\n");
			newSpell.setName(input.readLine());
			Spell.SubSet subset[]=new Spell.SubSet[2];
			int num=-1;
			System.out.println("Please enter index of first subset:\n");
			//Anchored, MaterialLink, Minion, Spell, Spotter
			System.out.println("1. "+Spell.SubSet.Anchored+"\n2. "+Spell.SubSet.MaterialLink+"\n3. "+Spell.SubSet.Minion+"\n4. "+Spell.SubSet.Spell+"\n5. "+Spell.SubSet.Spotter+"\n6. None");
			num=Integer.parseInt(input.readLine());
			if (num==1){
				subset[0]=Spell.SubSet.Anchored;
			} else if (num==2){
				subset[0]=Spell.SubSet.MaterialLink;
			} else if (num==3){
				subset[0]=Spell.SubSet.Minion;
			} else if (num==4){
				subset[0]=Spell.SubSet.Spell;
			} else if (num==5){
				subset[0]=Spell.SubSet.Spotter;
			} else {
				subset[0]=null;
			}
			System.out.println("Please enter index of second subset:\n");
			System.out.println("1. "+Spell.SubSet.Anchored+"\n2. "+Spell.SubSet.MaterialLink+"\n3. "+Spell.SubSet.Minion+"\n4. "+Spell.SubSet.Spell+"\n5. "+Spell.SubSet.Spotter+"\n6. None");
			num=Integer.parseInt(input.readLine());
			if (num==1){
				subset[1]=Spell.SubSet.Anchored;
			} else if (num==2){
				subset[1]=Spell.SubSet.MaterialLink;
			} else if (num==3){
				subset[1]=Spell.SubSet.Minion;
			} else if (num==4){
				subset[1]=Spell.SubSet.Spell;
			} else if (num==5){
				subset[1]=Spell.SubSet.Spotter;
			} else {
				subset[1]=null;
			}
			newSpell.setSubset(subset);
			
			ritualList.add(newSpell);
			System.out.println("Ritual '"+newSpell.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding Ritual!");
		}
	}
	
	public void printRitualList(){
		for (Spell s:this.ritualList){
			System.out.println(s.getName()+"["+s.getSubset()[0]+", "+s.getSubset()[1]+"]\n");
		}
	}
	
	public void loadRitualList() {
		try{
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/ritualList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/ritualList.xml");

			File load = new File(pathText);
			this.ritualList = (ArrayList<Spell>)xstream.fromXML(load);
		}catch(Exception e){

		}
	}
	
	public void saveRitualList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/ritualList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/ritualList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.ritualList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}

	
	public void compFormInputLoop(ListRoutine SLR){
		
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadCompFormList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveCompFormList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addCompForm();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printCompFormList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	public void addCompForm(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Spell newSpell = new Spell();
			System.out.println("Adding new Complex Form to List\nPlease enter ComplexForm name:\n");
			newSpell.setName(input.readLine());
			//Persona, Device, File, Self, Sprite
			System.out.println("Please enter complex form target (p)ersona, (d)evice, (f)ile, (se)lf, (sp)rite:\n");
			String s=input.readLine();
			if (s.equals("p")||s.equals("persona")){
				newSpell.setType(Type.Persona);
			} else if(s.equals("d")||s.equals("device")){
				newSpell.setType(Type.Device);
			} else if(s.equals("f")||s.equals("file")){
				newSpell.setType(Type.File);
			} else if(s.equals("se")||s.equals("self")){
				newSpell.setType(Type.Self);
			} else if(s.equals("sp")||s.equals("sprite")){
				newSpell.setType(Type.Sprite);
			}
			System.out.println("Please enter spell Duration (i/p/s):\n");
			s=input.readLine();
			if (s.equals("i")||s.equals("instant")){
				newSpell.setDuration(Duration.Instant);
			} else if(s.equals("p")||s.equals("permanent")){
				newSpell.setDuration(Duration.Permanent);
			} else if(s.equals("s")||s.equals("sustain")){
				newSpell.setDuration(Duration.Sustain);
			}
			
			System.out.println("Please enter complex form fading value (modifactor of level) :\n");
			newSpell.setDrain(Integer.parseInt(input.readLine()));
			
			complexFormList.add(newSpell);
			System.out.println("Complex Form '"+newSpell.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding Complex Form!");
		}
	}
	public void printCompFormList(){
		for (Spell s:this.complexFormList){
			System.out.println(s.getName()+"(target: "+s.getType()+", duration: "+s.getDuration()+", drain: Level+"+s.getDrain()+"\n");
		}
	}
	
	public void loadCompFormList() {
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/complexFormList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/complexFormList.xml");

			File load = new File(pathText);
			this.complexFormList = (ArrayList<Spell>)xstream.fromXML(load);
		}catch(Exception e){
			
		}
	}
	
	public void saveCompFormList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/complexFormList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/complexFormList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.complexFormList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}

	
	public void programInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadProgList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveProgList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addProgram();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printProgList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	
	public void addProgram(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Program newProg = new Program();
			System.out.println("Adding new Program to List\nPlease insert Program name:\n");
			newProg.setName(input.readLine());
			System.out.println("Please insert program availability:\n");
			newProg.setAvailability(Integer.parseInt(input.readLine()));
			System.out.println("Please enter availability type. 0 (legal), 1 (restricted), 2 (illegal)\n");
			newProg.setAvailabilityType(Integer.parseInt(input.readLine()));
			System.out.println("Please insert program price:\n");
			newProg.setPrice(Integer.parseInt(input.readLine()));	
			System.out.println("Do you wish to enter a program description?\n");
			String s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				System.out.println("Please insert description:\n");
				newProg.setDescription(input.readLine());
			}
			programList.add(newProg);
			System.out.println("Program '"+newProg.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding Program!");
		}
	}
	
	public void printProgList(){
		for (Program s:this.programList){
			System.out.println(s.getName()+" - "+s.getPrice()+" NuYen\n");
		}
	}
	
	public void loadProgList() {
		try{
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/programList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/programList.xml");

			File load = new File(pathText);
			this.programList = (ArrayList<Program>)xstream.fromXML(load);
		}catch(Exception e){
			
		}
	}
	
	public void saveProgList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/programList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/programList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.programList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}

	
	public void powerInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadPowList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.savePowList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addPower();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printPowList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	
	public void addPower(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Power newPow = new Power();
			System.out.println("Adding new Power to List\nPlease insert Power name:\n");
			newPow.setName(input.readLine());
			System.out.println("Please insert power cost:\n");
			newPow.setCost(Double.parseDouble(input.readLine()));
			System.out.println("Is this Power leveled?");
			String s=input.readLine();
			if (s.equals("y")||s.equals("yes")){
				newPow.setLeveled(true);
			} else {
				newPow.setLeveled(false);
			}
			System.out.println("Do you wish to enter a power description?\n");
			s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				System.out.println("Please insert description:\n");
				newPow.setNotes(input.readLine());
			}
			powerList.add(newPow);
			System.out.println("Power '"+newPow.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding Power!");
		}
	}
	
	public void printPowList(){
		for (Power s:this.powerList){
			System.out.println(s.getName()+" ("+s.getCost()+")\n");
		}
	}
	
	public void loadPowList() {
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/powerList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/powerList.xml");

			File load = new File(pathText);
			this.powerList = (ArrayList<Power>)xstream.fromXML(load);
		} catch(Exception e){
			
		}
	}
	
	public void savePowList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/powerList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/powerList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.powerList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}

	
	public void agentInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd), aP(add from programs) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadAgentList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveAgentList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addAgent();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printAgentList();
				} else if (s.equals("aP")||s.equals("add from programs")){
					SLR.agentFromPrograms();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	
	public void agentFromPrograms(){
		this.loadProgList();
		if (!this.programList.isEmpty()){
			for (Program p:this.programList){
				Agent newAge = new Agent();
				newAge.setName(p.getName()+" Agent");
				newAge.setAvailability(3);
				newAge.setAvailabilityType(p.getAvailabilityType());
				newAge.setLevel(1);
				newAge.setMaxLevel(3);
				newAge.setPrice(1000);
				this.agentList.add(newAge);
				Agent newAge2 = new Agent();
				newAge2.setName(p.getName()+" Agent");
				newAge2.setAvailability(3);
				newAge2.setAvailabilityType(p.getAvailabilityType());
				newAge2.setLevel(4);
				newAge2.setMaxLevel(6);
				newAge2.setPrice(2000);
				this.agentList.add(newAge2);
			}
		}
	}
	
	public void addAgent(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Agent newAge = new Agent();
			System.out.println("Adding new Agent to List\nPlease insert Agent name:\n");
			newAge.setName(input.readLine());
			System.out.println("Please insert starting level for agent in this price range:\n");
			newAge.setLevel(Integer.parseInt(input.readLine()));
			System.out.println("Please insert maximum level for agent in this price range:\n");
			newAge.setMaxLevel(Integer.parseInt(input.readLine()));
			System.out.println("Please insert agent availability (per level):\n");
			newAge.setAvailability(Integer.parseInt(input.readLine()));
			System.out.println("Please enter availability type. 0 (legal), 1 (restricted), 2 (illegal)\n");
			newAge.setAvailabilityType(Integer.parseInt(input.readLine()));
			System.out.println("Please insert agent price (per level):\n");
			newAge.setPrice(Integer.parseInt(input.readLine()));
			System.out.println("Do you wish to enter an agent description?\n");
			String s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				System.out.println("Please insert description:\n");
				newAge.setDescription(input.readLine());
			}
			agentList.add(newAge);
			System.out.println("Agent '"+newAge.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding Agent!");
		}
	}
	
	public void printAgentList(){
		for (Agent s:this.agentList){
			System.out.println(s.getName()+" (level: "+s.getLevel()+"-"+s.getMaxLevel()+" | "+s.getBasePrice()+" NuYen/Level)\n");
		}
	}
	
	public void loadAgentList() {
		try{
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/agentList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/agentList.xml");

		File load = new File(pathText);
		this.agentList = (ArrayList<Agent>)xstream.fromXML(load);
		}catch(Exception e){
			
		}
	}
	
	public void saveAgentList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/agentList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/agentList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.agentList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}

	
	public void ammunitionInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadAmmuList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveAmmuList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addAmmunition();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printAmmuList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	
	public void addAmmunition(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Ammunition newAmmu = new Ammunition();
			System.out.println("Adding new Ammunition to List\nPlease insert ammunition name:\n");
			newAmmu.setName(input.readLine());
			System.out.println("Please insert damage modificator:\n");
			newAmmu.setDamageMod(Integer.parseInt(input.readLine()));
			System.out.println("Does this ammunition inflict stun damage?\n");
			String s=input.readLine();
			if (s.equals("y")||s.equals("yes")){
				newAmmu.setDoesStunDamage(true);
			} else {
				newAmmu.setDoesStunDamage(false);
			}
			System.out.println("Please insert piercing modificator:\n");
			newAmmu.setPiercingMod(Integer.parseInt(input.readLine()));
			System.out.println("Please insert ammunition availability:\n");
			newAmmu.setAvailability(Integer.parseInt(input.readLine()));
			System.out.println("Please enter availability type. 0 (legal), 1 (restricted), 2 (illegal)\n");
			newAmmu.setAvailabilityType(Integer.parseInt(input.readLine()));
			System.out.println("Please insert ammunition price:\n");
			newAmmu.setPrice(Integer.parseInt(input.readLine()));
			ammuList.add(newAmmu);
			System.out.println("Ammunition '"+newAmmu.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding ammunition!");
		}
	}
	
	public void printAmmuList(){
		for (Ammunition s:this.ammuList){
			System.out.println(s.getName()+" (dmg: "+s.getDamageMod()+", prc: "+s.getPiercingMod()+") "+s.getPrice()+" NuYen\n");
		}
	}
	
	public void loadAmmuList() {
		try{
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/ammunitionList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/ammunitionList.xml");

		File load = new File(pathText);
		this.ammuList = (ArrayList<Ammunition>)xstream.fromXML(load);
		}catch(Exception e){
			
		}
	}
	
	public void saveAmmuList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/ammunitionList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/ammunitionList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.ammuList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}

	
	public void armorInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadArmorList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveArmorList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addArmor();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printArmorList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	
	public void addArmor(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Armor newArmor = new Armor();
			System.out.println("Adding new Armor to List\nPlease insert armor name:\n");
			newArmor.setName(input.readLine());
			System.out.println("Please enter armor protection:\n");
			newArmor.setRating(Integer.parseInt(input.readLine()));
			System.out.println("Please insert arrmor availability:\n");
			newArmor.setAvailability(Integer.parseInt(input.readLine()));
			System.out.println("Please enter availability type. 0 (legal), 1 (restricted), 2 (illegal)\n");
			newArmor.setAvailabilityType(Integer.parseInt(input.readLine()));
			System.out.println("Please insert armor price:\n");
			newArmor.setPrice(Integer.parseInt(input.readLine()));
			System.out.println("Is this armor a helmet, shield or a smiliar additional armor item?\n");
			String s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				newArmor.setAddable(true);
			} else {
				newArmor.setAddable(false);
			}
			System.out.println("Do you wish to enter an armor description?\n");
			s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				System.out.println("Please insert description:\n");
				newArmor.setNotes(input.readLine());
			}
			armorList.add(newArmor);
			System.out.println("Armor '"+newArmor.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding armor!");
		}
	}
	
	public void printArmorList(){
		for (Armor s:this.armorList){
			System.out.println(s.getName()+" (protection: "+s.getRating()+") - "+s.getPrice()+" NuYen\n");
		}
	}
	
	public void loadArmorList() {
		try{
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/armorList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/armorList.xml");

		File load = new File(pathText);
		this.armorList = (ArrayList<Armor>)xstream.fromXML(load);
		}catch(Exception e){
			
		}
	}
	
	public void saveArmorList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/armorList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/armorList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.armorList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}

	
	
	public void augmentationInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadAugmentationList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveAugmentationList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addAugmentation();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printAugmentationList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	
	public void addAugmentation(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Augmentation newAugment = new Augmentation();
			System.out.println("Adding new Augmentation to List\nPlease enter augmentation name:\n");
			newAugment.setName(input.readLine());
			System.out.println("Is this augmentation available in several levels?\n");
			String s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				newAugment.setLeveled(true);
			} else {
				newAugment.setLeveled(false);
			}
			System.out.println("Please enter augmentation price (per level if leveled):\n");
			newAugment.setPrice(Integer.parseInt(input.readLine()));
			if (newAugment.isLeveled()){
				System.out.println("Please insert maximum level for augmentation in this price range:\n");
				newAugment.setRating(Integer.parseInt(input.readLine()));
			}
			System.out.println("Please enter augmentation availability:\n");
			newAugment.setAvailability(Integer.parseInt(input.readLine()));
			System.out.println("Please enter availability type. 0 (legal), 1 (restricted), 2 (illegal)\n");
			newAugment.setAvailabilityType(Integer.parseInt(input.readLine()));
			System.out.println("Does this augmentation need to be build into another one?\n");
			s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				newAugment.setcanBeBuildIn(true);
			} else {
				newAugment.setcanBeBuildIn(false);
			}
			System.out.println("Please enter augmentation capacity (capacityCost if built in):\n");
			newAugment.setCapacity(Integer.parseInt(input.readLine()));
			System.out.println("Please enter augmentation essence loss\n");
			newAugment.setEssenceLoss(Double.parseDouble(input.readLine()));
			System.out.println("Do you wish to enter an augmentation description?\n");
			s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				System.out.println("Please insert description:\n");
				newAugment.setNotes(input.readLine());
			}
			augmentList.add(newAugment);
			System.out.println("Augmentation '"+newAugment.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding augmentation!");
		}
	}
	
	public void printAugmentationList(){
		for (Augmentation s:this.augmentList){
			if (s.isLeveled()){
				System.out.println(s.getName()+" (cap: "+s.getCapacity()+", ess:"+s.getEssenceLoss()+") - "+s.getPrice()+" NuYen/level ("+s.getRating()+")\n");
			} else {
				System.out.println(s.getName()+" (cap: "+s.getCapacity()+", ess:"+s.getEssenceLoss()+") - "+s.getPrice()+" NuYen\n");
			}
		}
	}
	
	public void loadAugmentationList() {
		try{
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/augmentList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/augmentList.xml");

		File load = new File(pathText);
		this.augmentList = (ArrayList<Augmentation>)xstream.fromXML(load);
		}catch(Exception e){
			
		}
	}
	
	public void saveAugmentationList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/augmentList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/augmentList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.augmentList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}
	
	
	public void deckInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadDeckList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveDeckList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addDeck();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printDeckList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	public void addDeck(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Deck newDeck = new Deck();
			System.out.println("Adding new Deck to List\nPlease enter deck name:\n");
			newDeck.setModel(input.readLine());
			/*System.out.println("Please enter deck attack:\n");
			newDeck.setAttack(Integer.parseInt(input.readLine()));
			System.out.println("Please enter deck sleaze:\n");
			newDeck.setSleaze(Integer.parseInt(input.readLine()));
			*/
			System.out.println("Please enter deck device rating:\n");
			newDeck.setDeviceRating(Integer.parseInt(input.readLine()));
			/*System.out.println("Please enter deck data processing:\n");
			newDeck.setDataProcessing(Integer.parseInt(input.readLine()));
			System.out.println("Please enter deck firewall:\n");
			newDeck.setFirewall(Integer.parseInt(input.readLine()));
			*/
			int array[]=new int[4];
			System.out.println("Please enter first attribute:\n");
			array[0]=Integer.parseInt(input.readLine());
			System.out.println("Please enter second attribute:\n");
			array[1]=Integer.parseInt(input.readLine());
			System.out.println("Please enter third attribute:\n");
			array[2]=Integer.parseInt(input.readLine());
			System.out.println("Please enter fourth attribute:\n");
			array[3]=Integer.parseInt(input.readLine());
			newDeck.setAttributeArray(array);
			System.out.println("Please enter number of simultaneously executable programs:\n");
			newDeck.setSimultaniousPrograms(Integer.parseInt(input.readLine()));
			System.out.println("Please enter deck availability:\n");
			newDeck.setAvailability(Integer.parseInt(input.readLine()));
			System.out.println("Please enter availability type. 0 (legal), 1 (restricted), 2 (illegal)\n");
			newDeck.setAvailabilityType(Integer.parseInt(input.readLine()));
			System.out.println("Please enter deck price:\n");	
			newDeck.setPrice(Integer.parseInt(input.readLine()));
			deckList.add(newDeck);
			System.out.println("Deck '"+newDeck.getModel()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding deck!");
		}
	}
	public void printDeckList(){
		for (Deck s:this.deckList){
				System.out.println(s.getModel()+" ["+s.getDeviceRating()+"] ("+s.getAttributeArray()[0]+" | "+s.getAttributeArray()[1]+" | "+s.getAttributeArray()[2]+" | "+s.getAttributeArray()[3]+") - "+s.getPrice()+" NuYen\n");
		}
	}
	public void loadDeckList() {
		try{
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/deckList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/deckList.xml");

		File load = new File(pathText);
		this.deckList = (ArrayList<Deck>)xstream.fromXML(load);
		}catch(Exception e){
			
		}
	}
	public void saveDeckList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/deckList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/deckList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.deckList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}
	
	
	public void gearInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadGearList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveGearList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addGear();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printGearList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	public void addGear(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Gear newGear = new Gear();
			System.out.println("Adding new piece of gear to List\nPlease enter gear name:\n");
			newGear.setName(input.readLine());
			System.out.println("Please enter gear price:\n");
			newGear.setPrice(Integer.parseInt(input.readLine()));
			System.out.println("Please enter gear availability:\n");
			newGear.setAvailability(Integer.parseInt(input.readLine()));
			System.out.println("Please enter availability type. 0 (legal), 1 (restricted), 2 (illegal)\n");
			newGear.setAvailabilityType(Integer.parseInt(input.readLine()));
			System.out.println("Please enter gear rating:\n");
			newGear.setRating(Integer.parseInt(input.readLine()));
			System.out.println("Is this gear rating leveled?\n");
			String s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				newGear.setRatingLeveled(true);
			} else {
				newGear.setRatingLeveled(false);
			}
			if (newGear.isRatingLeveled()){
				System.out.println("Please enter gear maximum rating:\n");
				newGear.setMaxRating(Integer.parseInt(input.readLine()));
			}
			System.out.println("Do you wish to enter an gear description?\n");
			s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				System.out.println("Please insert description:\n");
				newGear.setNotes(input.readLine());
			}
			gearList.add(newGear);
			System.out.println("Piece of gear '"+newGear.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding gear!");
		}
	}
	public void printGearList(){
		for (Gear s:this.gearList){
			if (s.isRatingLeveled()){
				System.out.println(s.getName()+" ("+s.getRating()+"-"+s.getMaxRating()+") - Rating*"+s.getBasePrice()+" NuYen\n");	
			} else {
				System.out.println(s.getName()+" ("+s.getRating()+") - "+s.getPrice()+" NuYen\n");
			}
		}
	}
	public void loadGearList() {
		try{
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/gearList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/gearList.xml");

		File load = new File("io/github/unseenwizzard/sr5chargen/data/gearList.xml");
		this.gearList = (ArrayList<Gear>)xstream.fromXML(load);
		}catch(Exception e){
			
		}
	}
	public void saveGearList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/gearList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/gearList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.gearList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}
	
	
	public void meeleWInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadMeeleWpList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveMeeleWpList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addMeeleWp();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printMeeleWpList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	public void addMeeleWp(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			MeeleWeapon newWeapon = new MeeleWeapon();
			System.out.println("Adding new melee weapon to List\nPlease enter weapon name:\n");
			newWeapon.setName(input.readLine());
			System.out.println("Please enter weapon accuracy:\n");
			newWeapon.setAccuracy(Integer.parseInt(input.readLine()));
			System.out.println("Please enter weapon reach:\n");
			newWeapon.setReach(Integer.parseInt(input.readLine()));
			System.out.println("Is this weapons damage dependent of the characters strength?\n");
			String s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				newWeapon.setDependsOnStrength(true);
			} else {
				newWeapon.setDependsOnStrength(false);
			}
			System.out.println("Please enter weapon damage:\n");
			newWeapon.setDamage(Integer.parseInt(input.readLine()));
			System.out.println("Does this weapon inflict stun damage?\n");
			s=input.readLine();
			if (s.equals("y")||s.equals("yes")){
				newWeapon.setDoesStunDamage(true);
			} else {
				newWeapon.setDoesStunDamage(false);
			}
			System.out.println("Please enter weapon AP:\n");
			newWeapon.setAP(Integer.parseInt(input.readLine()));
			System.out.println("Please enter weapon availability:\n");
			newWeapon.setAvailability(Integer.parseInt(input.readLine()));
			System.out.println("Please enter availability type. 0 (legal), 1 (restricted), 2 (illegal)\n");
			newWeapon.setAvailabilityType(Integer.parseInt(input.readLine()));
			System.out.println("Please enter weapon price:\n");
			newWeapon.setPrice(Integer.parseInt(input.readLine()));
			meeleWpList.add(newWeapon);
			System.out.println("Meleeweapon '"+newWeapon.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding weapon!");
		}
	}
	
	public void printMeeleWpList(){
		for (MeeleWeapon s:this.meeleWpList){
				System.out.println(s.getName()+" ("+s.getDamage()+"|"+s.getAccuracy()+"|"+s.getReach()+"|"+s.getAP()+") - "+s.getPrice()+" NuYen\n");
		}
	}
	
	public void loadMeeleWpList() {
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/meeleWeaponList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/meeleWeaponList.xml");

		File load = new File(pathText);
		this.meeleWpList = (ArrayList<MeeleWeapon>)xstream.fromXML(load);	
		}catch(Exception e){
			
		}
	}
	
	public void saveMeeleWpList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/meeleWeaponList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/meeleWeaponList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.meeleWpList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}

	
	
	public void rangedWInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadRangedWpList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveRangedWpList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addRangedWp(SLR);
				} else if (s.equals("print")||s.equals("p")){
					SLR.printRangedWpList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	public void addRangedWp(ListRoutine SLR){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			RangedWeapon newWeapon = new RangedWeapon();
			System.out.println("Adding new ranged weapon to List\nPlease enter weapon name:\n");
			newWeapon.setName(input.readLine());
			System.out.println("Please enter weapon accuracy:\n");
			newWeapon.setAccuracy(Integer.parseInt(input.readLine()));
			System.out.println("Please enter weapon damage:\n");
			newWeapon.setDamage(Integer.parseInt(input.readLine()));
			System.out.println("Does this weapon inflict stun damage?\n");
			String s=input.readLine();
			if (s.equals("y")||s.equals("yes")){
				newWeapon.setDoesStunDamage(true);
			} else {
				newWeapon.setDoesStunDamage(false);
			}
			System.out.println("Please enter weapon armor piercing:\n");
			newWeapon.setArmorPiercing(Integer.parseInt(input.readLine()));
			boolean done = false;
			while(!done){
				System.out.println("Please add weapon mode (ss)singleshot, (sa)semiautomatic, (sb)semiautoburst, (bf)burstfire, (lb)longburs, (fa)fullauto, or type (q)uite when done adding modes:");
				s=input.readLine();
				if (s.equals("ss")||s.equals("singleshot")){
					newWeapon.addMode(Mode.SINGLESHOT);
				} else if (s.equals("sa")||s.equals("semiautomatic")){
					newWeapon.addMode(Mode.SEMIAUTOMATIC);
				} else if (s.equals("sb")||s.equals("semiautoburst")){
					newWeapon.addMode(Mode.SEMIAUTOMATICBURST);
				} else if (s.equals("bf")||s.equals("burstfire")){
					newWeapon.addMode(Mode.BURSTFIRE);
				} else if (s.equals("lb")||s.equals("longburst")){
					newWeapon.addMode(Mode.LONGBURST);
				} else if (s.equals("fa")||s.equals("fullauto")){
					newWeapon.addMode(Mode.FULLAUTO);
				} else if (s.equals("q")||s.equals("quit")){
					done=true;
				}
			}
			System.out.println("Please enter weapon recoil:\n");
			newWeapon.setRecoil(Integer.parseInt(input.readLine()));
			System.out.println("Please enter weapon ammunition:\n");
			newWeapon.setAmmo(Integer.parseInt(input.readLine()));
			System.out.println("Please enter weapon ammotype (c)lip, (b)reakAction, (belt)fed, (m)agazine, (ml)muzzleloader, (cy)linder, (d)rum!");
			s=input.readLine();
			if (s.equals("c")||s.equals("clip")){
				newWeapon.setAmmotype(AmmoType.CLIP);
			} else if (s.equals("b")||s.equals("breakaction")){
				newWeapon.setAmmotype(AmmoType.BREAKACTION);
			} else if (s.equals("belt")||s.equals("beltfed")){
				newWeapon.setAmmotype(AmmoType.BELTFED);
			} else if (s.equals("d")||s.equals("drum")){
				newWeapon.setAmmotype(AmmoType.DRUM);
			} else if (s.equals("m")||s.equals("magazine")){
				newWeapon.setAmmotype(AmmoType.MAGAZINE);
			} else if (s.equals("ml")||s.equals("muzzleloader")){
				newWeapon.setAmmotype(AmmoType.MUZZLELOADER);
			} else if (s.equals("cy")||s.equals("cylinder")){
				newWeapon.setAmmotype(AmmoType.CYLINDER);
			} 
			System.out.println("Please enter weapon availability:\n");
			newWeapon.setAvailability(Integer.parseInt(input.readLine()));
			System.out.println("Please enter availability type. 0 (legal), 1 (restricted), 2 (illegal)\n");
			newWeapon.setAvailabilityType(Integer.parseInt(input.readLine()));
			System.out.println("Please enter weapon price:\n");
			newWeapon.setPrice(Integer.parseInt(input.readLine()));
			System.out.println("Does this weapon have any standard modifications?\n");
			s=input.readLine();
			if (s.equals("y")||s.equals("yes")){
				boolean quit=false;
				SLR.loadWeaponModList();
				SLR.printWeaponModList();
				while (!quit){
					System.out.println("Please insert modifiaction index or type (q)uit when done!");
					s=input.readLine();
					if (s.equals("q")||s.equals("quit")){
						quit=true;
					}else {
						newWeapon.addModification(SLR.wpModList.get(Integer.parseInt(s)));
					}
				}
			}
			rangedWpList.add(newWeapon);
			System.out.println("Ranged weapon '"+newWeapon.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding weapon!");
		}
	}
	public void printRangedWpList(){
		for (RangedWeapon s:this.rangedWpList){
				System.out.println(s.getName()+" ("+s.getAccuracy()+"("+s.getRealAccuracy()+")|"+s.getDamage()+"("+s.getRealDamage()+")|"+s.getArmorPiercing()+"("+s.getRealArmorPiercing()+")|"+s.getAmmo()+"("+s.getRealAmmo()+")) - "+s.getPrice()+" NuYen\n");
		}
	}
	public void loadRangedWpList() {
		try{
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/rangedWeaponList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/rangedWeaponList.xml");

		File load = new File(pathText);
		this.rangedWpList = (ArrayList<RangedWeapon>)xstream.fromXML(load);
		}catch(Exception e){
			
		}
	}
	public void saveRangedWpList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/rangedWeaponList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/rangedWeaponList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.rangedWpList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}

	
	public void weaponmodificationmInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadWeaponModList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveWeaponModList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addWeaponModification();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printWeaponModList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	public void addWeaponModification(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			WeaponModification newWeapMod = new WeaponModification();
			System.out.println("Adding new weapon modification to List\nPlease enter modification name:\n");
			newWeapMod.setName(input.readLine());
			System.out.println("Please enter modification price:\n");
			newWeapMod.setPrice(Integer.parseInt(input.readLine()));
			System.out.println("Please enter modification availability:\n");
			newWeapMod.setAvailability(Integer.parseInt(input.readLine()));
			System.out.println("Please enter availability type. 0 (legal), 1 (restricted), 2 (illegal)\n");
			newWeapMod.setAvailabilityType(Integer.parseInt(input.readLine()));
			System.out.println("Please enter damage modifactor:\n");
			newWeapMod.setDamageMod(Integer.parseInt(input.readLine()));
			System.out.println("Is this for a ranged weapon?\n");
			String s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				System.out.println("Please enter accuracy modifactor:\n");
				newWeapMod.setAccuracyMod(Integer.parseInt(input.readLine()));
				System.out.println("Please enter armor piercing modifactor:\n");
				newWeapMod.setArmorPiercingMod(Integer.parseInt(input.readLine()));
				System.out.println("Please enter recoil modifactor:\n");
				newWeapMod.setRecoilMod(Integer.parseInt(input.readLine()));
				System.out.println("Please enter ammunition modifactor:\n");
				newWeapMod.setAmmoMod(Integer.parseInt(input.readLine()));
				System.out.println("Please enter modification mount point (t)op, (u)nder, (tu)top or under, (b)arrel, (n)one:\n");
				s=input.readLine();
				if (s.equals("t")||s.equals("top")){
					newWeapMod.setMountPoint(WeaponModification.MountPoint.Top);
				} else if (s.equals("u")||s.equals("under")){
					newWeapMod.setMountPoint(WeaponModification.MountPoint.Under);
				} else if (s.equals("tu")||s.equals("topORunder")){
					newWeapMod.setMountPoint(WeaponModification.MountPoint.UnderORTop);
				} else if (s.equals("b")||s.equals("barrel")){
					newWeapMod.setMountPoint(WeaponModification.MountPoint.Barrel);
				} else if (s.equals("n")||s.equals("none")){
					newWeapMod.setMountPoint(WeaponModification.MountPoint.None);
				} 
			} else {
				System.out.println("Please enter reach modifactor:\n");
				newWeapMod.setReachMod(Integer.parseInt(input.readLine()));
			}
			System.out.println("Do you wish to enter a modification description?\n");
			s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				System.out.println("Please insert description:\n");
				newWeapMod.setNotes(input.readLine());
			}
			wpModList.add(newWeapMod);
			System.out.println("Weapon modification '"+newWeapMod.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding gear!");
		}
	}
	public void printWeaponModList(){
		for (WeaponModification s:this.wpModList){
				System.out.println(wpModList.indexOf(s)+". "+s.getName()+" - "+s.getPrice()+" NuYen\n");
		}
	}
	public void loadWeaponModList() {
		try{
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/weaponmodificationList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/weaponmodificationList.xml");

		File load = new File(pathText);
		this.wpModList = (ArrayList<WeaponModification>)xstream.fromXML(load);
		}catch(Exception e){
			
		}
	}
	public void saveWeaponModList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/weaponmodificationList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/weaponmodificationList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.wpModList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}
	
	public void grenadeInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadGrenadeList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveGrenadeList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addGrenade();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printGrenadeList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	public void addGrenade(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Grenade newGrenade = new Grenade();
			System.out.println("Adding new grenade to List\nPlease enter grenade name:\n");
			newGrenade.setName(input.readLine());
			System.out.println("Please enter grenade damage:\n");
			newGrenade.setDamage(Integer.parseInt(input.readLine()));
			System.out.println("Does this grenade inflict stun damage?\n");
			String s=input.readLine();
			if (s.equals("y")||s.equals("yes")){
				newGrenade.setDoesStunDamage(true);
			} else {
				newGrenade.setDoesStunDamage(false);
			}
			System.out.println("Please enter grenade AP:\n");
			newGrenade.setArmorpiercing(Integer.parseInt(input.readLine()));
			System.out.println("Please enter grenade blast radius:\n");
			newGrenade.setBlast(Integer.parseInt(input.readLine()));
			System.out.println("Please enter grenade availability:\n");
			newGrenade.setAvailability(Integer.parseInt(input.readLine()));
			System.out.println("Please enter availability type. 0 (legal), 1 (restricted), 2 (illegal)\n");
			newGrenade.setAvailabilityType(Integer.parseInt(input.readLine()));
			System.out.println("Please enter grenade price:\n");
			newGrenade.setPrice(Integer.parseInt(input.readLine()));
			grenadeList.add(newGrenade);
			System.out.println("New grenade '"+newGrenade.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding gear!");
		}
	}
	public void printGrenadeList(){
		for (Grenade s:this.grenadeList){
				System.out.println(s.getName()+" - "+s.getPrice()+" NuYen\n");
		}
	}
	public void loadGrenadeList() {
		try{
		File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		String pathText = "io/github/unseenwizzard/sr5chargen/data/grenadeList.xml";
		if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
			pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/grenadeList.xml");

		File load = new File(pathText);
		this.grenadeList = (ArrayList<Grenade>)xstream.fromXML(load);	
		}catch(Exception e){
			
		}
	}
	public void saveGrenadeList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/grenadeList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/grenadeList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.grenadeList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}

	
	public void vehicleInputLoop(ListRoutine SLR){
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Type l(oad), s(ave), p(rint), a(dd) or e(xit)");
				s = input.readLine();
				if (s.equals("load")||s.equals("l")){
					SLR.loadVehicleList();
				} else if (s.equals("save")||s.equals("s")){
					SLR.saveVehicleList();
				} else if (s.equals("add")||s.equals("a")){
					SLR.addVehicle();
				} else if (s.equals("print")||s.equals("p")){
					SLR.printVehicleList();
				} else if (s.equals("exit")||s.equals("e")){
					quit=true;
				}
			}
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}
	public void addVehicle(){
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			Vehicle newVehicle = new Vehicle();
			String what = "vehicle/drone";
			System.out.println("Adding new "+what+" to List\nPlease enter "+what+" name:\n");
			newVehicle.setName(input.readLine());
			System.out.println("Is this a (g)roundcraft, (w)atercraft, (a)ircraft, or (d)rone?\n");
			String s = input.readLine();
			if (s.equals("g")||s.equals("groundcraft")){
				newVehicle.setType(Vehicle.CraftType.Groundcraft);
				what="groundcraft";
			} else if (s.equals("w")||s.equals("watercraft")){
				newVehicle.setType(Vehicle.CraftType.Watercraft);
				what="watercraft";
			}  else if (s.equals("a")||s.equals("aircraft")){
				newVehicle.setType(Vehicle.CraftType.Aircraft);
				what="aircraft";
			} else if (s.equals("d")||s.equals("drone")){
				newVehicle.setType(Vehicle.CraftType.Drone);
				what="drone";
			}
			System.out.println("Please enter "+what+" handling:\n");
			newVehicle.setHandling(Integer.parseInt(input.readLine()));
			if (newVehicle.getType()==CraftType.Groundcraft){
				System.out.println("Please enter "+what+" off road handling:\n");
				newVehicle.setHandlingOffRoad(Integer.parseInt(input.readLine()));
			}
			System.out.println("Please enter "+what+" speed:\n");
			newVehicle.setSpeed(Integer.parseInt(input.readLine()));
			if (newVehicle.getType()==CraftType.Groundcraft){
				System.out.println("Please enter "+what+" off road speed:\n");
				newVehicle.setSpeedOffRoad(Integer.parseInt(input.readLine()));
			}
			System.out.println("Please enter "+what+" acceleration:\n");
			newVehicle.setAcceleration(Integer.parseInt(input.readLine()));
			System.out.println("Please enter "+what+" body:\n");
			newVehicle.setBody(Integer.parseInt(input.readLine()));
			System.out.println("Please enter "+what+" armor:\n");
			newVehicle.setArmor(Integer.parseInt(input.readLine()));
			System.out.println("Please enter "+what+" pilot:\n");
			newVehicle.setPilot(Integer.parseInt(input.readLine()));
			System.out.println("Please enter "+what+" sensor:\n");
			newVehicle.setSensor(Integer.parseInt(input.readLine()));
			System.out.println("Please enter "+what+" seats:\n");
			newVehicle.setSeats(Integer.parseInt(input.readLine()));
			System.out.println("Please enter "+what+" availability:\n");
			newVehicle.setAvailability(Integer.parseInt(input.readLine()));
			System.out.println("Please enter availability type. 0 (legal), 1 (restricted), 2 (illegal)\n");
			newVehicle.setAvailabilityType(Integer.parseInt(input.readLine()));
			System.out.println("Please enter "+what+" price:\n");
			newVehicle.setPrice(Integer.parseInt(input.readLine()));
			System.out.println("Do you wish to enter an "+what+" description?\n");
			s = input.readLine();
			if (s.equals("y")||s.equals("yes")){
				System.out.println("Please insert description:\n");
				newVehicle.setNotes(input.readLine());
			}
			vehicleList.add(newVehicle);
			System.out.println("New "+what+" '"+newVehicle.getName()+"' added!");
			System.out.flush();
		} catch (IOException e){
			System.err.println("Failure adding gear!");
		}
	}
	public void printVehicleList(){
		for (Vehicle s:this.vehicleList){
				System.out.println(s.getName()+" ("+s.getType()+") - "+s.getPrice()+" NuYen\n");
		}
	}
	public void loadVehicleList() {
		try{
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/vehicleList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/vehicleList.xml");

		File load = new File(pathText);
		this.vehicleList = (ArrayList<Vehicle>)xstream.fromXML(load);	
		}catch(Exception e){
			
		}
	}
	public void saveVehicleList() {
		FileOutputStream saveFile=null;
		try {
			File path= new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String pathText = "io/github/unseenwizzard/sr5chargen/data/vehicleList.xml";
			if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
				pathText=(path.getAbsolutePath().substring(0, path.getAbsolutePath().length()-14)+ "io/github/unseenwizzard/sr5chargen/data/vehicleList.xml");

			saveFile = new FileOutputStream(pathText);
		} catch (Exception e){
			System.err.println("Failure opening/creating save file!");
		}
		OutputStreamWriter writer = new OutputStreamWriter(saveFile, Charset.forName("UTF-8"));
		xstream.toXML(this.vehicleList, writer);
		try {
			saveFile.close();
		} catch (IOException e){
			
		}
	}
	
	
	public static void main(String[] args) {
		ListRoutine SLR = new ListRoutine();
		boolean quit = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while (!quit){
				System.out.println("Welcome! Please choose which List you'd like to access.\n"
						+ "1. skills\n"
						+ "2. qualities\n"
						+ "3. spells\n"
						+ "4. powers\n"
						+ "5. programs\n"
						+ "6. agents\n"
						+ "7. ammunition\n"
						+ "8. armor\n"
						+ "9. augmentations\n"
						+ "10. gear\n"
						+ "11. meeleweapons\n"
						+ "12. rangedweapons\n"
						+ "13. weaponmodifications\n"
						+ "14. vehicles\n"
						+ "15. skillgroups\n"
						+ "16. decks\n"
						+ "17. grenades\n"
						+ "18. complexforms\n"
						+ "19. rituals\n"
						+ " Type (q)uit to quit.");
				s = input.readLine();
				if (s.equals("skills")||s.equals("1")){
					SLR.skillInputLoop(SLR);
				} else if (s.equals("qualities")||s.equals("2")){
					SLR.qualityInputLoop(SLR);
				} else if (s.equals("spells")||s.equals("3")){
					SLR.spellInputLoop(SLR);
				} else if (s.equals("powers")||s.equals("4")){
					SLR.powerInputLoop(SLR);
				} else if (s.equals("programs")||s.equals("5")){
					SLR.programInputLoop(SLR);
				} else if (s.equals("agents")||s.equals("6")){
					SLR.agentInputLoop(SLR);
				} else if (s.equals("ammunition")||s.equals("7")){
					SLR.ammunitionInputLoop(SLR);
				} else if (s.equals("armor")||s.equals("8")){
					SLR.armorInputLoop(SLR);
				} else if (s.equals("augmentations")||s.equals("9")){
					SLR.augmentationInputLoop(SLR);
				} else if (s.equals("gear")||s.equals("10")){
					SLR.gearInputLoop(SLR);
				} else if (s.equals("meeleweapons")||s.equals("11")){
					SLR.meeleWInputLoop(SLR);
				} else if (s.equals("rangedweapons")||s.equals("12")){
					SLR.rangedWInputLoop(SLR);
				} else if (s.equals("weaponmodifications")||s.equals("13")){
					SLR.weaponmodificationmInputLoop(SLR);
				} else if (s.equals("vehicles")||s.equals("14")){
					SLR.vehicleInputLoop(SLR);
				} else if (s.equals("skillgroups")||s.equals("15")){
					SLR.skillgroupInputLoop(SLR);
				} else if (s.equals("decks")||s.equals("16")){
					SLR.deckInputLoop(SLR);
				} else if (s.equals("grenades")||s.equals("17")){
					SLR.grenadeInputLoop(SLR);
				} else if (s.equals("complexforms")||s.equals("18")){
					SLR.compFormInputLoop(SLR);
				} else if (s.equals("ritual")||s.equals("19")){
					SLR.ritualInputLoop(SLR);
				} else if (s.equals("quit")||s.equals("q")){
					quit=true;
				}
			}
			input.close();
		} catch (IOException e){
			System.err.println("Failure processing input!");
		}
	}

	public ArrayList<Skill> getSkillList() {
		return skillList;
	}

	public ArrayList<SkillGroup> getSkillGroupList() {
		return skillGroupList;
	}

	public ArrayList<Quality> getQualityList() {
		return qualityList;
	}

	public ArrayList<Spell> getSpellList() {
		return spellList;
	}
	
	public ArrayList<Spell> getRitualList() {
		return ritualList;
	}
	
	public ArrayList<Spell> getComplexFormList() {
		return complexFormList;
	}

	public ArrayList<Program> getProgramList() {
		return programList;
	}

	public ArrayList<Power> getPowerList() {
		return powerList;
	}

	public ArrayList<Agent> getAgentList() {
		return agentList;
	}

	public ArrayList<Ammunition> getAmmuList() {
		return ammuList;
	}

	public ArrayList<Armor> getArmorList() {
		return armorList;
	}

	public ArrayList<Augmentation> getAugmentList() {
		return augmentList;
	}

	public ArrayList<Deck> getDeckList() {
		return deckList;
	}

	public ArrayList<Gear> getGearList() {
		return gearList;
	}

	public ArrayList<MeeleWeapon> getMeeleWpList() {
		return meeleWpList;
	}

	public ArrayList<RangedWeapon> getRangedWpList() {
		return rangedWpList;
	}

	public ArrayList<WeaponModification> getWpModList() {
		return wpModList;
	}

	public ArrayList<Vehicle> getVehicleList() {
		return vehicleList;
	}

	public ArrayList<Grenade> getGrenadeList() {
		return grenadeList;
	}
	
	
}
