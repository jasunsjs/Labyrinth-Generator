import javax.swing.*;
import java.awt.*;

public class Visualizer extends JFrame{
    char[][] labyrinth;
    MazePanel panel;
    
    final int MAX_X = (int)getToolkit().getScreenSize().getWidth();
    final int MAX_Y = (int)getToolkit().getScreenSize().getHeight();    
    final int GRIDSIZE = MAX_Y/60;

    Visualizer (char[][] labyrinth){
        this.labyrinth = labyrinth;
        this.panel = new MazePanel();
        this.panel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(BorderLayout.CENTER, panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(MAX_X/2, MAX_Y/2);
        this.setVisible(true);
    }
    private class MazePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            for (int row=0; row<labyrinth.length; row++){
                for (int col=0; col<labyrinth[0].length; col++){
                    if  (labyrinth[row][col] == Const.ALLEY){
                        g.setColor(Color.WHITE);
                    }else if (labyrinth[row][col] == Const.WALL){
                        g.setColor(Color.BLACK);
                    }
                    g.fillRect(col*GRIDSIZE,row*GRIDSIZE,GRIDSIZE,GRIDSIZE);          
                }
            }
            this.repaint();
        }
    }
}