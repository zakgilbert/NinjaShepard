import java.util.Random;

public class NinjaFactory {

	private int x, y, z;/// x position y position and speed
	private EZImage ninjaPic; /// adds pic
	Random rG = new Random();

	public NinjaFactory(int posx, int posy, int speed) {
		//// constructor for Zombies. sets parameters.
		x = posx;
		y = posy;
		z = speed;
		ninjaPic = EZ.addImage("Outsider.gif", x, y); // adds Ninja Pic

	}

	public int getX() {
		return x;/// returns X
	}

	public int getY() {
		return y;/// returns Y
	}

	public int reSpeed() {
		z = 4;/// makes speed 5 which is starting speed for Zombies.
		return z;
	}

	public int changeSpeed() {
		if (z >= 2) { /// slows zombie down by half and keeps zombie from stopping completely.
			z = z / 2;
		}
		return z;

	}

	public void pull() {
		ninjaPic.pullForwardOneLayer();
	}

	private void ninjaPOS(int posx, int posy) {
		ninjaPic.translateTo(posx, posy);/// private function for Zombie movemnt.
	}

	public void ninLeft() {
		x = x - z;
		ninjaPOS(x, y);

	}

	public void ninRight() {
		x = x + z;

		ninjaPOS(x, y);
	}

	public void ninUp() {
		y = y - z;
		ninjaPOS(x, y);

	}

	public void ninDown() {
		y = y + z;
		ninjaPOS(x, y);

	}

	public void removal() {
		ninjaPic.pushToBack();
	}

	public boolean within(int posx, int posy) {/// function to help with collision.
		return ninjaPic.isPointInElement(posx, posy);
	}

}