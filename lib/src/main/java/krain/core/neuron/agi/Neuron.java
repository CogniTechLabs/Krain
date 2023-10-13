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

package krain.core.neuron.agi;

import krain.core.constants.Neurotransmitters;

import java.util.*;

public class Neuron {
    private double membranePotential; // Membrane potential
    private double restingPotential; // Resting potential
    private double actionPotentialThreshold; // Threshold for firing
    private Map<Neuron, Map<String, Double>> preSynapticConnections; // Presynaptic neurons and their transmitter strengths
    private Map<Neuron, Map<String, Double>> postSynapticConnections; // Postsynaptic neurons and their transmitter strengths
    private boolean isExcitatory; // Indicates if the neuron is excitatory

    public Neuron(boolean isExcitatory, double restingPotential, double actionPotentialThreshold) {
        this.isExcitatory = isExcitatory;
        this.restingPotential = restingPotential;
        this.actionPotentialThreshold = actionPotentialThreshold;
        this.preSynapticConnections = new HashMap<>();
        this.postSynapticConnections = new HashMap<>();
        this.membranePotential = restingPotential;
    }

    public void establishSynapse(Neuron preSynapse, double synapticWeight) {
        preSynapticConnections.put(preSynapse, new HashMap<>());
        postSynapticConnections.put(preSynapse, new HashMap<>());
    }

    public void setNeurotransmitterStrength(Neuron preSynapse, String transmitter, double strength) {
        if (preSynapticConnections.containsKey(preSynapse)) {
            preSynapticConnections.get(preSynapse).put(transmitter, strength);
        }
    }

    public Map<String, Double> stimulate(Map<String, Double> input) {
        // Simulate input affecting the membrane potential
        double strengthAdjustment = isExcitatory ? input.values().stream().mapToDouble(Double::doubleValue).sum() :
                -input.values().stream().mapToDouble(Double::doubleValue).sum();
        membranePotential += strengthAdjustment;

        // Calculate neurotransmitter release based on synaptic weights and strengths
        Map<String, Double> transmitterReleaseMap = new HashMap<>();
        for (Map.Entry<Neuron, Map<String, Double>> synapseEntry : preSynapticConnections.entrySet()) {
            Neuron preSynapse = synapseEntry.getKey();
            Map<String, Double> transmitterStrengths = synapseEntry.getValue();

            for (Map.Entry<String, Double> transmitterEntry : transmitterStrengths.entrySet()) {
                String transmitter = transmitterEntry.getKey();
                double strength = transmitterEntry.getValue();

                if ((isExcitatory && membranePotential >= actionPotentialThreshold) ||
                        (!isExcitatory && membranePotential <= actionPotentialThreshold)) {
                    double yield = input.getOrDefault(transmitter, 0.0);
                    double releaseAmount = Neurotransmitters.release(transmitter, yield * strength);
                    transmitterReleaseMap.put(transmitter, transmitterReleaseMap.getOrDefault(transmitter, 0.0) + releaseAmount);
                }
            }
        }

        // Reset the membrane potential regardless of firing or not
        membranePotential = restingPotential;

        // Print the release amount
        System.out.println("Neuron fired! Total neurotransmitter released: " + transmitterReleaseMap);
        return transmitterReleaseMap;
    }

    public static void main(String[] args) {
        // Create neurons with their original parameters
        Neuron excitatoryNeuron = new Neuron(true, -70.0, -55.0);
        Neuron inhibitoryNeuron = new Neuron(false, -70.0, 10.0);
        Neuron secondExcitatoryNeuron = new Neuron(true, -70.0, -50.0);

        // Establish synapses and set neurotransmitter strengths
        excitatoryNeuron.establishSynapse(inhibitoryNeuron, 0.5);
        excitatoryNeuron.setNeurotransmitterStrength(inhibitoryNeuron, "GABA", 0.5);
        excitatoryNeuron.setNeurotransmitterStrength(inhibitoryNeuron, "DOPAMINE", 0.8);

        excitatoryNeuron.establishSynapse(secondExcitatoryNeuron, 6.0);
        excitatoryNeuron.setNeurotransmitterStrength(secondExcitatoryNeuron, "GABA", 5.0);
        excitatoryNeuron.setNeurotransmitterStrength(secondExcitatoryNeuron, "GLUTAMATE", 10.0);
        excitatoryNeuron.setNeurotransmitterStrength(secondExcitatoryNeuron, "DOPAMINE", 43.0);

        // Create input dictionary
        Map<String, Double> input = new HashMap<>();
        input.put("GABA", 54.0);
        input.put("GLUTAMATE", 37.3);
        input.put("DOPAMINE", 80.23);

        // Stimulate the neuron and print the total release amount
        Map<String, Double> result = excitatoryNeuron.stimulate(input);
    }
}
