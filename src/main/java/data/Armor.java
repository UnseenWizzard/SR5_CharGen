package data;

public class Armor extends Gear{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean addable;
	
	@Override
	public String toString() {
		String avType="L";
		if (super.getAvailabilityType()==1){
			avType="R";
		} else if (super.getAvailabilityType()==2){
			avType="F";
		}
		String add="";
		if (addable){
			add="+";
		}
		return  super.getName() + " ( "+add+ super.getRating() + ") "+super.getAvailability()+" "+avType+" - "+super.getPrice()+"NuYen";
	}
	
	public Armor(){
		super();
	}
	
	public Armor(String name, int rating, int price, boolean addable){
		super(name,rating,price);
		this.addable=addable;
	}

	public boolean isAddable() {
		return addable;
	}

	public void setAddable(boolean addable) {
		this.addable = addable;
	}	
	

}
