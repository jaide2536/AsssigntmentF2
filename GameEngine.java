package f2.spw;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.JButton;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<Friend> friends = new ArrayList<Friend>();
	private ArrayList<Bullet> bullets = new ArrayList<>();
	private ArrayList<BulletNuke> bulletnukes = new ArrayList<>();
	private SpaceShip v;

	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.1;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generateFriend(){
		Friend a = new Friend((int)(Math.random()*390), 30);
		gp.sprites.add(a);
		friends.add(a);
	}

	
	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
			generateFriend();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
			}
		}
		
		Iterator<Friend> a_iter = friends.iterator();
		while(a_iter.hasNext()){
			Friend a = a_iter.next();
			a.proceed();
			
			if(!a.isAlive()){
				a_iter.remove();
				gp.sprites.remove(a);
				score += 100;
			}
		}

		Iterator<Bullet> b_iter = bullets.iterator();
                while(b_iter.hasNext()){
                    Bullet b = b_iter.next();
                    b.proceed();
                }

        Iterator<BulletNuke> n_iter = bulletnukes.iterator();
                while(n_iter.hasNext()){
                    BulletNuke n = n_iter.next();
                    n.proceed();
                }

		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double br;
		Rectangle2D.Double nr;
		Rectangle2D.Double ar;

		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				die();
				return;
			}


			for(Bullet b : bullets ){
            br = b.getRectangle();
            if(br.intersects(er)){  
            	e.enemydie();
            	return;
        		}

        		for(Friend a : friends ){
        			ar = a.getRectangle();
        			if(br.intersects(ar)){
            			a.frienddie();
            			score -= 500;
            			return;
            		}
            	}
			}

			for(BulletNuke n : bulletnukes ){
            	nr = n.getRectangle();
            	if(nr.intersects(er)){  
            	e.enemydie();
            	return;
        		}
        	}
		}
	}


	public void fire(){
		Bullet b = new Bullet((v.x)+12,(v.y)-20);
		gp.sprites.add(b);
		bullets.add(b);
	}

	public void firenu(){
		BulletNuke n = new BulletNuke((v.x)-400,(v.y)-20);
		gp.sprites.add(n);
		bulletnukes.add(n);
	}
	
	public void die(){
		
		timer.stop();
		JOptionPane.showMessageDialog(null,"Game Over");	
		System.exit(0);		
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1);
			break;
			
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break;
		case KeyEvent.VK_A:
			fire();
			break;
		case KeyEvent.VK_N:
			firenu();
			break;
		}
	}

	public long getScore(){
		return score;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
