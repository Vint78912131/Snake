import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GameField extends JPanel implements ActionListener{
    private final int SIZE = 400;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private BufferedImage dot;
    private BufferedImage fruit;
    private int fruitX;
    private int fruitY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;


    public GameField(){
        setBackground(Color.white);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame(){
        dots = 3; //snake start size
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }

        timer = new Timer(250,this);
        timer.start();
        createFruit();
    }

    public void createFruit(){ //set fruit position
        fruitX = new Random().nextInt(20)*DOT_SIZE;
        fruitY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImages(){
        try {
            fruit = ImageIO.read(new File("redApple.png"));
            dot = ImageIO.read(new File("heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Images not found.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String str;
        g.setColor(Color.black);
        if (inGame) { //display fruit
            g.drawImage(fruit,fruitX,fruitY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);
            }
        } else {
            if (dots > 9) { //If length of snake = 10 - you wins
                str = "You won. Get out of here!";
            } else {
                str = "Game Over!";
            }
            g.drawString(str,125,SIZE/2);
        }
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        } if(up){
            y[0] -= DOT_SIZE;
        } if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkFruit(){
        if(x[0] == fruitX && y[0] == fruitY){
            dots++; //snake grows
            createFruit(); //new fruit
        }
    }

    public void checkCollisions(){
        if (dots > 9) inGame = false; //If length of snake = 10 - you wins
        for (int i = dots; i >0 ; i--) {
            if(i > 4 && x[0] == x[i] && y[0] == y[i]){ //collision to herself is possible with length > 4
                inGame = false;
            }
        }
        if(x[0] > SIZE) //right bound
            inGame = false;
        if(x[0] < 0) //left bound
            inGame = false;
        if(y[0] > SIZE) //lower bound
            inGame = false;
        if(y[0] < 0) //upper bound
            inGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkFruit();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right = false;
                down = true;
                left = false;
            }
        }
    }
}