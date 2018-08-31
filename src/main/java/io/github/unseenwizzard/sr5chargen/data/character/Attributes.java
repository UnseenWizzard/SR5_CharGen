package io.github.unseenwizzard.sr5chargen.data.character;

import java.io.Serializable;

public class Attributes implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int body,
	agility,
	reaction,
	strength,
	willpower,
	logic,
	intuition,
	charisma,
	edge,
	magic,
	resonance,
	initiative,
	matrixInitiative,
	astralInitiative,
	composure,
	judgeIntentions,
	memory,
	liftCarry,
	run,
	walk,
	physicalLimit,
	mentalLimit,
	socialLimit,
	
	
	MAXbody,
	MAXagility,
	MAXreaction,
	MAXstrength,
	MAXwillpower,
	MAXlogic,
	MAXintuition,
	MAXcharisma,
	MAXedge,
	MAXmagic,
	MAXresonance = 0;
	double essence=6.0;
	double adeptPowerPoints=0.0;
	int iniDice;
	
	public int getIniDice(){
		return this.iniDice;
	}
	public void setIniDice(int iniDice){
		this.iniDice=iniDice;
	}
	
	public void evaluate(){
		this.initiative=intuition+reaction;
		this.astralInitiative=intuition*2;
		this.matrixInitiative=intuition+reaction;
		double t=(double)(strength*2+body+reaction);
		t/=3;
		this.physicalLimit=(int)Math.ceil(t);
		t=(double)(logic*2+intuition+willpower);
		t/=3;
		this.mentalLimit=(int)Math.ceil(t);
		t=(double)(charisma*2+willpower+essence);
		t/=3;
		this.socialLimit=(int)Math.ceil(t);
		this.composure=charisma+willpower;
		this.judgeIntentions=charisma+intuition;
		this.liftCarry=body+strength;
		this.memory=willpower+logic;
		this.walk=agility*2;
		this.run=agility*4;
		if (this.MAXmagic>0){
			this.MAXmagic=(int)Math.floor(this.essence);
			if (this.magic>this.MAXmagic){
				this.magic=this.MAXmagic;
			}
			if (this.adeptPowerPoints>0){
				this.adeptPowerPoints=this.magic;
			}
		}
		
	}
	
	public int getPhysicalLimit() {
		this.evaluate();
		return physicalLimit;
	}

	public void setPhysicalLimit(int physicalLimit) {
		this.physicalLimit = physicalLimit;
	}

	public int getMentalLimit() {
		this.evaluate();
		return mentalLimit;
	}

	public void setMentalLimit(int mentalLimit) {
		this.mentalLimit = mentalLimit;
	}

	public int getSocialLimit() {
		this.evaluate();
		return socialLimit;
	}

	public void setSocialLimit(int socialLimit) {
		this.socialLimit = socialLimit;
	}

	public int getBody() {
		return body;
	}

	public void setBody(int body) {
		this.body = body;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getReaction() {
		return reaction;
	}

	public void setReaction(int reaction) {
		this.reaction = reaction;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getWillpower() {
		return willpower;
	}

	public void setWillpower(int willpower) {
		this.willpower = willpower;
	}

	public int getLogic() {
		return logic;
	}

	public void setLogic(int logic) {
		this.logic = logic;
	}

	public int getIntuition() {
		return intuition;
	}

	public void setIntuition(int intuition) {
		this.intuition = intuition;
	}

	public int getCharisma() {
		return charisma;
	}

	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}

	public int getEdge() {
		return edge;
	}

	public void setEdge(int edge) {
		this.edge = edge;
	}

	public double getEssence() {
		return essence;
	}

	public void setEssence(double essence) {
		this.essence = essence;
	}

	public int getMagic() {
		this.evaluate();
		return magic;
	}

	public void setMagic(int magic) {
		this.magic = magic;
	}

	public int getResonance() {
		return resonance;
	}

	public void setResonance(int resonance) {
		this.resonance = resonance;
	}

	public int getInitiative() {
		this.evaluate();
		return initiative;
	}

	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	public int getMatrixInitiative() {
		this.evaluate();
		return matrixInitiative;
	}

	public void setMatrixInitiative(int matrixInitiative) {
		this.matrixInitiative = matrixInitiative;
	}

	public int getAstralInitiative() {
		this.evaluate();
		return astralInitiative;
	}

	public void setAstralInitiative(int astralInitiative) {
		this.astralInitiative = astralInitiative;
	}

	public int getComposure() {
		this.evaluate();
		return composure;
	}

	public void setComposure(int composure) {
		this.composure = composure;
	}

	public int getJudgeIntentions() {
		this.evaluate();
		return judgeIntentions;
	}

	public void setJudgeIntentions(int judgeIntentions) {
		this.judgeIntentions = judgeIntentions;
	}

	public int getMemory() {
		this.evaluate();
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getLiftCarry() {
		this.evaluate();
		return liftCarry;
	}

	public void setLiftCarry(int liftCarry) {
		this.liftCarry = liftCarry;
	}

	public String getMovement() {
		this.evaluate();
		return walk+"/"+run;
	}

	public int getWalk(){
		this.evaluate();
		return walk;
	}
	public int getRun(){
		this.evaluate();
		return run;
	}

	public int getMAXbody() {
		return MAXbody;
	}

	public void setMAXbody(int mAXbody) {
		MAXbody = mAXbody;
	}

	public int getMAXagility() {
		return MAXagility;
	}

	public void setMAXagility(int mAXagility) {
		MAXagility = mAXagility;
	}

	public int getMAXreaction() {
		return MAXreaction;
	}

	public void setMAXreaction(int mAXreaction) {
		MAXreaction = mAXreaction;
	}

	public int getMAXstrength() {
		return MAXstrength;
	}

	public void setMAXstrength(int mAXstrength) {
		MAXstrength = mAXstrength;
	}

	public int getMAXwillpower() {
		return MAXwillpower;
	}

	public void setMAXwillpower(int mAXwillpower) {
		MAXwillpower = mAXwillpower;
	}

	public int getMAXlogic() {
		return MAXlogic;
	}

	public void setMAXlogic(int mAXlogic) {
		MAXlogic = mAXlogic;
	}

	public int getMAXintuition() {
		return MAXintuition;
	}

	public void setMAXintuition(int mAXintuition) {
		MAXintuition = mAXintuition;
	}

	public int getMAXcharisma() {
		return MAXcharisma;
	}

	public void setMAXcharisma(int mAXcharisma) {
		MAXcharisma = mAXcharisma;
	}

	public int getMAXedge() {
		return MAXedge;
	}

	public void setMAXedge(int mAXedge) {
		MAXedge = mAXedge;
	}

	public int getMAXmagic() {
		return MAXmagic;
	}

	public void setMAXmagic(int mAXmagic) {
		MAXmagic = mAXmagic;
	}

	public int getMAXresonance() {
		return MAXresonance;
	}

	public void setMAXresonance(int mAXresonance) {
		MAXresonance = mAXresonance;
	}

	public Attributes(){
		
	}
	public double getAdeptPowerPoints() {
		this.evaluate();
		return adeptPowerPoints;
	}
	public void setAdeptPowerPoints(double adeptPowerPoints) {
		this.adeptPowerPoints = adeptPowerPoints;
	}
	
	
}
