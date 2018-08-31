package io.github.unseenwizzard.sr5chargen.utils;

import io.github.unseenwizzard.sr5chargen.utils.dice.DieRoll;
import io.github.unseenwizzard.sr5chargen.utils.dice.DieRoller;

public class RandomRunGenerator {
	
	private static String genVenue(int num){
		String venue="The runners go to a meeting ";
		switch(num){
		case (1):
			venue+="at a bar, club, or restaurant ";
		break;
		case (2):
			venue+="at a warehouse, dock, factory or another scarcely frequented place ";
		break;
		case (3):
			venue+="in the Barrens or a similiar urban hellhole ";
		break;
		case (4):
			venue+="in a moving vehicle ";
		break;
		case (5):
			venue+="in the matrix ";
		break;
		case (6):
			venue+="in the astral plane ";
		break;
		}
		venue+="for their next job.\n";
		return venue;
	}
	
	private static String genEmployer(int num){
		String employer="";
		switch(num){
		case (2):
			employer+="A secret society (e.g. Black Lodge, Human Nation) ";
		break;
		case (3):
			employer+="A political group or activists (e.g. Humanis Policlub, Mothers of Metahumans) ";
		break;
		case (4):
			employer+="A government agency ";
		break;
		case (5):
			employer+="A small corporation (A-corp or smaller) ";
		break;
		case (6):
			employer+="A small corporation (A-corp or smaller) ";
		break;
		case (7):
			employer+="A megacorporation (AA-corp or bigger) ";
		break;
		case (8):
			employer+="A megacorporation (AA-corp or bigger) ";
		break;
		case (9):
			employer+="A crime syndicate (e.g. Yakuza, Mafia) ";
		break;
		case (10):
			employer+="A magical group (e.g. Illuminates of the New Dawn) ";
		break;
		case (11):
			employer+="A private individual ";
		break;
		case (12):
			employer+="An exotic or mysterious being (e.g. free spirit, dragon, AI) ";
		break;
		}
		employer+="hires them to ";
		return employer;
	}
	
	private static String genType(int num){
		String string="";
		switch(num){
		case (1):
			string+="steal data from ";
		break;
		case (2):
			string+="assasinate or destroy ";
		break;
		case (3):
			string+="extract or infiltrate ";
		break;
		case (4):
			string+="distract ";
		break;
		case (5):
			string+="provide protection for ";
		break;
		case (6):
			string+="transport ";
		break;
		}
		return string;
	}

	private static String genTarget(int num){
		String string="";
		switch(num){
		case (1):
			string+="an important employee";
		break;
		case (2):
			string+="a prototype";
		break;
		case (3):
			string+="revolutionary research findings";
		break;
		case (4):
			string+="a genetically modified life-form";
		break;
		case (5):
			string+="a magical artefact";
		break;
		case (6):
			string+="a building, rural location or public building";
		break;
		}
		string+=".\n";
		return string;
	}
	
	private static String genComplication(int num){
		String string="The run gets complicated when ";
		switch(num){
		case (1):
			string+="security is higher than expected";
		break;
		case (2):
			string+="a third party is also interested";
		break;
		case (3):
			string+="the target is not what it seemed to be. (Employer lied)";
		break;
		case (4):
			string+="the runners figure out that they need a special piece of equipment for the job";
		break;
		case (5):
			string+="the target is being or has been moved to another location";
		break;
		case (6):
			string+="the Johnson pulls a fast one on the team and tries to ripp them off";
		break;
		}
		string+=".\n";
		return string;
	}
	
	public static String generateRandomRun (){
		String run = "";
		DieRoller roller=new DieRoller();
		DieRoll dice = roller.rollDice(1);
		System.out.println(dice.getDice());
		run+=genVenue(dice.getDice().get(0).getRolledValue());
		DieRoll t = roller.rollDice(2);
		run+=genEmployer((int)t.getNumberOfSuccesses() + t.getDice().get(0).getRolledValue());
		dice=roller.rollDice(1);
		System.out.println(dice.getDice().get(0).getRolledValue());
		run+=genType(dice.getDice().get(0).getRolledValue());
		dice=roller.rollDice(1);
		System.out.println(dice.getDice().get(0).getRolledValue());
		run+=genTarget(dice.getDice().get(0).getRolledValue());
		dice=roller.rollDice(1);
		System.out.println(dice.getDice().get(0).getRolledValue());
		run+=genComplication(dice.getDice().get(0).getRolledValue());
		
		return run;
	}
}
