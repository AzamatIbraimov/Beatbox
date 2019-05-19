package YNWA;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
public class Main {
    JFrame theFrame;
    JPanel mainPanel;
    ArrayList<JCheckBox> checkboxList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    float tempoFactor = 1;
    JLabel Speed;
    JProgressBar speedBar= new JProgressBar();


    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat",
            "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo",
            "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibroslap", "Low-mod Tom",
            "High Agogo", "Open Hi Conga"};

    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    public static void main(String[] args) {
        new Main().startUp();
    }

    public void startUp() {
        setUpMidi();
        buildGUI();
    }

    public void buildGUI() {
        theFrame = new JFrame("Azamat Ibraimov's BeatBox BETA");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JMenuBar menuBar=new JMenuBar();
        JMenu menu=new JMenu("Menu");
        menuBar.add(menu);
        theFrame.setJMenuBar(menuBar);



        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        checkboxList = new ArrayList<JCheckBox>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);
        JButton start = new JButton("Start                ");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop = new JButton("Stop                 ");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("TempoUp       ");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("TempoDown  ");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        JButton ClearAll = new JButton("Clear               ");
        ClearAll.addActionListener(new MyStopListener());
        ClearAll.addActionListener(e -> {
            for (JCheckBox chb : checkboxList) {
                chb.setSelected(false);
            } });
        buttonBox.add(ClearAll);

        Speed = new JLabel("     Speed: 50%");
        Speed.setVisible(true);
        buttonBox.add(Speed);

        speedBar.setMaximumSize(new Dimension(200,10));
        speedBar.setMinimum(0);
        speedBar.setMaximum(100);
        speedBar.setValue(50);
        buttonBox.add(speedBar);


        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }

        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        theFrame.getContentPane().add(background);
        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
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

    public void buildTrackAndStart() {
        ArrayList<Integer> trackList;
        sequence.deleteTrack(track);
        track = sequence.createTrack();


        for (int i = 0; i < 16; i++) {
            trackList = new ArrayList<>();

            for (int j = 0; j < 16; j++) {
                JCheckBox jc = (JCheckBox) checkboxList.get(j + (16 * i));
                if (jc.isSelected()) {
                    int key = instruments[i];
                    trackList.add(key);
                } else {
                    trackList.add(null);
                }
            }
            makeTracks(trackList);
        }
        track.add(Midi.makeEvent(192, 9, 1, 0, 15));
        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyStartListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            buildTrackAndStart();
        }
    }
    public class MyStopListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            sequencer.stop();
        }
    }
    public class MyUpTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            tempoFactor = sequencer.getTempoFactor();
            double speed = tempoFactor * 1.03;
            Speed.setText(Format.formatSpeedString(speed));
            sequencer.setTempoFactor((float) (speed));
            speedBar.setValue(Format.formatSpeedInt(speed));
        }
    }
    public class MyDownTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            tempoFactor = sequencer.getTempoFactor();
            double speed = tempoFactor * .97;
            Speed.setText(Format.formatSpeedString(speed));
            sequencer.setTempoFactor((float) (speed));
            speedBar.setValue(Format.formatSpeedInt(speed));
        }
    }
    public void makeTracks(ArrayList list) {
        Iterator it = list.iterator();
        for (int i = 0; i < 16; i++) {
            Integer num = (Integer) it.next();
            if (num != null) {
                int numKey = num;
                track.add(Midi.makeEvent(144, 9, numKey, 100, i));
                track.add(Midi.makeEvent(128, 9, numKey, 100, i + 1));
            }
        }
    }
}
