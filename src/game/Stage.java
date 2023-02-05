package game;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;

public class Stage {
    
    private ImageIcon backgroundPic;
    private ImageIcon figurePic;
    private String[] questions;
    //private String[] answers;
    private String answer;
    private ImageIcon[] qImages;
    
    public Stage(ImageIcon bp, String[] q, ImageIcon i)
    {
        backgroundPic = bp;
        questions = q;
        figurePic = i;
    }
    
    public Stage(ImageIcon bp, String[] q)
    {
        backgroundPic = bp;
        questions = q;
    }
    
    public Image getBackgroundPic()
    {
        return backgroundPic.getImage();
    }
    
    public void setQuestion(String[] q) {
        
        this.questions = q;
    }
    
    public String getQuestion()
    {
        Random random = new Random();
        int index = random.nextInt(3);
        return questions[index];
        
    }
    
    public void setAnswer(String a)
    {      
        this.answer = a;
    }
    
    public String getAnswer() 
    {
        return answer; 
    }
    
    public Image getFigurePic()
    {
        return figurePic.getImage();
    }
    
    public void setImages(ImageIcon[] i)
    {
        this.qImages = i;
    }
}
