import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.*;
import java.awt.Point;

public class WhiteBoardPanel extends JPanel{

	/**
	 * Create the panel.
	 */
	
	private Listener listener;
	private ArrayList<DrawObject> drawing = new ArrayList<DrawObject>();
	private ArrayList<ShapeObject> shapes = new ArrayList<ShapeObject>();
	private ArrayList<Texts> texts = new ArrayList<Texts>();
	private boolean isText = false;
	private boolean isShape = false;
	private String currText;
	private Point currStarting;
	private Point currEnding;
	private String currShape;
	private Color color;
	private IRemoteDraw drawObj;
	private Timer timer = new Timer(100, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			repaint();
			
		}
	
	});
	public WhiteBoardPanel(IRemoteDraw drawObj) {
		timer.start();
		this.drawObj = drawObj;
		color = new Color(0,0,0);
		listener = new Listener();
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		try {
			drawing = drawObj.getDraw();
			shapes = drawObj.getShape();
			texts = drawObj.getText();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			//System.out.println("error connecting to remote object");
		}
		int size = drawing.size();
		int size2 = shapes.size();
		int size3 = texts.size();
		
		for (int k = 0; k < size3; k++) {
			String t = texts.get(k).getText();
			Point p = texts.get(k).getCenter();
			JLabel jLabel = new JLabel(t);
			jLabel.setBounds(p.x, p.y, 700, 50);
			this.add(jLabel);
		}
		
		for (int j = 0; j < size2; j++) {
			Point value = shapes.get(j).getCenter();
			int x = value.x;
			int y = value.y;
			
			if (shapes.get(j) instanceof Square) {
				int width = ((Square) shapes.get(j)).getWidth();
				g.setColor(shapes.get(j).getColor());
				g.drawRect(x, y, width, width);
			}
			else if (shapes.get(j) instanceof Circle) {
				int width = ((Circle) shapes.get(j)).getRadius();
				g.setColor(shapes.get(j).getColor());
				g.drawOval(x, y, width, width);
			}
			else if (shapes.get(j) instanceof Oval) {
				int width = ((Oval) shapes.get(j)).getWidth();
				int height = ((Oval) shapes.get(j)).getHeight();
				g.setColor(shapes.get(j).getColor());
				g.drawOval(x, y, width, height);
			}
			else if (shapes.get(j) instanceof Line){
				Point start = shapes.get(j).getCenter();
				Point end = ((Line) shapes.get(j)).getEnd();
				g.setColor(shapes.get(j).getColor());
				g.drawLine(start.x, start.y, end.x, end.y);
			}
			else if (shapes.get(j) instanceof Rectangles) {
				int width = ((Rectangles) shapes.get(j)).getWidth();
				int height = ((Rectangles) shapes.get(j)).getHeight();
				g.setColor(shapes.get(j).getColor());
				g.drawRect(x, y, width, height);
			}
			
		}
		
		for(int i = 0; i < size; i++) {
			Point value = drawing.get(i).getPoint();
			int x = value.x;
			int y = value.y;
			g.fillOval(x, y, 10, 10);
			g.setColor(drawing.get(i).getColor());
		}
	}
	
	private class Listener implements MouseListener, MouseMotionListener{
		public void mouseDragged(MouseEvent e) {
			
			if(!isShape) {
				int y = e.getY();
				int x = e.getX();
			
				Point val = new Point(x,y);
				DrawObject dObject = new DrawObject(val, color);
				try {
					drawObj.addDraw(dObject);
				} catch (RemoteException e1) {
				// TODO Auto-generated catch block
					System.out.println("error connecting to remote object");
				}
			//drawing.add(dObject);
				repaint();
			}
			
			
		}

		
		public void mouseMoved(MouseEvent e) {
			
		}

		
		public void mouseClicked(MouseEvent e) {
			
		}

		
		public void mousePressed(MouseEvent e) {
			if (isText) {
				Point p = e.getPoint();
				addTextField(p);
				isText = false;
				repaint();
			}
			
			if (isShape) {
				Point p = e.getPoint();
				currStarting = p;
			}
		}

		
		public void mouseReleased(MouseEvent e) {
			if (isShape) {
				Point p = e.getPoint();
				currEnding = p;
				addShape();
				isShape = false;
			}
			
		}

		public void mouseEntered(MouseEvent e) {
			
			
		}

		
		public void mouseExited(MouseEvent e) {
	
		}
	}
	
	public void changeColor(Color color) {
		this.color = color;
		repaint();
	}
	
	public void addChat(String chat, String username) {
		try {
			drawObj.addChat(chat, username);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getChat() {
		try {
			return drawObj.getChat();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getUserForChat() {
		try {
			return drawObj.getUserForChat();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void addShape() {
		Point start = currStarting;
		Point end = currEnding;
		int width;
		int height;
		ShapeObject shape = null;
		if (currShape.compareTo("Rectangle") == 0 || currShape.compareTo("Oval") == 0) {
			if (start.getX() < end.getX()) {
				if (start.getY() < end.getY()) {
					width = (int)(end.getX() - start.getX());
					height = (int)(end.getY() - start.getY());
				}
				else {
					width = (int)(end.getX() - start.getX());
					height = (int)(start.getY() - end.getY());
					start.y = (start.y) - height;
				}
			}else {
				if (start.getY() < end.getY()) {
					width = (int)(start.getX() - end.getX());
					height = (int)(end.getY() - start.getY());
					start.x = start.x - width;
				}
				else {
					width = (int)(start.getX() - end.getX());
					height = (int)(start.getY() - end.getY());
					start.x = start.x - width;
					start.y = start.y - height;
				}
			}
			if (currShape.compareTo("Rectangle") == 0) {
				shape = new Rectangles(start, width, height, color);
			}
			else {
				shape = new Oval(start, width, height, color);
			}
		}
		
		else if (currShape.compareTo("Circle") == 0 || currShape.compareTo("Square") == 0) {
			if (start.getX() < end.getX()) {
				if (start.getY() < end.getY()) {
					width = (int)(end.getX() - start.getX());
					height = (int)(end.getY() - start.getY());
				}
				else {
					width = (int)(end.getX() - start.getX());
					height = (int)(start.getY() - end.getY());
					start.y = (start.y) - height;
				}
			}else {
				if (start.getY() < end.getY()) {
					width = (int)(start.getX() - end.getX());
					height = (int)(end.getY() - start.getY());
					start.x = start.x - width;
				}
				else {
					width = (int)(start.getX() - end.getX());
					height = (int)(start.getY() - end.getY());
					start.x = start.x - width;
					start.y = start.y - height;
				}
			}
			if (currShape.compareTo("Square") == 0) {
				shape = new Square(start, width, color);
			}
			else {
				shape = new Circle(start, width, color);
			}
		}
		
		else if (currShape.compareTo("Line") == 0) {
			shape = new Line(start, end, color);
		}
		
		try {
			drawObj.addShape(shape);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("error connecting to remote object");
		}
		repaint();
	}
	
	public void addTextField(Point p) {
		Texts text = new Texts(currText, p);
		try {
			drawObj.addText(text);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("error connecting to remote object");
		}
		
	}
	
	public void placeText(String t) {
		isText = true;
		currText = t;
	}
	
	public void placeShape(String shape) {
		isShape = true;
		currShape = shape;
		
	}

}
