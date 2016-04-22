package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Items extends Sprite{
	public static final int Y_TO_FADE = 600;
	public static final int Y_TO_DIE = 600;
	
	private int step = 12;
	private boolean alive = true;
	
	public Items (int x, int y) {
		super(x, y, 19, 2);

	}

	@Override
	public void draw(Graphics2D g) {
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}
		g.setColor(Color.WHITE);
		
			g.fillRect(x-12,y+10,width+2,height+5);
		
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;

	}

	public boolean itemsdie(){
	return alive = false;
	}
}
