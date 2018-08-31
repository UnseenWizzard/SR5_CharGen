package data;

import java.io.Serializable;

public class Contact implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int loyalty, connection,favors;
	
	@Override
	public String toString(){
		return name;
	}
	
	public Contact(){
		this.name=new String();
		this.loyalty=0;
		this.connection=0;
		this.favors=0;
	}
	
	public Contact(String name, int Loyalty, int Connection, int Favor){
		this.name=name;
		this.loyalty=Loyalty;
		this.connection=Connection;
		this.favors=Favor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLoyalty() {
		return loyalty;
	}

	public void setLoyalty(int loyalty) {
		this.loyalty = loyalty;
	}

	public int getConnection() {
		return connection;
	}

	public void setConnection(int connection) {
		this.connection = connection;
	}

	public int getFavors() {
		return favors;
	}

	public void setFavors(int favor) {
		this.favors = favor;
	}


	
}
