import java.awt.Color;
import java.awt.Point;

public class Rectangles extends ShapeObject {
	private int width;
	private int height;
	public Rectangles(Point center, int width, int height, Color color) {
		super(center, color);
		this.width = width;
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
