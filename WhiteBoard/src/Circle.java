import java.awt.Color;
import java.awt.Point;

public class Circle extends ShapeObject{
	private int radius;
	public Circle(Point point, int radius, Color color) {
		super(point,color);
		this.radius = radius;
	}
	public int getRadius() {
		return radius;
	}
	
}
