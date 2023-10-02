/**
 * #############################################################################
 * #                                                                           #
 * #                      CTPL v1 - CogniTech Public License, Version 1        #
 * #                                                                           #
 * #############################################################################
 *
 * Purpose: CTPL v1 is designed to promote transparency and prevent spyware in software.
 *
 * Duration: This license has a 10-year expiration date after the last stable release; it's completely open-source after then.
 *
 * Permissions:
 * - You may modify and distribute software under CTPL v1 for non-commercial purposes.
 * - Derivative works are allowed as long as they also use the CTPL v1 license.
 * - Attribution to the creator or organization is required.
 *
 * Restrictions:
 * - CTPL v1-licensed software must remain open-source.
 * - Commercial use is only permitted through a contract between the creator and the buyer.
 * - The contract may result in the code becoming closed source and the license revoked if the buyer chooses to but still open to those who have a copy.
 * - The pricing for this contract is determined by the creator and can be negotiated by the buyer.
 *
 * Enforcement: Any violation of this license may result in legal action.
 *
 * Copyright (c) 2023, CogniTechLabs (Renato Czarnezcki B. Bason)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions, and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this
 *    list of conditions, and the following disclaimer in the documentation and/or
 *    other materials provided with the distribution.
 *
 * 3. Neither the name of CogniTechLabs nor the names of its contributors may
 *    be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 *
 * Author: CogniTechLabs (Renato Czarnezcki B. Bason)
 * #############################################################################
 */

package krain.core.neuron.agi.funstuff;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NeuronVisualizer extends JPanel {
    private List<PositionedNeuron> neurons; // List of neurons with positions

    // Embedded PositionedNeuron interface
    interface PositionedNeuron {
        int getX();
        int getY();
        List<? extends PositionedNeuron> getConnectedNeurons();
    }

    public NeuronVisualizer() {
        this.neurons = new ArrayList<>();
    }

    public void addNeuron(PositionedNeuron neuron) {
        neurons.add(neuron);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (PositionedNeuron neuron : neurons) {
            int x = neuron.getX(); // X-coordinate of the neuron
            int y = neuron.getY(); // Y-coordinate of the neuron

            // Draw the neuron as a circle
            int diameter = 30; // Diameter of the circle
            int radius = diameter / 2;
            int circleX = x - radius;
            int circleY = y - radius;

            // You can set any color or style you want for the neurons
            g.setColor(Color.BLUE);
            g.fillOval(circleX, circleY, diameter, diameter);

            // Draw connections as lines (assuming each neuron has a list of connected neurons)
            g.setColor(Color.BLACK); // Color for connections
            for (PositionedNeuron connectedNeuron : neuron.getConnectedNeurons()) {
                int targetX = connectedNeuron.getX();
                int targetY = connectedNeuron.getY();
                g.drawLine(x, y, targetX, targetY);
            }
        }
    }

    public void startVisualization() {
        JFrame frame = new JFrame("Neuron Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setSize(400, 400);
        frame.setVisible(true);

        Timer timer = new Timer(100, e -> {
            repaint();
        });
        timer.start();
    }
}
