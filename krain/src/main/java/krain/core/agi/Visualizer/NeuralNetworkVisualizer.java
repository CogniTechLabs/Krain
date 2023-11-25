package krain.core.agi.Visualizer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class NeuralNetworkVisualizer {
    private static final int NUM_LAYERS = 5;
    private static final int NEURONS_PER_LAYER = 5;
    private static final int NEURON_SIZE = 30;
    private static final int NEURON_DISTANCE = 100;

    public NeuralNetworkVisualizer() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI(args);
        });
    }

    private static void createAndShowGUI(String[] args) {
      JFrame frame = new JFrame("Neural Network Visualizer");
      frame.setDefaultCloseOperation(3);
      frame.setSize(600, 400);
      frame.getContentPane().add(new NeuralNetworkPanel(args));
      frame.setVisible(true);
   }
}
