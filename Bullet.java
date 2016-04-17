package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet extends Sprite{
	public static final int Y_TO_END = 0;

	private int step = 40;
	private boolean alive = true;

	public Bullet(int x, int y){
		super(x, y, 5, 50);
	}

	public void draw(Graphics2D g){
		g.setColor(Color.GREEN);
		
                g.fillRect(x-12,y+10,width+3,height-20);
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
