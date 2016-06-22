package pl.wwsis.MSI.Recognizer; 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.neuroph.imgrec.ImageRecognitionPlugin;
import org.neuroph.core.NeuralNetwork;

/*
 *Class Recognizing
 */
public class Recognizing
{
//	private String filePath = "/Recognizer/src/main/java/pl/wwsis/MSI/Resources/recognizing_net.nnet";
	private String filePath = "D:\\Git\\Rep\\Recognizer\\src\\main\\java\\pl\\wwsis\\MSI\\Resources\\recognizing_net.nnet";
	File file = new File(filePath);

	ImageRecognitionPlugin imageRecognition;
	NeuralNetwork nnet;
	InputStream nnetStream;
	
	/*
	 *Recognizing Constructor
	 *Neural Network loaded from recourses (opened stream) 
	 *@param nnet				The neural network for the recognition
	 */
	public Recognizing() throws IOException
	{
		FileInputStream stream = new FileInputStream(filePath);
		try 
		{	
			nnet = NeuralNetwork.load(stream);
		}
		catch(Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Neural network could not be created!","Error",JOptionPane.ERROR_MESSAGE);
			System.err.println("ERRRRRRORRR"+ " e " + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if (stream != null) {
				stream.close();
			}
		}

	}
	
	/*
	 *Recognizing Constructor
	 *@param String 		file	Path of the already created neural network	
	 *@param NeuralNetwork 	nnet	The neural network for the recognition
	 */
    public Recognizing(String file){
    	try 
		{
    		nnet = NeuralNetwork.createFromFile(file);
    		
		}
		catch(Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Neural network could not be created!","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
    }
	

	/*
	 *Recognize an image with number, the output will be a recognized number 
	 *@param nnet				The neural network for the recognition
	 *@param imageRecognition	Image recognition plugin for image recognition 
	 *@param outputMap	  		The output name of the HashMap which contains <String, Double> (string "number", double value of recognition result)
	 *@param maxValueInMap 		The max value in the Hashmap
	 *@param entry 				Used param for iteration of the through hashmap
	 */
			
	String getRecognitionResult(File image)
	{
		if(file.exists())
		{
			try {
				imageRecognition = (ImageRecognitionPlugin)nnet.getPlugin(ImageRecognitionPlugin.class);
				//image recognition is done here (specify some existing image file)
				HashMap<String, Double> outputMap = imageRecognition.recognizeImage(image);
				Double maxValueInMap=(Collections.max(outputMap.values()));
//				System.out.println(outputMap.toString());
				for (Entry<String, Double> entry : outputMap.entrySet()) {
		            if (entry.getValue()==maxValueInMap) {
		                System.out.println(entry.getKey());
		                return entry.getKey();
		            }
		        }
//				return outputMap.toString();
		        return null;
			}
			catch(Exception e) 
			{
				e.printStackTrace();
			}
			return null;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Image could not be read!","Error",JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
//	private BufferedImage getImage(File f)
//	{
//		BufferedImage 	image	= null;
//		
//		try
//		{
//			image = ImageIO.read(f);
//			
//		}
//		catch(Exception ex) 
//		{
//			JOptionPane.showMessageDialog(null, "Image could not be read!","Error",JOptionPane.ERROR_MESSAGE);
//			ex.printStackTrace();
//		}
//		return image;
//	}
}

