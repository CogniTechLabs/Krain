package krain.core.agi;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class NeuronTest {

    private Neuron excitatoryNeuron;
    private Neuron inhibitoryNeuron;

    @Before
    public void setUp() {
        // Create a new Neuron instance before each test
        excitatoryNeuron = new Neuron(1.0, 0.5, 1000);
        inhibitoryNeuron = new Neuron(1.0, 0.5, 1000, false);
    }

    @After
    public void tearDown() {
        // Clean up resources after each test
        excitatoryNeuron.stopThread();
        inhibitoryNeuron.stopThread();
    }

    @Test
    public void testStimulateExcitatoryNeuron() {
        // Test stimulating an excitatory neuron
        double input = 0.7;
        excitatoryNeuron.stimulate(input);
        
        double expectedOutput = input;
        double actualOutput = excitatoryNeuron.getLastOutput();

        System.out.println("Test: Stimulate Excitatory Neuron");
        System.out.println("Input: " + input);
        System.out.println("Expected Output: " + expectedOutput);
        System.out.println("Actual Output: " + actualOutput);

        assertEquals(expectedOutput, actualOutput, 0.001);
    }

    @Test
    public void testStimulateInhibitoryNeuron() {
        // Test stimulating an inhibitory neuron
        double input = 0.7;
        inhibitoryNeuron.stimulate(input);
        
        double expectedOutput = input;
        double actualOutput = inhibitoryNeuron.getLastOutput();

        System.out.println("Test: Stimulate Inhibitory Neuron");
        System.out.println("Input: " + input);
        System.out.println("Expected Output: " + expectedOutput);
        System.out.println("Actual Output: " + actualOutput);

        assertEquals(expectedOutput, actualOutput, 0.001);
    }

    @Test
    public void testDynamicLearningRate() {
        // Test dynamic learning rate adjustment
        double input = 0.5;
        excitatoryNeuron.stimulate(input);
        double initialLearningRate = excitatoryNeuron.calculateLearningRate(input);
        excitatoryNeuron.stimulate(input);
        double adjustedLearningRate = excitatoryNeuron.calculateLearningRate(input);

        System.out.println("Test: Dynamic Learning Rate Adjustment");
        System.out.println("Input: " + input);
        System.out.println("Initial Learning Rate: " + initialLearningRate);
        System.out.println("Adjusted Learning Rate: " + adjustedLearningRate);

        assertEquals(initialLearningRate * 0.5, adjustedLearningRate, 0.001);
    }

    @Test
    public void testRefractoryPeriod() throws InterruptedException {
        // Test refractory period
        double input = 0.7;
        excitatoryNeuron.stimulate(input);
        double outputDuringRefractory = excitatoryNeuron.getLastOutput();
        Thread.sleep(1500); // Wait for refractory period to end
        excitatoryNeuron.stimulate(input);
        double outputAfterRefractory = excitatoryNeuron.getLastOutput();

        System.out.println("Test: Refractory Period");
        System.out.println("Input: " + input);
        System.out.println("Output During Refractory: " + outputDuringRefractory);
        System.out.println("Output After Refractory: " + outputAfterRefractory);

        assertEquals(outputDuringRefractory, outputAfterRefractory, 0.001);
    }

    @Test
    public void testConnectToPreSynapse() {
        // Test connecting to pre-synapse(s)
        Neuron preSynapse1 = new Neuron(0.5, 0.3, 800);
        Neuron preSynapse2 = new Neuron(0.6, 0.4, 1000);
        excitatoryNeuron.connectToPreSynapse(preSynapse1);
        excitatoryNeuron.connectToPreSynapse(preSynapse2);

        System.out.println("Test: Connect to Pre-Synapse(s)");
        System.out.println("Pre-Synapses Connected: " + excitatoryNeuron.getPreSynapses());
        
        assertEquals(2, excitatoryNeuron.getPreSynapses().size());
    }    

    @Test
    public void testConnectToMultiplePreSynapses() {
        // Test connecting to multiple pre-synapses at once
        ArrayList<Neuron> preSynapses = new ArrayList<>();
        preSynapses.add(new Neuron(0.7, 0.5, 1200));
        preSynapses.add(new Neuron(0.8, 0.6, 1500));
        excitatoryNeuron.connectToPreSynapse(preSynapses);

        System.out.println("Test: Connect to Multiple Pre-Synapses at Once");
        System.out.println("Pre-Synapses Connected: " + excitatoryNeuron.getPreSynapses());

        assertEquals(2, excitatoryNeuron.getPreSynapses().size());
    }
    // Add more test cases as needed

}
