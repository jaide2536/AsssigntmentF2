package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class BulletNuke extends Sprite{
	public static final int Y_TO_END = 0;

	private int step = 20;
	private boolean alive = true;

	public BulletNuke(int x, int y){
		super(x, y, 999, 100);
	}

	public void draw(Graphics2D g){
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
	}

	public void proceed(){
		y -= step;
		if(y > Y_TO_END){
			alive = false;
		}
	}
	
	
	public boolean isAlive(){
		return alive;
	}
}
