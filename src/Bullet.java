import javax.swing.ImageIcon;

public class Bullet extends Entity {
	
	private boolean moveUp;
	private boolean moveDown;
	private int rand = (int) (Math.random() * 11);
	
	public Bullet(int x, int y, int s, boolean move)
	{
		super(x, y, s, true);
		moveUp = false;
		moveDown = false;
		if(move == true)
			moveUp = true;
		else
		{
			moveDown = true;
			determineVis();
		}
		this.setWidth(15);
		this.setHeight(20);
		initialize();
		
	}
	
	//image
	public void initialize()
	{
		ImageIcon image = new ImageIcon("C:\\Users\\stupi\\Downloads\\New Piskel-3.png.png");
		setImage(image.getImage());
	}
	
	//getters
	public boolean getMoveUp()							{ return moveUp; }
	public boolean getMoveDown()						{ return moveDown; }
	
	//setters
	public void setMoveUp(boolean b)					{ moveUp = b; }
	public void setMoveDown(boolean b)					{moveDown = b; }
	
	//methods
	public void move()
	{
		if(moveUp)
		{
			this.incY(-this.getSpeed());
			if(this.getY() <= 0)
				this.setVisible(false);
		}
		else
		{
			this.incY(this.getSpeed());
			if(this.getY() > 1000)
				this.setVisible(false);
		}
	}
	
	public void checkFirable()
	{
		int rand = (int) ((Math.random() * 11));
		if(moveUp == false)
		{
			if(rand > 5)
				setVisible(!getIsVisible());
		}
	}
	
	public void determineVis()
	{
		if(rand < 5)
			setVisible(false);
		else
			setVisible(true);
		rand = (int) (Math.random() * 11);
	}

}
