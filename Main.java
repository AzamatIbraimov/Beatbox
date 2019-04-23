package YNWA;
import javax.swing.*;
import java.awt.*;


public class Main {
    JFrame theFrame;


    public static void main(String[] args) {
        new Main().startUp();
    }
    public void startUp() {
        buildGUI();
    }
    public void buildGUI() {
        theFrame = new JFrame("BeatBox");
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        Box buttonBox = new Box(BoxLayout.Y_AXIS);
        JButton start = new JButton("Start");
        buttonBox.add(start);

    }
}
