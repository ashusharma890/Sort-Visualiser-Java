package sortvisualiser.screens;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.AlgorithmConstraints;
import java.time.chrono.IsoEra;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;

import sortvisualiser.MainApp;
import sortvisualiser.algorithms.*;
import java.awt.event.ActionEvent;



public final class MainMenuScreen  extends Screen{
    private static final Color BACKGROUND_COLOUR = Color.DARK_GRAY;
    private final ArrayList<AlgorithmCheckBox> checkboxes;

    public MainMenuScreen(MainApp app)
    {
        super(app);
        checkboxes = new ArrayList<>();
        setUpGUI();
    }

    private void addCheckBox(ISortAlgorithm algorithm,JPanel panel)
    {
        JCheckBox box = new JCheckBox("",true);
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.setBackground(BACKGROUND_COLOUR);
        box.setForeground(Color.WHITE);
        checkboxes.add(new AlgorithmCheckBox(algorithm,box));
        panel.add(box);
    }

    private void initContainer(JPanel p)
    {
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.setBackground(BACKGROUND_COLOUR);
    }

    public void setUpGUI()
    {
        JPanel sortAlgorithmContainer = new JPanel();
        JPanel optionsContainer = new JPanel();
        JPanel outerContainer = new JPanel();
        initContainer(this);
        initContainer(optionsContainer);
        initContainer(sortAlgorithmContainer);

        outerContainer.setBackground(BACKGROUND_COLOUR);
        outerContainer.setLayout(new BoxLayout(outerContainer, BoxLayout.LINE_AXIS));

        try
        {
            ClassLoader loader = getClass().getClassLoader();
            BufferedImage image = ImageIO.read(new File("C:/Users/user/Desktop/Java/Sorting_visualizer_java/logo.png.jpeg"));
            JLabel label = new JLabel(new ImageIcon(image));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(label);
        }
        catch (IOException e)
        {
            System.out.println("unable to load logo");
        }

        sortAlgorithmContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        addCheckBox(new BubbleSort(),     sortAlgorithmContainer);
        addCheckBox(new QuickSort(),      sortAlgorithmContainer);
        addCheckBox(new SelectionSort(),  sortAlgorithmContainer);
        addCheckBox(new InsertionSort(),  sortAlgorithmContainer);
        addCheckBox(new MergeSort(),      sortAlgorithmContainer);
        addCheckBox(new HeapSort(),       sortAlgorithmContainer);

        JCheckBox soundCheckBox = new JCheckBox("Play Sounds");
        soundCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        soundCheckBox.setBackground(BACKGROUND_COLOUR);
        soundCheckBox.setForeground(Color.WHITE);

        optionsContainer.add(soundCheckBox);

        JButton startButton = new JButton("Begin Visual Sorter");
        startButton.addActionListener((ActionEvent e) ->
        {
            ArrayList<ISortAlgorithm> algorithms = new ArrayList<>();
            for(AlgorithmCheckBox cb : checkboxes)
            {
                if(cb.isSelected())
                {
                    algorithms.add(cb.getAlgorithm());
                }
            }
            app.pushScreen(
                    new SortingVisualiserScreen(
                            algorithms,
                            soundCheckBox.isSelected(),
                            app
                    ));
        });

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        outerContainer.add(optionsContainer);
        outerContainer.add(Box.createRigidArea(new Dimension(5,0)));
        outerContainer.add(sortAlgorithmContainer);

        int gap = 15;
        add(Box.createRigidArea(new Dimension(0, gap)));
        add(outerContainer);
        add(Box.createRigidArea(new Dimension(0, gap)));
        add(startButton);
    }

    @Override
    public void onOpen()
    {
        checkboxes.forEach((box) -> {
            box.unselect();
        });
    }

    private class AlgorithmCheckBox
    {
        private final ISortAlgorithm algorithm;
        private final JCheckBox box;

        public AlgorithmCheckBox(ISortAlgorithm algorithm, JCheckBox box)
        {
            this.algorithm = algorithm;
            this.box = box;
            this.box.setText(algorithm.getName());
        }

        public void unselect()
        {
            box.setSelected(false);
        }

        public boolean isSelected()
        {
            return box.isSelected();
        }

        public ISortAlgorithm getAlgorithm()
        {
            return algorithm;
        }
    }

}


