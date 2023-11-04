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

package krain.core.agi;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class Neuron {
    private double excitThreshold;
    private double excitBaselineRate;
    private double excitRefractoryPeriod;

    private double inhibThreshold;
    private double inhibBaselineRate;
    private double inhibRefractoryPeriod;

    private boolean isExcit;

    private double lastOutput;

    private ArrayList<Neuron> preSynapses = new ArrayList<Neuron>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public Neuron(double threshold, double baselineRate, double refractoryPeriod) {
        this.excitThreshold = threshold;
        this.excitBaselineRate = baselineRate;
        this.excitRefractoryPeriod = refractoryPeriod;
        this.isExcit = true;

    }

    public Neuron(double threshold, double baselineRate, double refractoryPeriod, boolean isExcit) {
        if (!isExcit) {
            this.inhibThreshold = threshold;
            this.inhibBaselineRate = baselineRate;
            this.inhibRefractoryPeriod = refractoryPeriod;
            this.isExcit = false;
        } else {
            this.excitThreshold = threshold;
            this.excitBaselineRate = baselineRate;
            this.excitRefractoryPeriod = refractoryPeriod;
            this.isExcit = true;
        }
    }

    public void connectToPreSynapse(Neuron neuron) {
        preSynapses.add(neuron);
        System.out.println("Connected to neuron: " + neuron);
    }

    public void connectToPreSynapse(ArrayList<Neuron> neurons) {
        preSynapses.addAll(neurons);
        System.out.println("Connected to neurons: " + neurons);
    }

    public void getPreSynapses() {
        System.out.println(preSynapses);
    }

    public void innerClock() {
        ScheduledExecutorService clock = Executors.newSingleThreadScheduledExecutor();
        clock.scheduleAtFixedRate(() -> {
            // Get the current time and display it (this can be replaced with your desired clock logic)
            System.out.println("Current time: " + System.currentTimeMillis());
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void stimulate(double input) {
        // System.out.println("Stimulated");
        double currentOutput[] = {0.0};
        AtomicBoolean refractory = new AtomicBoolean(false);

        executor.execute(() -> {
            if (!refractory.get()) { // Check if not in refractory period
                double output = 0.0;

                if (isExcit) {
                    if (excitBaselineRate + input >= excitThreshold) {
                        output = input;

                        double excitLearningRate = calculateLearningRate(input); // Dynamic learning rate for excitatory neurons
                        excitThreshold += excitLearningRate * input;
                    } else {
                        excitThreshold -= input;
                    }
                } else {
                    if (inhibBaselineRate - input <= inhibThreshold) {
                        output = input;

                        double inhibLearningRate = calculateLearningRate(input); // Dynamic learning rate for inhibitory neurons
                        inhibThreshold += inhibLearningRate * input;
                    } else {
                        inhibThreshold -= input;
                    }
                }
                currentOutput[0] = output;
            }

            // System.out.println(currentOutput[0]);
            for (Neuron neuron : preSynapses) {
                neuron.stimulate(currentOutput[0]);
            }
        });
        if (isExcit) { // Check if the neuron is excitatory
            refractory.set(true); // Set neuron to refractory state

            // Simulating the refractory period using a timer
            ScheduledExecutorService refractoryTimer = Executors.newSingleThreadScheduledExecutor();
            refractoryTimer.schedule(() -> {
                refractory.set(false); // Reset neuron to non-refractory state
            }, (long) excitRefractoryPeriod, TimeUnit.MILLISECONDS);

            refractoryTimer.shutdown();
        } else {
            refractory.set(true); // Set neuron to refractory state

            // Simulating the refractory period using a timer
            ScheduledExecutorService refractoryTimer = Executors.newSingleThreadScheduledExecutor();
            refractoryTimer.schedule(() -> {
                refractory.set(false); // Reset neuron to non-refractory state
            }, (long) inhibRefractoryPeriod, TimeUnit.MILLISECONDS);

            refractoryTimer.shutdown();
        }
    }
    
    private double calculateLearningRate(double input) {
        // Example of a dynamic learning rate adjustment based on the input
        double baseLearningRate = 0.1;
        double dynamicRate = baseLearningRate * input; // Adjust as needed based on the specific scenario
        return dynamicRate;
    }    

    public double getLastOutput() {
        return lastOutput;
    }

    public void stopThread() {
        executor.shutdown();
    }

    public static void main(String[] args) {
        Neuron neuron11 = new Neuron(2.0, 0.5, 0.1);
        Neuron neuron12 = new Neuron(2.2, 0.5, 0.07);
        Neuron neuron21 = new Neuron(1.4, 0.3, 0.06);
        Neuron neuron22 = new Neuron(1.5, 0.7, 0.08);

        ArrayList<Neuron> layer2Neurons = new ArrayList<>();

        layer2Neurons.add(neuron21);
        layer2Neurons.add(neuron22);

        neuron11.connectToPreSynapse(neuron21);
        neuron11.connectToPreSynapse(neuron22);

        neuron12.connectToPreSynapse(layer2Neurons);

        System.out.println("Neuron 1 Layer 1 Presynapses:");
        neuron11.getPreSynapses();

        System.out.println("Neuron 2 Layer 1 Presynapses:");
        neuron12.getPreSynapses();

        neuron11.stimulate(4.5);
        neuron12.stimulate(2.2);
    }
}
