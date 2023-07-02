import java.awt.Color;
import java.awt.Point;

public class Oval extends ShapeObject{
	private int width;
	private int height;
	public Oval(Point point, int width, int height, Color color) {
		super(point, color);
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
