import java.awt.Color;
import java.awt.Point;

public class Square extends ShapeObject{
	private int width;
	public Square(Point center, int width, Color color) {
		super(center, color);
		this.width = width;
	}
	public int getWidth() {
		return width;
	}
	
}
