/**
 * 
 */
package pl.wwsis.MSI.Recognizer;

import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.imgrec.ColorMode;
import org.neuroph.imgrec.ImageRecognitionHelper;
import org.neuroph.imgrec.image.Dimension;
import org.neuroph.util.TransferFunctionType;

/**
 * @author PiotrAllaf
 *
 */
public class NeuralNetworkCreator
{
	
	private ArrayList<Integer> layers = new ArrayList<Integer> ();
	NeuralNetwork<LearningRule> nn;
	
	NeuralNetworkCreator(List<String> imageLabels)
	{
		layers.add (12);
		nn = ImageRecognitionHelper.createNewNeuralNetwork ("recognition", new Dimension (20,20), ColorMode.BLACK_AND_WHITE, imageLabels, layers, TransferFunctionType.SIGMOID);
	}
	
	public NeuralNetwork<LearningRule> getNeuralNetwork()
	{
		return nn;
		
		
	}
}
