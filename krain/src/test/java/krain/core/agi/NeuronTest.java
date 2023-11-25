
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
        excitatoryNeuron.stimulate(0.7);
        assertEquals(0.7, excitatoryNeuron.getLastOutput(), 0.001);
    }

    @Test
    public void testStimulateInhibitoryNeuron() {
        // Test stimulating an inhibitory neuron
        inhibitoryNeuron.stimulate(0.7);
        assertEquals(0.7, inhibitoryNeuron.getLastOutput(), 0.001);
    }

    @Test
    public void testDynamicLearningRate() {
        // Test dynamic learning rate adjustment
        excitatoryNeuron.stimulate(0.5);
        double initialLearningRate = excitatoryNeuron.calculateLearningRate(0.5);
        excitatoryNeuron.stimulate(0.5);
        double adjustedLearningRate = excitatoryNeuron.calculateLearningRate(0.5);
        assertEquals(initialLearningRate * 0.5, adjustedLearningRate, 0.001);
    }

    @Test
    public void testRefractoryPeriod() throws InterruptedException {
        // Test refractory period
        excitatoryNeuron.stimulate(0.7);
        double outputDuringRefractory = excitatoryNeuron.getLastOutput();
        Thread.sleep(1500); // Wait for refractory period to end
        excitatoryNeuron.stimulate(0.7);
        double outputAfterRefractory = excitatoryNeuron.getLastOutput();
        assertEquals(outputDuringRefractory, outputAfterRefractory, 0.001);
    }

    @Test
    public void testConnectToPreSynapse() {
        // Test connecting to pre-synapse(s)
        Neuron preSynapse1 = new Neuron(0.5, 0.3, 800);
        Neuron preSynapse2 = new Neuron(0.6, 0.4, 1000);
        excitatoryNeuron.connectToPreSynapse(preSynapse1);
        excitatoryNeuron.connectToPreSynapse(preSynapse2);
        assertEquals(2, excitatoryNeuron.getPreSynapses().size());
    }    

    @Test
    public void testConnectToMultiplePreSynapses() {
        // Test connecting to multiple pre-synapses at once
        ArrayList<Neuron> preSynapses = new ArrayList<>();
        preSynapses.add(new Neuron(0.7, 0.5, 1200));
        preSynapses.add(new Neuron(0.8, 0.6, 1500));
        excitatoryNeuron.connectToPreSynapse(preSynapses);
        assertEquals(2, excitatoryNeuron.getPreSynapses().size());
    }
    // Add more test cases as needed

}
