package game;

import javax.swing.ImageIcon;

public class Player {

    private int lives;
    private ImageIcon image;
    private boolean move;
    private int dx;
    
    public Player() 
    {        
        lives = 3;
        image = new ImageIcon("src/game/images/player.PNG");
        move = true;
        dx = 0;
    }
    
    public int getLives() 
    {
        
        return lives;
    }
    
    public void loseLife()
    {
        lives--;
    }
    
    public ImageIcon getImageIcon() 
    {
        
        return image;
    }
    
    public void setDX(int dx)
    {
        this.dx = dx;
    }
    
    public void setMove(boolean move)
    {
        this.move = move;
    }
    
    public boolean getMove()
    {
        return move;
    }
    
    public int move(int bound)
    {
        if(dx < bound)
        {
            move = true;
            return dx+=20;
        }
        
        move = false;
        return dx;
    }
}
