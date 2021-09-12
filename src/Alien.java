
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Alien extends Entity{
	
	private boolean moveLeft;
	private boolean moveRight;
	private boolean shooter;
	private int rand;
	private ArrayList<Bullet> buls;
	
	
	public Alien(int x, int y, int s, int w, int h)
	{
		super(x, y, s, true, w, h);
		moveLeft = false;
		moveRight = true;
		shooter = false;
		rand = (int) ((Math.random() * 2) + 1);
		buls = new ArrayList<Bullet>();
		initialize();
		
	}
	
	//image
	public void initialize()
	{
		ImageIcon image = new ImageIcon("C:\\Users\\stupi\\Downloads\\New Piskel-2.png.png");
		setImage(image.getImage());
	}
	
	//getters
	public boolean getMoveRight()						{ return moveRight; }
	public boolean getMoveLeft()						{ return moveLeft; }
	public boolean getShooter()							{ return shooter; }
	public int getRand()								{ return rand; }
	
	//setters
	
	public void setMoveRight(boolean b)					{ moveRight = b; }
	public void setMoveLeft(boolean b)					{ moveLeft = b; }
	public void setShooter(boolean b)					{ shooter = b; }
	
	//methods
	public Bullet makeB()
	{
		Bullet x = new Bullet(getX(), getY(), 1, false);
		return x;
	}
	
	public boolean roll()
	{
		if(rand > 5)
			return true;
		rand = (int) ((Math.random() * 11));
		return false;
	}
	
	
}
