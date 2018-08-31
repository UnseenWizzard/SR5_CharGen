package io.github.unseenwizzard.sr5chargen.data.character;

import java.io.Serializable;

public class Power implements Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String name;
		private int level;
		private String notes;
		private boolean isLeveled;
		private double PPcost;
		@Override
		public String toString(){
			if (isLeveled&&level>0)
				return this.name + " "+this.level+" ("+this.getCost()+")";
			return this.name+" ("+this.getCost()+")";
		}
		
		@Override
		public boolean equals(Object obj){
			if (obj instanceof Power){
				if (((Power)obj).getName().equals(this.getName())){
					return true;
				}
			}
			return false;
		}
		
		public void setLeveled(boolean isLeveled){
			this.isLeveled=isLeveled;
		}
		
		public boolean isLeveled(){
			return this.isLeveled;
		}
		
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int Level) {
			this.level = Level;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		public Power(){
			this.name = new String();
			this.PPcost=0;
			this.notes = new String();
		}
		
		public Power(String name, int APcost, int GPcost){
			this.name = name;
			this.notes=new String();
			this.PPcost=APcost;
		}

		public double getCost() {
			if (this.isLeveled()&&this.level>0){
				return PPcost*this.level;
			}
			return PPcost;
		}

		public void setCost(double cost) {
			PPcost = cost;
		}
}
