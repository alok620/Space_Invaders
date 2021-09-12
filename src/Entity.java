import java.awt.Image;

import javax.swing.ImageIcon;

public class Entity {
	
	private boolean isVisible;
	private int x;
	private int y;
	private int speed;
	private int width;
	private int height;
	private Image image;
	
	public Entity(int x, int y, int speed, boolean v)
	{
		isVisible = false;
		this.x = x;
		this.y = y;
		this.speed = speed;
		isVisible = v;
		width = 20;
		height = 20;
	}
	
	public Entity(int x, int y, int speed, boolean v, int w, int h)
	{
		isVisible = true;
		this.x = x;
		this.y = y;
		this.speed = speed;
		isVisible = v;
		width = w;
		height = h;
	}
	
	//getters
	
	public boolean getIsVisible()					{ return isVisible; }
	public int getX()								{ return x; }
	public int getY()								{ return y;}
	public int getSpeed()							{ return speed; }
	public int getWidth()							{ return width; }
	public int getHeight()							{ return height; }
	public Image getImage()							{ return image; }
	
	public void setX(int num)								{ x = num; }
	public void setY(int num)								{ y = num; }
	public void incX(int num)								{ x += num; }
	public void incY(int num)								{ y += num; }
	public void setVisible(boolean b)						{ isVisible = b; }
	public void setWidth(int num)							{ width = num; }
	public void setHeight(int num)							{ height = num; }
	public void setImage(Image i)							{ image = i; }
	


}
