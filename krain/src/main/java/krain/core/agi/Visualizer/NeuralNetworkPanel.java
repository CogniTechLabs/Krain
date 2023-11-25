package krain.core.agi.Visualizer;

import krain.core.agi.Neuron;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

class NeuralNetworkPanel extends JPanel {
   private Neuron[][] neurons;

   NeuralNetworkPanel(String[] args) {
      this.initializeNeurons(args);
   }

   private void initializeNeurons(String[] args) {
      this.neurons = new Neuron[5][5];

      for(int layer = 0; layer < 5; ++layer) {
         for(int neuron = 0; neuron < 5; ++neuron) {
            if (layer == 0) {
               this.neurons[layer][neuron] = new Neuron(0.0, 0.0, 0.0, true);
            } else {
               this.neurons[layer][neuron] = new Neuron(0.0, 0.0, 0.0, true);
            }
         }
      }

   }

   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      this.drawNeuralNetwork(g);
   }

   private void drawNeuralNetwork(Graphics g) {
      for(int layer = 0; layer < 5; ++layer) {
         for(int neuron = 0; neuron < 5; ++neuron) {
            int x = (layer + 1) * 100;
            int y = (neuron + 1) * 100;
            g.setColor(Color.GRAY);
            if (layer < 4) {
               int x2 = (layer + 2) * 100;

               for(int nextNeuron = 0; nextNeuron < 5; ++nextNeuron) {
                  int y2 = (nextNeuron + 1) * 100;
                  g.drawLine(x + 30, y + 15, x2, y2 + 15);
               }
            }

            g.setColor(Color.BLUE);
            g.fillOval(x, y, 30, 30);
            g.setColor(Color.WHITE);
            double output = this.neurons[layer][neuron].getLastOutput();
            g.drawString(String.format("%.2f", output), x + 15 - 10, y + 15 + 5);
         }
      }

   }
}
