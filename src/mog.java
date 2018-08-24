
public class mog {
	EZImage mogPicture;
	int x, y;
	int frame = 0;

	public mog(int posx, int posy) {
		x = posx;
		y = posy;
		mogPicture = EZ.addImage("Mog (Front).gif", x, y);
	}

	public int getX() {
		return mogPicture.getXCenter();
	}

	public int getY() {
		return mogPicture.getYCenter();
	}

	public int getW() {
		return mogPicture.getWidth();
	}

	public int getH() {
		return mogPicture.getHeight();
	}

	public void danceDance(int posx, int posy) {
		mogPicture.translateTo(posx, posy);
	}

	public void danceLeft(int dance) {
		x -= dance;
		danceDance(x, y);
	}

	public void danceRight(int dance) {
		x += dance;
		danceDance(x, y);

	}

	public void danceUp(int dance) {
		y -= dance;
		danceDance(x, y);

	}

	public void danceDown(int dance) {
		y += dance;
		danceDance(x, y);

	}

	public boolean within(int posx, int posy) {
		return mogPicture.isPointInElement(posx, posy);
	}

	public void removal() {
		EZ.removeEZElement(mogPicture);
	}

}
