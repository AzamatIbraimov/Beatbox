package YNWA;
import javax.swing.*;
import java.awt.*;


public class Main {
    JFrame theFrame;
   
    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat",
            "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo",
            "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibrslap", "Low-mod Tom",
            "High Agogo", "Open Hi Conga"};
    
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};



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

        JButton stop = new JButton("Stop");
        buttonBox.add(stop);

        JButton upTempo = new JButton("Tempo Up");
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo Down");
        buttonBox.add(downTempo);
        
       
        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        theFrame.getContentPane().add(background);
        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
    

        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);

        }

        theFrame.setBounds(50, 50, 300, 300);
        theFrame.pack();
        theFrame.setVisible(true);
    }
    public void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    }
}
