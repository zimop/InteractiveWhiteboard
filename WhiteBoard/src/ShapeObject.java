import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

public class ShapeObject implements Serializable{
	
	private Point center;
	private Color color;
	
	public ShapeObject(Point center, Color color) {
		this.center = center;
		this.color = color;
	}
	
	public Point getCenter() {
		return center;
	}
	
	public void setCenter(Point center) {
		this.center = center;
	}
	
	public Color getColor() {
		return color;
	}
}
