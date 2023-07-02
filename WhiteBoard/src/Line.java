import java.awt.Color;
import java.awt.Point;

public class Line extends ShapeObject{
	private Point end;
	public Line(Point center, Point end, Color color) {
		super(center, color);
		this.end = end;
	}
	public Point getEnd() {
		return end;
	}
}
