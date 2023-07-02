import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

public class DrawObject implements Serializable{
	
	private Point point;
	private Color color;
	public DrawObject(Point point, Color color) {
		this.point = point;
		this.color = color;
	}
	public Point getPoint() {
		return point;
	}
	public Color getColor() {
		return color;
	}
}
