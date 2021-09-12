import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Player extends Entity{
	
	private boolean moveLeft;
	private boolean moveRight;
	private ArrayList<Bullet> buls;
	
	public Player(int x, int y, int s, int w, int h)
	{
		super(x, y, s, false, w, h);
		moveLeft = false;
		moveRight = false;
		buls = new ArrayList<Bullet>();
		initialize();
	}
	
	//image
	public void initialize()
	{
		ImageIcon image = new ImageIcon("C:\\Users\\stupi\\Downloads\\New Piskel-1.png.png");
		setImage(image.getImage());
	}
	
	//getters
	public boolean getMoveRight()						{ return moveRight; }
	public boolean getMoveLeft()						{ return moveLeft; }
	public ArrayList<Bullet> getBuls()					{ return buls; }
	
	
	//setters
	public void setMoveRight(boolean b)					{ moveRight = b; }
	public void setMoveLeft(boolean b)					{moveLeft = b; }
	
	
	//methods
	public void fire()
	{
		buls.add(new Bullet(getX()+getWidth()/4, getY(), 4, true));
	}
	
	
	
	

	

	

}
