package models;
 
 
public class BattleshipArena {

	private char[][] xySpace;
	private Ship[] shipList;
	private static int shipcounter = 0;
	private int xlength;
	private int ylength;
	private static int score = 0;

	public BattleshipArena(int xlength, int ylength) {
		
		this.xlength = xlength;
		this.ylength = ylength;
		this.shipList = new Ship[12];
		this.xySpace = new char[xlength][ylength];
		
		initXYspace(xySpace);
	}

	public void drawArena() {
		char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'};
		
		System.out.print("\t\t\t\t   BATTLESHIP GAME\n\n\n\t\t\t\t  ");
		for (int j = 0; j < ylength; j++) {
			System.out.print(j + " ");
		}
		System.out.println("");
		for (int i = 0; i < xlength; i++) {
			System.out.printf("\t\t\t\t%s ",letters[i]);
			for (int j = 0; j < ylength; j++) {
				if(xySpace[i][j] == 'X') {
					System.out.print("X ");
				}
				else if(xySpace[i][j] == '\u25FC') {
					System.out.print("\u25FC ");
				}
				else {
					System.out.print(". ");
					
				}
			}
			System.out.println();
		}
		//System.out.println("\t\tv");
		//System.out.println("\t\tx axis\n");
	}
	public void drawArena(boolean truth) {
		
		char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'};
		
		System.out.print("\t\t\tBATTLESHIP GAME\n\n\n\t\t\t\t  ");
		for (int j = 0; j < ylength; j++) {
			System.out.print(j + " ");
		}
		System.out.println("");
		for (int i = 0; i < xlength; i++) {
			System.out.printf("\t\t\t\t%s ",letters[i]);
			for (int j = 0; j < ylength; j++) {
				System.out.print(xySpace[i][j] + " ");
			}
			System.out.println();
		}
		//System.out.println("\t\tv");
		//System.out.println("\t\tx axis\n");
	}
	public void initXYspace(char[][] xySpace) {
		
		for(int i = 0; i < xySpace.length; i++) {
			for(int j = 0; j < xySpace[0].length; j++) {
				xySpace[i][j] = '.';
			}
		}
	}


	public boolean addShip(Ship ship, Location location) {
		try {
			
			if(shipcounter == shipList.length) {
				
				//System.err.println("There can be no more ships!\n");
				return false;
			}
			
			ship.setLocation(location);
			for(int i = location.getX(); i < location.getX() + ship.getWidth(); i++) {
				for(int j = location.getY(); j < location.getY() + ship.getHeight(); j ++) {
					
					if(xySpace[i][j] == 'S' || xySpace[i+1][j] == 'S' || xySpace[i-1][j] == 'S'
							|| xySpace[i][j+1] == 'S' || xySpace[i][j-1] == 'S') {
					//	System.err.println("The location overlaps with another ship\n");
						return false;
					}
				}
			}
			
			for(int i = location.getX(); i < location.getX() + ship.getWidth(); i++) {
				for(int j = location.getY(); j < location.getY() + ship.getHeight(); j ++) {

					xySpace[i][j] = 'S';
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException error){
			
			if(isLocationsValid(location)) {
				
				//System.err.println("This is not a valid location!\n");
				return false;
			}
			else if(ship.getLocation().getX() + ship.getWidth() >= xySpace.length || ship.getLocation().getY() + ship.getHeight()>= xySpace[0].length) {
				
				//System.err.println("The ship does not fit the arena!\n");
				return false;
			}
		}
		
		ship.setLocation(location);
		
		for(int i = location.getX(); i < location.getX() + ship.getWidth(); i++) {
			for(int j = location.getY(); j < location.getY() + ship.getHeight(); j ++) {
				xySpace[i][j] = 'S';
			}
		}
		
		for(int i = 0; i < shipList.length; i++) {
			
			if(shipList[i] == null) {
				shipList[i] = ship;
				shipcounter += 1;
				break;
			}
		}
		return true;
	}


	public void attack(Location location) {

		if(isLocationsValid(location)) {
			Ship hit = isHit(location);
			if(hit != null) {
				xySpace[location.getX()][location.getY()] = 'X';
				hit.reduceHealth(calculateHitDamage(hit));
				for (int i = 1; i < 50; ++i) System.out.println();
				//System.out.print(hit);

				if(!hit.isAlive()) {
					for(int i = hit.getLocation().getX()-1; i < hit.getLocation().getX() + hit.getWidth() + 1; i++) {
						for(int j = hit.getLocation().getY()-1; j < hit.getLocation().getY() + hit.getHeight() + 1; j++) {
							//System.out.print(i + " " + j + "\n");
							if(xySpace[i][j] != 'X' && xySpace[i][j] != 'S') {
								xySpace[i][j] = '\u25FC';
							}
						}
					}
				}
				drawArena();
				System.out.print("\nHit!!\nHealth: " + hit.getHealth("bar") + "\nisAlive: " + hit.isAlive() + "\n");
			}
			else {
				if(xySpace[location.getX()][location.getY()] != 'X') {
					xySpace[location.getX()][location.getY()] = '\u25FC';
				}
				for (int i = 0; i < 50; ++i) System.out.println();
				drawArena();
				System.out.printf("\nNo hit! %d ships remain!\nTry again: \n", getAliveShipCount());
			}
		}
	}


	public void showShipInfo() {
		
		for(int i = 0; i < shipList.length; i++) {
			
			System.out.print(shipList[i]);
		}
	}

	private double calculateHitDamage(Ship ship) {
		return (100.0 / (ship.getWidth() * ship.getHeight()));
	}


	private Ship isHit(Location location) {

		if(xySpace[location.getX()][location.getY()] == 'S') {
			for(int i = 0; i < shipList.length; i++) {
				if(shipList[i] != null && shipList[i].isAlive()) {
					if(isLocationHitsTheShip(shipList[i], location)) {
						
						return shipList[i];
					}
				}
			}
		}
		return null;
	}


	private boolean isLocationHitsTheShip(Ship ship, Location location) {
		System.out.print(ship.getLocation().getX() + " " + location.getX() + " " + (ship.getLocation().getX() + ship.getWidth() - 1) + "\n");
		
		if(ship.getLocation().getX() <= location.getX() && location.getX() <= ship.getLocation().getX() + ship.getWidth() - 1) {
			if(ship.getLocation().getY() <= location.getY() && location.getY() <= ship.getLocation().getY() + ship.getHeight() - 1) {
			
				return true;
			}
		}
		return false;
	}


	public boolean isLocationsValid(Location location) {

		return ((location.getX() < xySpace.length) || location.getY() < xySpace[0].length);
	}


	public long getAliveShipCount() {
		
		int shipcounter = 0;
		for(int i = 0; i < shipList.length; i++) {
			if(shipList[i] != null) {
				if(shipList[i].isAlive()) {
					shipcounter += 1;
					
				}
			}
			else {
				break;
			}
		}
		return shipcounter;
	}

	public Ship[] getShipList() {
		return shipList;
	}

	public int getXlength() {
		return xlength;
	}

	public void setXlength(int xlength) {
		this.xlength = xlength;
	}

	public int getYlength() {
		return ylength;
	}

	public char[][] getXySpace() {
		return xySpace;
	}

	public static void setScore() {
		score += 1;
	}

	public static int getScore() {
		return score;
	}
	
}
