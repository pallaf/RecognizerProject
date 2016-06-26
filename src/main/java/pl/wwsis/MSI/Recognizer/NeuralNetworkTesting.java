/**
 * 
 */
package pl.wwsis.MSI.Recognizer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.ImageRecognitionPlugin;
import org.neuroph.imgrec.ImageSizeMismatchException;

/**
 * NOT USED YET !!!!!
 *
 */

public class NeuralNetworkTesting {

	private ArrayList<String> results = new ArrayList<String>();
	private HashMap<String, Double> output;
	private Iterator<String> iterador = output.keySet().iterator();
	
	NeuralNetworkTesting(String path, String testImagesFolder) throws ImageSizeMismatchException, IOException
	{
		NeuralNetwork<?> nnet = NeuralNetwork.createFromFile(path);
		ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin) nnet.getPlugin(ImageRecognitionPlugin.class);
		File directorio = new File (testImagesFolder);

		for (File file : directorio.listFiles())
		{
			output   = imageRecognition.recognizeImage(file);
			iterador = output.keySet().iterator ();
	    	while (iterador.hasNext())
	    	{
	    		String image = iterador.next();
	    		Double value = output.get(image);
	    		if (value != null && value >= 0.70)
	    			results.add (image);
	    	}
	    }            
	}
// ---------------------------------------------------------------------------------------------------------------	
	public ArrayList<String> getTestResults(){
		return results;
		
	}
}
