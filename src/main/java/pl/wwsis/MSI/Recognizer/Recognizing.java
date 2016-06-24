package pl.wwsis.MSI.Recognizer; 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
//	private String filePath = "D:\\Git\\Rep\\Recognizer\\src\\main\\java\\pl\\wwsis\\MSI\\Resources\\recognizing_net.nnet";
	private String filePath = "D:\\Git\\Rep\\Recognizer\\src\\main\\java\\pl\\wwsis\\MSI\\Resources\\firstNN.nnet";
	private String filePath1 = "D:\\Git\\Rep\\Recognizer\\src\\main\\java\\pl\\wwsis\\MSI\\Resources\\calibri_net.nnet";
	private String filePath2 = "D:\\Git\\Rep\\Recognizer\\src\\main\\java\\pl\\wwsis\\MSI\\Resources\\chiller_net.nnet";
	private String filePath3 = "D:\\Git\\Rep\\Recognizer\\src\\main\\java\\pl\\wwsis\\MSI\\Resources\\handwitten_net.nnet";
	private String filePath4 = "D:\\Git\\Rep\\Recognizer\\src\\main\\java\\pl\\wwsis\\MSI\\Resources\\secondhandwitten_net.nnet";
	
	private File file = new File(filePath);

	private ImageRecognitionPlugin imageRecognition;
	private NeuralNetwork nnet, nnet1, nnet2, nnet3, nnet4, nnet5;
	private Set<NeuralNetwork> nnett = new HashSet<NeuralNetwork>();
	private InputStream nnetStream;
	
	private Map<String, Double> resultsMap = new HashMap<String, Double>();
	
	/*
	 *Recognizing Constructor
	 *Neural Network loaded from recourses (opened stream) 
	 *@param nnet				The neural network for the recognition
	 */
	public Recognizing() throws IOException
	{

		
		FileInputStream stream = new FileInputStream(filePath);
		FileInputStream stream1 = new FileInputStream(filePath1);
		FileInputStream stream2 = new FileInputStream(filePath2);
		FileInputStream stream3 = new FileInputStream(filePath3);
		FileInputStream stream4 = new FileInputStream(filePath4);
		
		try 
		{	
			nnett.add(nnet = NeuralNetwork.load(stream));
			nnett.add(nnet1 = NeuralNetwork.load(stream1));
			nnett.add(nnet2 = NeuralNetwork.load(stream2));
			nnett.add(nnet3 = NeuralNetwork.load(stream3));
			nnett.add(nnet4 = NeuralNetwork.load(stream4));
		}
		catch(Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Neural network could not be created!","Error",JOptionPane.ERROR_MESSAGE);
			System.err.println("ERRRRRRORRR"+ " e " + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if (stream != null)  {stream.close(); }
			if (stream1 != null) {stream1.close();}
			if (stream2 != null) {stream2.close();}
			if (stream3 != null) {stream3.close();}
			if (stream4 != null) {stream4.close();}
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
    		File folder = new File("D:\\Git\\Rep\\Recognizer\\src\\main\\java\\pl\\wwsis\\MSI\\Resources\\");
    		File[] listOfFiles = folder.listFiles();

    		for (File f : listOfFiles) {
    		    if (f.isFile()) {
    		    	if(Image_Filter.getExtension(f).equals("nnet"))
    		    	nnett.add(NeuralNetwork.createFromFile(f));
    		    }
    		}
//    		nnet = NeuralNetwork.createFromFile(file);
    		
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
				String result = ""; int i = 0;
				resultsMap.clear();
				for (NeuralNetwork nn : nnett )
				{	
					i++;
					imageRecognition = (ImageRecognitionPlugin)nn.getPlugin(ImageRecognitionPlugin.class);
					if (i != 1){result += ", ";};
					getNumRecoForEachFont(imageRecognition, image);
//					result += getNumRecoForEachFont(imageRecognition, image);
					
				}
				Double maxValueInMap=(Collections.max(resultsMap.values()));
				for (Entry<String, Double> entry : resultsMap.entrySet()) {
		            if (entry.getValue()==maxValueInMap) {
		                return entry.getKey() + ": " + entry.getValue();
		            }
				}
//				System.out.println(result);
//				return result;
				return null;
				
//				imageRecognition = (ImageRecognitionPlugin)nnet.getPlugin(ImageRecognitionPlugin.class);
//				//image recognition is done here (specify some existing image file)
//				HashMap<String, Double> outputMap = imageRecognition.recognizeImage(image);
//				Double maxValueInMap=(Collections.max(outputMap.values()));
////				System.out.println(outputMap.toString());
//				for (Entry<String, Double> entry : outputMap.entrySet()) {
//		            if (entry.getValue()==maxValueInMap) {
//		                System.out.println(entry.getKey());
//		                return entry.getKey();
//		            }
//		        }
////				return outputMap.toString();
				
//		        return null;
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
	
	private void getNumRecoForEachFont(ImageRecognitionPlugin imageRecognition, File image){
		
		try {
//			imageRecognition = (ImageRecognitionPlugin)nnet.getPlugin(ImageRecognitionPlugin.class);
			//image recognition is done here (specify some existing image file)
			HashMap<String, Double> outputMap = imageRecognition.recognizeImage(image);
			Double maxValueInMap=(Collections.max(outputMap.values()));
//			System.out.println(outputMap.toString());
			for (Entry<String, Double> entry : outputMap.entrySet()) {
	            if (entry.getValue()==maxValueInMap) {
	                System.out.println(entry.getKey() + ", " + entry.getValue() + ", " + imageRecognition.getParentNetwork().toString());
	            	resultsMap.put(entry.getKey(), entry.getValue());
//	                return entry.getKey() + ": " + entry.getValue();
	            }
	        }
//			return outputMap.toString();
//	        return null;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
//		return null;
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