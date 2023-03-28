package simulationengine;

import java.security.SecureRandom;
import java.util.Scanner;

import models.BattleshipArena;
import models.Location;
import models.Ship;


public class BattleshipSimulationEngine {

	private boolean status;
	private BattleshipArena arena;

	
	
	public BattleshipSimulationEngine init() {
		status = true;
		this.arena = new BattleshipArena(10, 10);
		
		SecureRandom random = new SecureRandom();
        int numberofShips = 10;
		
        while(arena.getAliveShipCount() < numberofShips) {
        	
        	int rotation = random.nextInt(2);
        	int width;
        	int height;

        	if(rotation == 1) {
            	width = 1;
            	height = random.nextInt(3) + 2;
        	}
        	else {
            	width = random.nextInt(3) + 2;
            	height = 1;
        	}

        	int Xlocation = random.nextInt(arena.getXlength());
        	int Ylocation = random.nextInt(arena.getYlength());
        	Location location = new Location(Xlocation, Ylocation);
        	Ship ship = new Ship("sh", width, height, location);
        	
        	arena.addShip(ship, location);
        }
        arena.drawArena();
		return this;
	}

	public void start() {
		while (this.getStatus()) {
			this.update();
		}
	}


	public BattleshipSimulationEngine update() {
		
		if(arena.getAliveShipCount() > 0) {
			char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'};

			Scanner scanner = new Scanner(System.in);
			// USER CHOOSES WHERE TO SHOOT
			System.out.print("\nPlayer X Location to fire:\n");
			
			char xlocationchar = scanner.next().charAt(0);
			int xlocation = -1;
			for(int i = 0; i < 10; i++) {
				if(letters[i] == xlocationchar) {
					xlocation = i;
					break;
				}
			}
			for(int i = 0; i < 10; i++) {
				if(i == Character.getNumericValue(xlocationchar)) {
					xlocation = i;
					break;
				}
			}

			System.out.print("Player Y Location to fire:\n");
			int ylocation = scanner.nextInt();
			BattleshipArena.setScore();
			Location attackLocation = new Location(xlocation, ylocation);
			if(xlocation == -1) {
				status = false;
				for (int i = 0; i < 50; ++i) System.out.println();
				arena.drawArena(false);
				System.out.print("\n");
				for(int i = 0; i < arena.getShipList().length; i++) {
					if(arena.getShipList()[i] != null) {
						System.out.print(arena.getShipList()[i]);
					}
				}
			}
			else {
				arena.attack(attackLocation);
			}
		}
		else {
			/*for(int i = 0; i < arena.getShipList().length; i++) {
				if(arena.getShipList()[i] != null) {
					System.out.print(arena.getShipList()[i]);
				}
			}*/
			System.out.printf("\n\nWELL DONE! YOUR SCORE IS: %d\n\n", 100 - BattleshipArena.getScore());
			status = false;
		}
		return this;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
