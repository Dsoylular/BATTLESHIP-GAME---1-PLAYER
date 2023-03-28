package models;

public class Ship {
	
	private String name;
	private double health;
	private Location location;
	private boolean isAlive;
	
	private int xwidth;
	private int ywidth;
	
	public Ship(String name, int xwidth, int ywidth, Location location) {
		
		this.name = name;
		this.health = 100;
		this.isAlive = true;
		this.xwidth = xwidth;
		this.ywidth = ywidth;
		this.location = location;
	}
	
	public void reduceHealth(double damageScore) {
		
		health -= damageScore;
		
		if(health < 1) {
			isAlive = false;
		}
	}
	

	public String getName() {
		return name;
	}

	public double getHealth() {
		return health;
	}
	public String getHealth(String string) {
		String endString = "  " + health + " [ ";
		int i  = 0;
		
		while(i < health / 10) {
			i++;
			endString += "\u25A0";
		}
		while(i < 10) {
			i++;
			endString += "\u25A1";
		}
		
		return endString + " ]";
	}

	public Location getLocation() {
		return location;
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	public int getWidth() {
		return xwidth;
	}

	public int getHeight() {
		return ywidth;
	}
	

	public void setLocation(Location location) {
		this.location = location;
	}
	

	public String toString() {
		return "Ship [name=" + name + ", isAlive=" + isAlive + ", health=" + health + ", location=" + 
				location + ", width=" + xwidth + ", height=" + ywidth + "]\n";
	}
	
	
}
