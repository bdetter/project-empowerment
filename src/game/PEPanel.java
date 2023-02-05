package game;

import javax.swing.*;
import javax.swing.ImageIcon.*;
import java.awt.Graphics.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PEPanel extends JPanel implements ActionListener{

    private static Stage[] stages;
    private int stageCount;
    private static ImageIcon introStageScreen;
    private static ImageIcon stage1Screen;
    private static ImageIcon stage2Screen;
    private static ImageIcon stage3Screen;
    private static ImageIcon stage4Screen;
    private static ImageIcon stage5Screen;
    private static ImageIcon throneRoomScreen;
    private static ImageIcon ada1;
    private static ImageIcon ada2;
    private static ImageIcon ada3;
    private static ImageIcon[] ada;
    private static ImageIcon grace1;
    private static ImageIcon grace2;
    private static ImageIcon grace3;
    private static ImageIcon[] grace;
    private static ImageIcon mary1;
    private static ImageIcon mary2;
    private static ImageIcon mary3;
    private static ImageIcon[] mary;
    private static ImageIcon marg1;
    private static ImageIcon marg2;
    private static ImageIcon marg3;
    private static ImageIcon[] marg;
    private static ImageIcon meg1;
    private static ImageIcon meg2;
    private static ImageIcon meg3;
    private static ImageIcon[] meg;
    private static ImageIcon start;
    private static ImageIcon end;
    
    private static Player player;
    private static Timer timer;
    private static String[] fillers;
    private boolean intro;
    private boolean portal;
    private boolean advance;
    private boolean showQuestion;
    private boolean showPortal;

    private static JButton[] buttons;
    private static JPanel buttonPanel;
    private static JLabel lives;
    
    //music
    private File music;
    private AudioInputStream audio;
    private Clip clip;
    
    public PEPanel() throws IOException
    {
        storePics();
        setupStages();
        
        stageCount = 0;
        intro = true;
        portal = false;
        advance = true;
        showQuestion = false;
        showPortal = false;
        player = new Player();
        
        timer = new Timer(200, this);
        
        setLayout(new BorderLayout());
        
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBounds(0, 0, 400, 150);
        
        add(buttonPanel, BorderLayout.SOUTH);
        setFocusable(false);
        
        buttons = new JButton[4];
        buttons[0] = new JButton("");
        buttons[1] = new JButton("");
        buttons[2] = new JButton("");
        buttons[3] = new JButton("");
        buttons[0].addActionListener(new ButtonListener());
        buttons[1].addActionListener(new ButtonListener());
        buttons[2].addActionListener(new ButtonListener());
        buttons[3].addActionListener(new ButtonListener());
        
        buttonPanel.add(buttons[0], BorderLayout.SOUTH);
        buttonPanel.add(buttons[1], BorderLayout.SOUTH);
        buttonPanel.add(buttons[2], BorderLayout.SOUTH);
        buttonPanel.add(buttons[3], BorderLayout.SOUTH);
        
        buttonPanel.setVisible(false);
        
        lives = new JLabel("Lives: " + player.getLives());
        add(lives, BorderLayout.NORTH);
        
        setButtons(stages[1].getAnswer());

        controlMusic("play");
    }
    
    public void userInput(int k)
    {
        if(k == KeyEvent.VK_ENTER)
        {
            if(intro)
            {
             timer.start();
             nextStage();
            }
        }
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawScreen(g);
    }
    
    public void drawScreen(Graphics g)
    {
        g.drawImage(stages[stageCount].getBackgroundPic(), 0, 0, 700, 700, null);
        if(!intro)
            g.drawImage(stages[stageCount].getFigurePic(), 425, 350, 300, 250, null);
        g.drawImage(player.getImageIcon().getImage(), player.move(400), 430, 150, 150, null);
        if(showQuestion && !portal)
        {
            displayQuestion(g);
            showQuestion = false;
        }
        if(intro)        
            g.drawImage(start.getImage(), 150, 150, 300, 300, null);
        
        if(portal && showPortal)
            g.drawImage(end.getImage(), 310, 110, 300, 300, null);
    }
    
    public void storePics()
    {
        introStageScreen = new ImageIcon("src/game/images/intro_stage.PNG");
        stage1Screen = new ImageIcon("src/game/images/stage_1.PNG");
        stage2Screen = new ImageIcon("src/game/images/stage_2.PNG");
        stage3Screen = new ImageIcon("src/game/images/stage_3.PNG");
        stage4Screen = new ImageIcon("src/game/images/stage_4.PNG");
        stage5Screen = new ImageIcon("src/game/images/stage_5.PNG");
        throneRoomScreen = new ImageIcon("src/game/images/portal_room.PNG");
        ada1 = new ImageIcon("src/game/images/Ada1.PNG");
        ada2 = new ImageIcon("src/game/images/Ada2.PNG");
        ada3 = new ImageIcon("src/game/images/Ada3.PNG");
        ada = new ImageIcon[3];
        ada[0] = ada1;
        ada[1] = ada2;
        ada[2] = ada3;
        grace1 = new ImageIcon("src/game/images/Grace1.PNG");
        grace2 = new ImageIcon("src/game/images/Grace2.PNG");
        grace3 = new ImageIcon("src/game/images/Grace3.PNG");
        grace = new ImageIcon[3];
        grace[0] = grace1;
        grace[1] = grace2;
        grace[2] = grace3;
        mary1= new ImageIcon("src/game/images/Mary1.PNG");
        mary2 = new ImageIcon("src/game/images/Mary2.PNG");
        mary3 = new ImageIcon("src/game/images/Mary3.PNG");
        mary = new ImageIcon[3];
        mary[0] = mary1;
        mary[1] = mary2;
        mary[2] = mary3;
        marg1= new ImageIcon("src/game/images/Mary1.PNG");
        marg2 = new ImageIcon("src/game/images/Mary2.PNG");
        marg3 = new ImageIcon("src/game/images/Mary3.PNG");
        marg = new ImageIcon[3];
        marg[0] = marg1;
        marg[1] = marg2;
        marg[2] = marg3;
        meg1= new ImageIcon("src/game/images/Mary1.PNG");
        meg2 = new ImageIcon("src/game/images/Mary2.PNG");
        meg3 = new ImageIcon("src/game/images/Mary3.PNG");
        meg = new ImageIcon[3];
        meg[0] = meg1;
        meg[1] = meg2;
        meg[2] = meg3;
        start = new ImageIcon("src/game/images/intro.PNG");
        end = new ImageIcon("src/game/images/congrat.PNG");
    }
    
    public void setupStages() throws FileNotFoundException
    {
        stages = new Stage[7];
        stages[0] = new Stage(new ImageIcon("src/game/images/intro_stage.PNG"), null);
        for (int i = 1; i < 6; i++)
        {
            stages[i] = new Stage(new ImageIcon("src/game/images/stage_" + i + ".PNG"), null, new ImageIcon("src/game/images/guard.PNG"));
        }
        stages[6] = new Stage(new ImageIcon("src/game/images/portal_room.PNG"), null, new ImageIcon("src/game/images/guard.PNG"));
        
        stages[1].setImages(ada);
        stages[2].setImages(grace);
        stages[3].setImages(mary);
        stages[4].setImages(marg);
        stages[5].setImages(meg);
        
        readQuestions();
        readAnswers();
        readFillers();
    }
    
    public void readQuestions() throws FileNotFoundException 
    {       
        Scanner scanner = new Scanner(new FileReader("questions.txt"));
        int stageIndex = 1;
        int i=0;
        String[] questions = new String[3];
        
        while(scanner.hasNextLine()) 
        {           
            String line = scanner.nextLine();
            
            if(line.equals(""))
            {
                stages[stageIndex].setQuestion(questions);
                stageIndex++;
                questions = new String[3];
                i = 0;
                continue;
            }
            
            questions[i] = line;
            i++;
        }
        stages[stageIndex].setQuestion(questions);
        
        scanner.close();
    }
    
    public void readAnswers() throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new FileReader("answers.txt"));
        int stageIndex = 1;
        String line = "";
        
        while(scanner.hasNextLine()) 
        {           
            line = scanner.nextLine();

            stages[stageIndex].setAnswer(line);
            stageIndex++;
        }
        
        scanner.close();        
    }
    
    public void readFillers() throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new FileReader("fillers.txt"));
        fillers = new String[10];
        int i=0;
        
        while(scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            fillers[i] = line;
            i++;
        }
        
        scanner.close();
    }
    
    @SuppressWarnings("deprecation")
    public void setButtons(String answer) 
    {
        
        Random random = new Random();
        
        int index = random.nextInt(4);
        int fillCount = 1;
        
        for(int i=0;i<buttons.length;i++)
        {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
        
        buttons[index].setText(answer);      
        
        while(fillCount < 4)
        {
            index = random.nextInt(4);
            if(buttons[index].getText().equals(""))
            {
                int fill = random.nextInt(10);
                if(!fillers[fill].equals(answer))
                {
                    buttons[index].setLabel(fillers[fill]);
                    fillCount++;
                }
            }
        }
    }
    
    public void displayQuestion(Graphics g)
    {
        Random random = new Random();
        int index = random.nextInt(3);
        Image question = stages[stageCount].getImages()[index].getImage();
        g.drawImage(question, 310, 110, 300, 300, null);
    }
    
    public void nextStage()
    {
        stageCount++;
        if(stageCount == 6)
            portal = true;
        advance = false;
        intro = false;
        showQuestion= false;
        player.setDX(0);
        buttonPanel.setVisible(false);
        setButtons(stages[stageCount].getAnswer());
        timer.start();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
         if(!player.getMove())
         {
             if(!portal)
             {
                buttonPanel.setVisible(true);
                showQuestion = true;
                timer.stop();
                repaint();
             }
             else
                 showPortal = true;
         }
         repaint();

    }
    
    public class ButtonListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            // Get question and answer
            JButton button = (JButton)e.getSource();
            String answer = button.getText();
            // Compare answer with correct answer
            String correct = stages[stageCount].getAnswer();
            // Lose life if incorrect
            if(correct.equals(answer))
            {
                advance = true;
                nextStage();
            }
            // Move on if correct
            else
            {
                player.loseLife();
                lives.setText("Lives: " + player.getLives());
                button.setEnabled(false);
            }
            
            repaint();
        }            
    }
    
    public void controlMusic(String s)
{
    if(s.equals("play"))
        try
        {
            music = new File("src/game/SalmonLikeTheFish-Zion.au");
            audio = AudioSystem.getAudioInputStream(music);
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    else if(s.equals("stop"))
    {
        clip.stop();
    }
    else
    {
        System.out.println("Invalid command.");
    }
}
    
}
