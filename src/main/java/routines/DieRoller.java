package routines;

import java.util.Random;


public class DieRoller {
	
	private Random random;
	
	public DieRoller(){
		this.generateRandomGenerator();
	}
	
	public void generateRandomGenerator(){
		this.random = new Random(System.currentTimeMillis());
	}
	
	/*
	 * [0]:
	 * 		0... Okay
	 * 		1... Glitch!
	 * 		2... CriticalGlitch!
	 * [1]... number of succesfull rolls
	 * [2-numOfDice]... all rolled numbers
	 */
	public int[] rollDice(int numOfDice){
		int[] roll= new int[numOfDice+2];
		for (int i=0;i<numOfDice;i++){
			roll[i+2]=random.nextInt(6)+1;
			System.out.println(roll[i+2]);
		}
		int fiveSixCount=0;
		int oneCount=0;
		roll[0]=0;
		for (int i=2;i<numOfDice+2;i++){
			if (roll[i]==1){
				oneCount++;
			} else if (roll[i]==6 || roll[i]==5){
				fiveSixCount++;
			}
		}
		if (oneCount>numOfDice/2){
			roll[0]=1;
			if (fiveSixCount==0){
				roll[0]=2;
			}
		}
		roll[1]=fiveSixCount;
		return roll;
	}
	
	

}