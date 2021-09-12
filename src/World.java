import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;


public class World  extends JPanel implements Runnable, MouseListener, ActionListener
{
	boolean ingame = true;
	private Dimension d;
	int BOARD_WIDTH=1000;
	public int BOARD_HEIGHT=1000;
	int x = 0;
	BufferedImage img;
	//String message = "Click Board to Start";
	private Thread animator;
	private int NUM_ALIENS = 30;
	private int NUM_ALIEN_ROWS = 3;
	private boolean lost = false;
	private boolean canFire = true;
	private boolean won = false;
	private boolean playing = true;
	Player p;
	ArrayList<Alien> a = new ArrayList<Alien>();
	ArrayList<Bullet> b = new ArrayList<Bullet>();
	ArrayList<Bullet> alienB = new ArrayList<Bullet>();
	ArrayList<Alien> shooters = new ArrayList<Alien>();
	ImageIcon background = new ImageIcon("C:\\Users\\stupi\\Downloads\\yUNBsTejMsRxvemMzX62u9-1200-80.jpg");
	Image back = background.getImage();
	
	
	
	public World()
	{
	addKeyListener(new TAdapter());
    addMouseListener(this);
    setFocusable(true);
    d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    p = new Player(BOARD_WIDTH/2, BOARD_HEIGHT-90, 5, BOARD_WIDTH/20, BOARD_HEIGHT/25);
    resetAliens();
    
   setBackground(Color.black);
   
   
	if (animator == null || !ingame) {
	animator = new Thread(this);
	animator.start();
	}
                    
  
		setDoubleBuffered(true);
    }
    public void paint(Graphics g)
	{
		super.paint(g);
		
		g.setColor(Color.white);
		Font f = new Font("serif", Font.PLAIN, 18);
		g.fillRect(0, 0, d.width, d.height);
		g.drawImage(back, 0, 0, this);
		if(won)
		{
			g.setFont(f);;
			g.drawString("You Win!", d.width/2, d.height/2);
			g.setColor(Color.BLUE);
			g.fillRect(400, 600, 100, 30);
			g.fillRect(600, 600, 100, 30);
			g.setColor(Color.RED);
			g.drawString("Play Again", 410, 620);
			g.drawString("Exit Game", 610, 620);
			return;
		}
		if(lost)
		{
			g.setFont(f);
			g.drawString("You Lose", d.width/2, d.height/2);
			g.setColor(Color.BLUE);
			g.fillRect(400, 600, 100, 30);
			g.fillRect(600, 600, 100, 30);
			g.setColor(Color.RED);
			g.drawString("Play Again", 410, 620);
			g.drawString("Exit Game", 610, 620);
			return;
		}
		g.setColor(Color.RED);
		//g.fillRect(p.getX(), p.getY(), BOARD_WIDTH/20, BOARD_HEIGHT/25);
		g.drawImage(p.getImage(), p.getX(),p.getY(), this);
		if(p.getMoveRight() == true)
		{
			if(p.getX() < d.width - 69)
				p.incX(p.getSpeed());
		}
		if(p.getMoveLeft() == true)
		{
			if(p.getX() > 0)
				p.incX(-p.getSpeed());
		}
		g.setColor(Color.GREEN);
		for(int i = 0; i < a.size(); i++)
		{
			g.drawImage(a.get(i).getImage(), a.get(i).getX(), a.get(i).getY(), this);
			//g.fillRect(a.get(i).getX(), a.get(i).getY(), BOARD_WIDTH/16, BOARD_HEIGHT/16);
		}
		g.setColor(Color.DARK_GRAY);
		b = p.getBuls();
		if(b.size() > 0)
		{
			ArrayList<Rectangle> boxes = this.getAlienHBox();
			for(int j = 0; j < b.size(); j++)
			{
				b.get(j).move(); 
				//g.fillRect(b.get(j).getX(), b.get(j).getY(), b.get(j).getWidth(), b.get(j).getHeight());
				g.drawImage(b.get(j).getImage(), b.get(j).getX(), b.get(j).getY(), this);
				Rectangle hitBox = new Rectangle(b.get(j).getX(), b.get(j).getY(), b.get(j).getWidth(), b.get(j).getHeight());
				for(int i = 0; i < boxes.size(); i++)
				{
					if(boxes.get(i).intersects(hitBox) || hitBox.intersects(boxes.get(i)))
					{
						boxes.remove(i);
						a.remove(i);
						b.remove(j);
					}
				}
				if(b.size() > 0)
				{
					if(b.get(j).getY() <= 0)
						b.remove(j);
				}
			}
		}
		int rand = (int) ((Math.random()*3)+1);
			shooters = this.isShooter(a);
			for(int i = 0; i < shooters.size(); i++)
			{
				this.alienB.add(shooters.get(i).makeB());
				if(alienB.size() > 0)
				{
					alienB.get(i).move();
					g.setColor(Color.ORANGE);
					if(alienB.get(i).getIsVisible())
					{
						g.fillRect(alienB.get(i).getX() + alienB.get(i).getWidth()/2, alienB.get(i).getY()+alienB.get(i).getHeight()*2, alienB.get(i).getWidth(), alienB.get(i).getHeight());
						
						checkColisionBP(alienB.get(i), p);
					}
					if(alienB.get(i).getY() >= d.height)
					{
						//shooters = this.isShooter(a);
						for(int j = 0; j < shooters.size(); j++)
						{
							if(alienB.get(j).getY() >= d.height)
							{
								alienB.get(j).determineVis();
								alienB.get(j).setX(shooters.get(j).getX());
								alienB.get(j).setY(shooters.get(j).getY());
							}
						}
					}
					
				}
			}
			moveAliens();
		
		
		
		
		
		
			
	    
	   
	    
	       
	    
	   
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
    
    public void resetAliens()
    {
    	alienB.clear();
    	a.clear();
    	int ax = BOARD_WIDTH/50;
        int ay = BOARD_HEIGHT/50;
        int count = 0;
        for(int i = 0; i < NUM_ALIENS; i++)
        {
    		count++;
    		a.add(new Alien(ax, ay, 1, BOARD_WIDTH/20, BOARD_HEIGHT/20));
    		ax += BOARD_WIDTH/12;
    		if(count == NUM_ALIENS/NUM_ALIEN_ROWS)
    		{
    			ax = BOARD_WIDTH/50;
    			ay += BOARD_HEIGHT/12;
    			count = 0;
    		}
        }
    }
    
    public void moveAliens()
    {
    	for(int i = 0; i < a.size(); i++)
    	{
    		if(a.get(i).getMoveRight())
    		{
	    		a.get(i).incX(a.get(i).getSpeed());
    		}
    		if(a.get(i).getMoveLeft())
    		{
    			a.get(i).incX(-a.get(i).getSpeed());
    		}
    	}
    	for(int i = 0; i < a.size(); i++)
    	{
    		if(a.get(i).getX() > d.width - d.width/15)
    		{
    			for(int j = 0; j < a.size(); j++)
				{
    				a.get(j).setMoveRight(false);
    				a.get(j).setMoveLeft(true);
    				a.get(j).incY(5);
				}
    		}
    	}
    	for(int i = 0; i < a.size(); i++)
    	{
			if(a.get(i).getMoveLeft())
    		{
    			if(a.get(i).getX() < 0)
    			{
    				for(int j = 0; j < a.size(); j++)
    				{
	    				a.get(j).setMoveRight(true);
	    				a.get(j).setMoveLeft(false);
	    				a.get(j).incY(5);
    				}
    			}
    		}
    	}

    }
    
    public ArrayList<Rectangle> getAlienHBox()
    {
    	ArrayList<Rectangle> boxes = new ArrayList<Rectangle>();
    	for(Alien al : a) {
    		boxes.add(new Rectangle(al.getX(), al.getY(), al.getWidth(), al.getHeight()));
    	}
    	return boxes;
    }
    
    public void checkColisionPA(ArrayList<Alien> a, Player p)
    {
    	Rectangle player = new Rectangle(p.getX(), p.getY(), p.getWidth(), p.getHeight());
    	for(int i = 0; i < a.size(); i++)
    	{
    		Rectangle alien = new Rectangle(a.get(i).getX(), a.get(i).getY(), a.get(i).getWidth(), a.get(i).getHeight());
    		if(alien.intersects(player) || player.intersects(alien))
    		{
    			lost = true;
    			return;
    		}
    	}
    }
    
    public ArrayList<Alien> isShooter(ArrayList<Alien> a)
    {
    	ArrayList<Alien> ret = new ArrayList<Alien>();
    	super_loop:
    	for(int i = 0; i < a.size(); i++)
    	{
    		Rectangle r1 = new Rectangle(a.get(i).getX(), a.get(i).getY(), a.get(i).getWidth(), d.height);
    		for(int j = 0; j < a.size(); j++)
    		{
    			if(i != j)
    			{
    				Rectangle r2 = new Rectangle(a.get(j).getX(), a.get(j).getY(), a.get(j).getWidth(), a.get(j).getHeight());
    				if(r2.intersects(r1) || r1.intersects(r2))
    				{
    					continue super_loop;
    				}
    			}
    		}
    		a.get(i).setShooter(true);
    		ret.add(a.get(i));
    	}
    	return ret;
    }
    
    public void checkColisionBP(Bullet b, Player p)
    {
    	Rectangle pr = new Rectangle(p.getX(), p.getY(), p.getWidth(), p.getHeight());
    	Rectangle br = new Rectangle(b.getX() + b.getWidth()/2, b.getY()+b.getHeight()*2, b.getWidth(), b.getHeight());
    	if(br.intersects(pr) || pr.intersects(br))
		{
			lost = true;
			return;
		}
    	/*if(this.alienB.size() > 0)
    	{
    		for(Bullet x : alienB)
    		{
    			Rectangle br = new Rectangle(x.getX() + x.getWidth()/2, x.getY()+x.getHeight()*2, x.getWidth(), x.getHeight());
    			if(br.intersects(pr) || pr.intersects(br))
    			{
    				lost = true;
    				return;
    			}
    		}
    	}*/
    	
    }
    
    
        
    
    
	private class TAdapter extends KeyAdapter 
	{
	
		public void keyReleased(KeyEvent e) {
		     int key = e.getKeyCode();
		     if(key==39)
		    	 p.setMoveRight(false);
		     if(key==37)p.setMoveLeft(false);
		}
		
		public void keyPressed(KeyEvent e) 
		{
		    int key = e.getKeyCode();
		        if(key == 39){
		        	p.setMoveRight(true);
		        }
		        if(key == 37)
		        	p.setMoveLeft(true);
		        if(key == e.VK_SPACE)
		        {
		        	if(p.getBuls().size() < 1)
		        		p.fire();
		        }
		        
	       
	
		}

	}




	public void mousePressed(MouseEvent e) {
	    int x = e.getX();
	     int y = e.getY();
	     if(lost || won)
	     {
		     if((x > 400 && x < 500) && (y > 600 && y < 630))
		     {
		    	 resetAliens();
		    	 p.setX(BOARD_WIDTH/2);
		    	 p.setY(d.height-90);
		    	 won = false;
		    	 lost = false;
		     }
		     if((x > 600 && x < 700) && (y > 600 && y < 630))
		     {
		    	 System.out.println("Thanks for playing!");
		    	 System.exit(0);
		     }
	     }
	     
	}
	
	public void mouseReleased(MouseEvent e) {
	
	}
	
	public void mouseEntered(MouseEvent e) {
	
	}
	
	public void mouseExited(MouseEvent e) {
	
	}
	
	public void mouseClicked(MouseEvent e) {
	
	}

	public void run() {
	long beforeTime, timeDiff, sleep;
	beforeTime = System.currentTimeMillis();
	 int animationDelay = 5;
	 long time = 
	            System.currentTimeMillis();
	 
	 while(playing)
	 {
	    while (true) 
	    {
	    	if(won || lost)
	    	{
	    		break;
	    	}
	    	if(a.size() < 1)
	    	{
	    		won = true;
	    	}
	    	checkColisionPA(a, p);
	    	for(int i = 0; i < a.size(); i++)
	    	{
	    		//System.out.println(a.get(i).getY() + ", " + a.get(i).getHeight());
	    		if(a.get(i).getY()+100 > d.height)
	    			lost = true;
	    	}
		  repaint();
		  try {
		    time += animationDelay;
		    Thread.sleep(Math.max(0,time - 
		      System.currentTimeMillis()));
		  } catch (InterruptedException e) {
		    System.out.println(e);
		  }
		}
	    while(lost || won)
	    {
	    	this.repaint();
	    }
	 }
	
	    
	
	
	}
	Timer t = new Timer(500, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			canFire = true;
			t.setRepeats(true);
			t.start();
		}
	});


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}

         
  