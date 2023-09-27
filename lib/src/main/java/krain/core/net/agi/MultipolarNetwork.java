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
 * Copyright (c) 2023, CogniTechLabs (Renato B. Lugto III)
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
 * Author: CogniTechLabs (Renato B. Lugto III)
 * #############################################################################
 */

package krain.core.net.agi;

import krain.core.neuron.agi.Multipolar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultipolarNetwork {
    private List<List<Multipolar>> neuronLayers;

    public MultipolarNetwork(int[] numNeuronsPerLayer, double restingPotential, double actionPotentialThreshold, boolean[] isExcitatoryPerLayer) {
        if (numNeuronsPerLayer.length != isExcitatoryPerLayer.length) {
            throw new IllegalArgumentException("Number of layers and excitatory/inhibitory flags must match.");
        }

        neuronLayers = new ArrayList<>();

        // Random random = new Random();

        for (int i = 0; i < numNeuronsPerLayer.length; i++) {
            int numNeurons = numNeuronsPerLayer[i];
            boolean isExcitatory = isExcitatoryPerLayer[i];

            List<Multipolar> layerNeurons = new ArrayList<>();

            for (int j = 0; j < numNeurons; j++) {
                // double potentialModifier = isExcitatory ? 1.0 : -1.0;
                double neuronRestingPotential = restingPotential;
                double neuronThreshold = actionPotentialThreshold;

                // Modify resting potential and threshold for inhibitory neurons
                if (!isExcitatory) {
                    neuronRestingPotential -= 10.0; // Example: Lower resting potential for inhibitory neurons
                    neuronThreshold -= 5.0; // Example: Lower threshold for inhibitory neurons
                }

                layerNeurons.add(new Multipolar(neuronRestingPotential, neuronThreshold, isExcitatory));
            }

            neuronLayers.add(layerNeurons);
        }
    }

    public List<List<Multipolar>> getNeuronLayers() {
        return neuronLayers;
    }

    public static void main(String[] args) {
        // Create a multipolar network with two layers
        int[] numNeuronsPerLayer = {5, 3}; // 5 neurons in the first layer, 3 in the second
        boolean[] isExcitatoryPerLayer = {true, false}; // Excitatory in the first, inhibitory in the second

        MultipolarNetwork network = new MultipolarNetwork(numNeuronsPerLayer, -70.0, -55.0, isExcitatoryPerLayer);

        // Access the neurons in each layer
        List<List<Multipolar>> neuronLayers = network.getNeuronLayers();

        // Simulate network activity (you can customize this as needed)
        Random random = new Random();
        for (int timeStep = 0; timeStep < 10; timeStep++) {
            System.out.println("Time Step " + timeStep);

            // Simulate activity in each layer
            for (List<Multipolar> layer : neuronLayers) {
                // Provide random inputs to neurons and simulate activity
                for (Multipolar neuron : layer) {
                    neuron.receiveInput(random.nextDouble() * 10.0);
                    neuron.updateMembranePotential();
                    if (neuron.shouldFire()) {
                        neuron.fire();
                    }
                }
            }
        }
    }
}
