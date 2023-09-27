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

package krain.core.neuron.agi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Multipolar {
    private double membranePotential; // Membrane potential
    private double restingPotential; // Resting potential
    private double actionPotentialThreshold; // Threshold for firing
    private boolean isExcitatory; // Indicates if the neuron is excitatory

    public Multipolar(double restingPotential, double actionPotentialThreshold, boolean isExcitatory) {
        this.restingPotential = restingPotential;
        this.actionPotentialThreshold = actionPotentialThreshold;
        this.membranePotential = restingPotential; // Initialize to resting potential
        this.isExcitatory = isExcitatory;
    }

    public double getMembranePotential() {
        return membranePotential;
    }

    public void receiveInput(double input) {
        // Simulate input affecting the membrane potential
        if (isExcitatory) {
            membranePotential += input;
        } else {
            membranePotential -= input;
        }
    }

    public void updateMembranePotential() {
        // Simulate membrane potential dynamics (e.g., leakage)
        // You can add more complex dynamics here if needed.
        membranePotential -= 0.1; // Example: Gradually decrease potential
    }

    public boolean shouldFire() {
        return membranePotential >= actionPotentialThreshold;
    }

    public void fire() {
        System.out.println("Neuron fired!");
        membranePotential = restingPotential; // Reset to resting potential after firing
    }

    public static void main(String[] args) {
        // Create a list of neurons to simulate a neural network
        List<Multipolar> neuralNetwork = new ArrayList<>();
        Random random = new Random();

        // Add excitatory neurons to the network
        for (int i = 0; i < 5; i++) {
            neuralNetwork.add(new Multipolar(-70.0, -55.0, true));
        }

        // Add inhibitory neurons to the network
        for (int i = 0; i < 5; i++) {
            neuralNetwork.add(new Multipolar(-70.0, -55.0, false));
        }

        // Simulate network activity
        for (int timeStep = 0; timeStep < 10; timeStep++) {
            System.out.println("Time Step " + timeStep);

            // Provide random inputs to neurons
            for (Multipolar neuron : neuralNetwork) {
                neuron.receiveInput(random.nextDouble() * 10.0);
            }

            // Update membrane potentials
            for (Multipolar neuron : neuralNetwork) {
                neuron.updateMembranePotential();
            }

            // Check for firing and fire neurons
            for (Multipolar neuron : neuralNetwork) {
                if (neuron.shouldFire()) {
                    neuron.fire();
                }
            }
        }
    }
}
