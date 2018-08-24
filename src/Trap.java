
public class Trap {

	EZImage magiPicture;
	int x, y;

	public Trap(EZImage image) {
		magiPicture = image;

	}

	public int getX() {
		return magiPicture.getXCenter();
	}

	public int getY() {
		return magiPicture.getYCenter();
	}

	public void push() {
		magiPicture.pushBackOneLayer();
	}

	public void within(int posx, int posy) {
		x = posx;
		y = posy;
		magiPicture.translateTo(x, y);
	}
}
