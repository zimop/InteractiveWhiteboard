import java.awt.Point;
import java.io.Serializable;

public class Texts implements Serializable{
	private static final long serialVersionUID = 1L;
	private String text;
	private Point center;
	public Texts(String text, Point center) {
		this.text = text;
		this.center = center;
	}
	
	public String getText() {
		return text;
	}
	
	public Point getCenter() {
		return center;
	}
}
