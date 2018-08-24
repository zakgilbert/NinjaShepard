
public class Stat {

	private EZImage picture;

	public Stat(EZImage image) {
		picture = image;
	}

	public int getX() {
		return picture.getXCenter();
	}

	public int getY() {
		return picture.getYCenter();
	}

	public void remove() {
		EZ.removeEZElement(picture);
	}

}
