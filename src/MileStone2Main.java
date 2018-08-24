import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

import org.w3c.dom.Text;

public class MileStone2Main {
	static int SCREENWIDTH = 1000;
	static int SCREENHEIGHT = 700;
	static int ninjaArmy = 20;
	static int ninjaCount = 0;
	static int dance = 5;
	static int ninX, ninY;
	static Random rG = new Random();
	static NinjaFactory[] ninja; ////Zombie ARRAY
	static mog MOG;
	static long time;
	static int trapsSET;
	static int deposit;
	static int quiver = 4;
	static int maxTraps = 50;
	static Trap[] trap = new Trap[maxTraps]; /////BARBWire ARRAY
	static Stat stat;
	static boolean alive;
	static int kitCount;
	static int score;
	static EZSound Theme = EZ.addSound("JENOVA.wav");
	static EZSound laugh = EZ.addSound("kefka.wav");

	static void Setup() {
		EZ.initialize(SCREENWIDTH, SCREENHEIGHT);
		EZ.setBackgroundColor(new Color(0, 0, 0));
		EZImage grass = EZ.addImage("img029.jpg", 600, 500); // Background image
		grass.pushToBack();
		MOG = new mog(SCREENWIDTH / 2, SCREENHEIGHT / 2); /// declaring HERO
															/// function
		time = System.currentTimeMillis(); // Sets starting time to a variable
		stat = new Stat(EZ.addImage("Goddess.gif", rG.nextInt(900) + 25, rG.nextInt(700) + 25)); // barbed
																									// wire
																									// function
		ninja = new NinjaFactory[ninjaArmy]; // declares
		ninja[ninjaCount] = new NinjaFactory(spawnX(), spawnY(), 4);//// zombie
		laugh.play();
		/// function and spawns first zombie
		ninjaCount++; //// counts first Zombie
	}

	static void letterBox() {//// creates a boarder around the frame.
		EZ.addRectangle(0, SCREENHEIGHT / 2, 30, SCREENHEIGHT, Color.BLACK, true).pullToFront();
		EZ.addRectangle(SCREENWIDTH, SCREENHEIGHT / 2, 30, SCREENHEIGHT, Color.BLACK, true).pullToFront();
		EZ.addRectangle(SCREENWIDTH / 2, 0, SCREENWIDTH, 30, Color.BLACK, true).pullToFront();
		EZ.addRectangle(SCREENWIDTH / 2, SCREENHEIGHT, SCREENWIDTH, 30, Color.BLACK, true).pullToFront();
	}

	static void bounds() { /// the hero will not escape
		int centerX = MOG.getX();
		int centerY = MOG.getY();
		int height = MOG.getH();
		int width = MOG.getW();
		int aX1 = centerX - width / 2; /// left side
		int aX2 = centerX + width / 2;//// right side
		int aY1 = centerY - height / 2;/// top side
		int aY2 = centerY + height / 2;//// bottom side
		if (aX1 < 1) {
			MOG.danceDance(1 + width / 2, centerY);
		}
		if (aX2 > SCREENWIDTH) {
			MOG.danceDance(SCREENWIDTH - width / 2, centerY);
		}
		if (aY1 < 1) {
			MOG.danceDance(centerX, 1 + height / 2);
		}
		if (aY2 > SCREENHEIGHT) {
			MOG.danceDance(centerX, SCREENHEIGHT - height / 2);
		}
	}

	static void spawnNinja() {//// spawns 20 Zombies every 9 seconds. 30 seconds
								//// would be too easy but if i wanted to change
								//// that I would adjust 9000millis to
								//// 30000millis.
		if (System.currentTimeMillis() - time >= 8000 && ninjaCount < ninjaArmy) {
			//// if the current time minus the time at the start of the loop is
			//// 9000 millis and there are less than 20 Zombies; then spawn
			//// zombie
			ninX = spawnX();/// random X function
			ninY = spawnY();//// random Y function
			ninja[ninjaCount] = new NinjaFactory(ninX, ninY, 4);
			// starts zombie array
			ninja[ninjaCount].pull();/// pulls zombies up one layer.
			ninjaCount++;// counts zombies
			time += 8000;
			laugh.play();
		}
	}

	static boolean death() {
		/// true or false function if Zombie is touching HERO then return true.
		for (int i = 0; i < ninjaCount; i++) {
			if (MOG.within(ninja[i].getX() - 5, ninja[i].getY() - 5))
				return true;
			if (MOG.within(ninja[i].getX() - 5, ninja[i].getY() + 5))
				return true;
			if (MOG.within(ninja[i].getX() + 5, ninja[i].getY() - 5))
				return true;
			if (MOG.within(ninja[i].getX() + 5, ninja[i].getY() + 5))
				return true;
		}
		return false;
	}

	static void gameOver() {/// sets up Game Over screen.... if you play the
							/// game, you'll see this lot
		Theme.pause();
		MOG.removal();
		long t = System.currentTimeMillis();
		EZSound sad = EZ.addSound("GameOver.wav");
		sad.play();
		EZImage halfDead = EZ.addImage("Mog - Wounded.gif", MOG.getX(), MOG.getY());
		EZ.addRectangle(SCREENWIDTH / 2, SCREENHEIGHT / 2, SCREENWIDTH, SCREENHEIGHT, Color.BLACK, true);
		EZText gO = EZ.addText(SCREENWIDTH / 2, SCREENHEIGHT / 2, "GAME OVER", new Color(190, 0, 0), 60);
		gO.setFont("8-BIT WONDER.TTF");// was having issues getting the font to
										// set before the text is seen. tried
										// putting /gO/ into static variable,
										// and
										// making an individual function for it.
										// tried putting it under the background
										// and moving it up once player dies. I
										// even tried making a class for it
		halfDead.pullToFront();
		gO.pullToFront();
		while (true) {/// blocking for HERO
			if (System.currentTimeMillis() - t >= 2000) {
				halfDead.pushToBack();
				EZImage dead = EZ.addImage("Mog - Dead.gif", MOG.getX(), MOG.getY());
				dead.pullToFront();
			}

		}
	}

	static boolean win() {/// if Zombies = 20 then return true
		if (ninjaCount == 20) {//// choose difficulty of the game
			return true; //// 5 Ninjas = ages 5 and under
		} //// 8 Ninjas = easy
		return false; //// 13 Ninjas = journeymen
	} //// 16 NInjas = Advanced
		//// 20 Ninjas = Mythical

	static void victory() {/// same as game over screen, except you probably
		/// will not see this as much :)
		Theme.pause();
		MOG.removal();
		EZSound yay = EZ.addSound("Victory.wav");
		yay.play();
		EZImage yes = EZ.addImage("Mog - Wave (Front).gif", MOG.getX(), MOG.getY());
		EZ.addRectangle(SCREENWIDTH / 2, SCREENHEIGHT / 2, SCREENWIDTH, SCREENHEIGHT, Color.BLACK, true);
		yes.pullToFront();
		EZText vic = EZ.addText(SCREENWIDTH / 2, SCREENHEIGHT / 2, "VICTORY", new Color(0, 190, 0), 60);
		vic.setFont("8-BIT WONDER.TTF");
		vic.pullToFront();

	}

	static void follow() {//// Zombies follow HERO
		for (int i = 0; i < ninjaCount; i++) {
			if (ninja[i].getX() > MOG.getX()) {
				ninja[i].ninLeft();
			}
			if (ninja[i].getX() < MOG.getX()) {
				ninja[i].ninRight();
			}
			if (ninja[i].getY() > MOG.getY()) {
				ninja[i].ninUp();
			}
			if (ninja[i].getY() < MOG.getY()) {
				ninja[i].ninDown();
			}
		}
	}

	static int spawnX() {/// this generates a random x coordinate for Zombie
							/// spawn. Makes the timing very unpredictable and
							/// random
		int x = rG.nextInt(SCREENWIDTH * 2);// choose between 0 and 1999
		if (x > SCREENWIDTH) { /// gives Zombie a 50% chance of spawning off
								/// screen to the right.
		} else {
			x -= 1000;/// if random integer is below 1000 then minus 1000 so
						/// that Zombie spawn off-screen to the left
		}
		return x;/// so its almost a 50/50 chance of spawning off-screen left
					/// and off
					/// screen right. Only problem is you don't know how far off
					/// screen that is. the only indication is that you can hear
					/// them laugh as soon as they spawn.
	}

	static int spawnY() {/// this is the same as above but returns Y
		int y = rG.nextInt(SCREENHEIGHT * 2);
		if (y > SCREENHEIGHT) {
		} else {
			y -= 700;
		}
		return y;
	}

	static void movePlayer() {// move HERO
		if (EZInteraction.isKeyDown('d')) {
			MOG.danceRight(dance);
		}
		if (EZInteraction.isKeyDown('a')) {
			MOG.danceLeft(dance);
		}
		if (EZInteraction.isKeyDown('w')) {
			MOG.danceUp(dance);
		}
		if (EZInteraction.isKeyDown('s')) {
			MOG.danceDown(dance);
		}
	}

	static void spaceBar() {// HERO deposits traps/barbs.
		if (EZInteraction.wasKeyReleased(KeyEvent.VK_SPACE) && quiver >= 0 && deposit <= 49) {
			trap[deposit] = new Trap(EZ.addImage("Magicite.gif", MOG.getX(), MOG.getY()));
			trap[deposit].push();
			deposit++; // counts how many are down.
			quiver--;/// counts how much ammo you have.
		}
	}

	static void checkTraps() {/// Zombie and barb collision.
		for (int j = 0; j < ninjaCount; j++) {//// checks zombie array.
			ninja[j].reSpeed();/// if not colliding change speed back.
			for (int i = 0; i < deposit; i++) {//// checks barb array.
				if ((ninja[j].within(trap[i].getX() - 8, trap[i].getY() - 8))
						|| (ninja[j].within(trap[i].getX() + 8, trap[i].getY() - 8))
						|| (ninja[j].within(trap[i].getX() - 8, trap[i].getY() + 8))
						|| (ninja[j].within(trap[i].getX() + 8, trap[i].getY() + 8))) {
					ninja[j].changeSpeed();/// if colliding change speed.
				}
			}
		}
	}

	static void ninjaCollision() {/// keeps Zombies from bunching up into one.
									/// and
									/// makes game almost impkjossible.
		for (int j = 0; j < ninjaCount; j++)
			for (int i = 0; i < ninjaCount; i++) {
				if (ninja[j].within(ninja[i].getX() - 20, ninja[i].getY() - 20)) {
					ninja[j].ninUp();
					ninja[i].ninDown();
				}
				if (ninja[j].within(ninja[i].getX() + 20, ninja[i].getY() - 20)) {
					ninja[j].ninRight();
					ninja[i].ninLeft();
				}

				if (ninja[j].within(ninja[i].getX() - 20, ninja[i].getY() + 20)) {
					ninja[j].ninLeft();
					ninja[i].ninRight();
				}
				if (ninja[j].within(ninja[i].getX() + 20, ninja[i].getY() + 20)) {
					ninja[j].ninDown();
					ninja[i].ninUp();
				}
			}
	}

	static boolean spawnKit() {//// if HERO and KIT are touching, return true.
		if ((MOG.within(stat.getX() - 15, stat.getY() - 15)) || (MOG.within(stat.getX() + 15, stat.getY() - 15))
				|| (MOG.within(stat.getX() - 15, stat.getY() + 15))
				|| (MOG.within(stat.getX() + 15, stat.getY() + 15)) && kitCount <= 10) {
			return true;
		}
		return false;
	}

	static void kit() {
		if (spawnKit() == true && kitCount < 10) {
			// if HERO and KIT are touching, and there less than 11 kits. Remove
			// kit and spawn somewhere else on screen.
			stat.remove();
			EZSound s = EZ.addSound("bluemag.wav");
			s.play();// play kit sound
			stat = new Stat(EZ.addImage("Goddess.gif", rG.nextInt(900) + 25, rG.nextInt(500) + 25));
			// spawn somewhere random that you can see.
			quiver += 5;/// add five to barb ammo
			kitCount++;/// add one to kitCount.
			EZ.addImage("chalkBoard.png", 70, 60);
			EZ.addImage("GoddessSmall.gif", 50, 60);
			EZ.addText(80, 58, "X" + " " + kitCount, Color.YELLOW, 13);/// keep
																		/// score.
		}
		if (kitCount == 10) {
			stat.remove(); // if kitCount equal 11 then remove last kit
		}
	}

	public static void main(String[] args) {/// main
		// TODO Auto-generated method stub
		Setup();
		letterBox();
		Theme.loop();
		boolean alive = true;/// if alive is true run the loop.
		do {
			movePlayer();
			spaceBar();
			bounds();
			spawnNinja();
			follow();
			ninjaCollision();
			checkTraps();
			spawnKit();
			kit();
			if (win()) {/// if you reach 20 ZOmbies you win.
				victory();
				alive = false;
			}
			if (death()) {/// if a Zombie touches you, you die. you know this.
				gameOver();
				alive = false;
			}
			EZ.refreshScreen();
		} while (alive); //// I'm glad you guys gave us an example of how a do
							//// loop can be used. I rather enjoy it.
	}
}
