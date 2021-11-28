import javax.swing.*;

public class SnakeFrame extends JFrame {

    public  SnakeFrame(){
        setSize(400,400);
        setTitle("Game Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,400);
        setResizable(false); //don't resize my window
        setLocation(300,200);
        add(new GameField());
        setVisible(true);
    }

}
