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

package krain.core.constants;

import java.util.HashMap;

public class Neurotransmitters {
    private static final HashMap<String, Double> neurotransmitters = new HashMap<>();

    // Static initializer block to initialize neurotransmitters
    static {
        neurotransmitters.put("GLUTAMATE", 50.0);
        neurotransmitters.put("GABA", 10.0);
        neurotransmitters.put("SEROTONIN", 0.1);
        neurotransmitters.put("DOPAMINE", 0.01);
        neurotransmitters.put("ACETYLCHOLINE", 0.5);
        neurotransmitters.put("ENDORPHINS", 0.001);
        neurotransmitters.put("SUBSTANCE_P", 0.0001);
        neurotransmitters.put("NOREPINEPHRINE", 0.01);
        neurotransmitters.put("EPINEPHRINE", 0.001);
    }

    // Get the concentration of a neurotransmitter by name
    public static double getConcentration(String name) {
        Double concentration = neurotransmitters.get(name);
        if (concentration != null) {
            return concentration;
        } else {
            throw new IllegalArgumentException("Neurotransmitter not found: " + name);
        }
    }

    // Method to release neurotransmitter
    public static double release(String name, double yield) {
        double concentration = getConcentration(name);
        return Math.round(concentration * yield / 10.0);
    }

    public static void main(String[] args) {
        // Example usage: release neurotransmitter and print the result
        double releasedAmount = release("GLUTAMATE", 39.0);
        System.out.println("Released amount of GLUTAMATE: " + releasedAmount);
    }
}



