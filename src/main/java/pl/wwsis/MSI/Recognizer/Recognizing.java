package pl.wwsis.MSI.Recognizer; 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.awt.image.BufferedImage;
import java.util.TreeMap;

//import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.neuroph.imgrec.ImageRecognitionPlugin;

import org.neuroph.core.NeuralNetwork;

/*
 *Class Recognizing
 */
public class Recognizing
{
	
	private String filePathToFolder = "D:\\Git\\Rep\\Recognizer\\src\\main\\java\\pl\\wwsis\\MSI\\Resources\\";
//	private String filePath, filePath1, filePath2, filePath3, filePath4, filePath5, filePath6, filePath7, filePath8;
//	private String[] filePathes= {filePath, filePath1, filePath2, filePath3, filePath4, filePath5, filePath6, filePath7, filePath8};
	private String[] filePathes;

	private String  filePath = filePathToFolder + "firstNN.nnet";
	private String	filePath1 = filePathToFolder + "calibri_net.nnet";
	private String	filePath2 = filePathToFolder + "chiller_net.nnet";
	private String  filePath3 = filePathToFolder + "handwitten_net.nnet";
	private String	filePath4 = filePathToFolder + "secondhandwitten_net.nnet";
	private String  filePath5 = filePathToFolder + "numI_nn.nnet";
	private String  filePath6 = filePathToFolder + "numII_nn.nnet";
	private String  filePath7 = filePathToFolder + "numIII_nn.nnet";
//	private String  filePath8 = filePathToFolder + "numIV_nn.nnet";
	
	
//	private static final Logger log= Logger.getLogger(Recognizing.class.getName());
	
	private File file = new File(filePath);

	private static ImageRecognitionPlugin imageRecognition;
	@SuppressWarnings({ "unused", "rawtypes" })
	private NeuralNetwork nnet, nnet1, nnet2, nnet3, nnet4, nnet5, nnet6, nnet7;
	@SuppressWarnings("rawtypes")
	private Set<NeuralNetwork> nnett = new HashSet<NeuralNetwork>();
	@SuppressWarnings("unused")
	private InputStream nnetStream;
	
	private Map<String, Double> resultsMap = new TreeMap<String, Double>();
	
	private Map<String, Double> resultsAvrMap = new HashMap<String, Double>();
	private List<String> dupliList = new LinkedList<String>();
	Map<String, Double> sortedMapAsc = new LinkedHashMap<String, Double>();
	
	/*
	 *Recognizing Constructor
	 *Neural Network loaded from recourses (opened stream) 
	 *@param nnet				The neural network for the recognition
	 */
// ---------------------------------------------------------------------------------------------------------------	
	public Recognizing() throws IOException
	{
//		setFilePathes();
		FileInputStream stream = new FileInputStream(filePath);
		FileInputStream stream1 = new FileInputStream(filePath1);
		FileInputStream stream2 = new FileInputStream(filePath2);
		FileInputStream stream3 = new FileInputStream(filePath3);
		FileInputStream stream4 = new FileInputStream(filePath4);

		FileInputStream stream5 = new FileInputStream(filePath5);
		FileInputStream stream6 = new FileInputStream(filePath6);
		FileInputStream stream7 = new FileInputStream(filePath7);
//		FileInputStream stream8 = new FileInputStream(filePath8);
		
		try 
		{	
			nnett.add(nnet = NeuralNetwork.load(stream));
			nnett.add(nnet1 = NeuralNetwork.load(stream1));
			nnett.add(nnet2 = NeuralNetwork.load(stream2));
			nnett.add(nnet3 = NeuralNetwork.load(stream3));
			nnett.add(nnet4 = NeuralNetwork.load(stream4));
			nnett.add(nnet5 = NeuralNetwork.load(stream5));
			nnett.add(nnet6 = NeuralNetwork.load(stream6));
			nnett.add(nnet7 = NeuralNetwork.load(stream7));
//			nnett.add(nnet8 = NeuralNetwork.load(stream8));
		}
		catch(Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Neural network could not be created!","Error",JOptionPane.ERROR_MESSAGE);
			System.err.println("ERRRRRRORRR"+ " e " + e.getMessage());
//			log.log( Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
		finally
		{
			if (stream != null)  {stream.close(); }
			if (stream1 != null) {stream1.close();}
			if (stream2 != null) {stream2.close();}
			if (stream3 != null) {stream3.close();}
			if (stream4 != null) {stream4.close();}

			if (stream5 != null) {stream5.close();}
			if (stream6 != null) {stream6.close();}
			if (stream7 != null) {stream7.close();}
//			if (stream8 != null) {stream8.close();}
		}
	}
	
	/*
	 *Recognizing Constructor
	 *@param String 		file	Path of the already created neural network	
	 *@param NeuralNetwork 	nnet	The neural network for the recognition
	 */
// ---------------------------------------------------------------------------------------------------------------		
    public Recognizing(String file){
    	try 
		{
    		File folder = new File(filePathToFolder);
    		File[] listOfFiles = folder.listFiles();

    		for (File f : listOfFiles) {
    		    if (f.isFile()) {
    		    	if(Image_Filter.getExtension(f).equals("nnet"))
    		    	nnett.add(NeuralNetwork.createFromFile(f));
    		    }
    		}
		}
		catch(Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Neural network could not be created!","Error",JOptionPane.ERROR_MESSAGE);
//			log.log( Level.SEVERE, e.toString(), e);
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
 // ---------------------------------------------------------------------------------------------------------------				
	@SuppressWarnings({ "unchecked", "rawtypes" })
	String getRecognitionResult(File image)
	{
		if(file.exists())
		{
			try {
				resultsMap.clear();
				sortedMapAsc.clear();
				for (NeuralNetwork nn : nnett )
				{	
					imageRecognition = (ImageRecognitionPlugin)nn.getPlugin(ImageRecognitionPlugin.class);
					getNumRecoForEachFont(imageRecognition, image);
				}
				lookingForResult();
				Double maxValueInMap=(Collections.max(sortedMapAsc.values()));
				for (Entry<String, Double> entry : sortedMapAsc.entrySet()) {
		            if (entry.getValue().equals(maxValueInMap)) {
//		            	log.log( Level.FINE, "Koncowy wynik rozpoznawania: " + entry.getKey() + ": " + entry.getValue());
		                return entry.getKey() + ": " + entry.getValue();
		            }
				}
				return null;
			}
			catch(Exception e) 
			{
//				log.log( Level.SEVERE, e.toString(), e);
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
	
// ---------------------------------------------------------------------------------------------------------------		
	private void getNumRecoForEachFont(ImageRecognitionPlugin imageRecognition, File image){
		
		try {
			//image recognition is done here (specify some existing image file)
			HashMap<String, Double> outputMap = imageRecognition.recognizeImage(image);
			Double maxValueInMap=(Collections.max(outputMap.values()));
			for (Entry<String, Double> entry : outputMap.entrySet()) {
	            if (entry.getValue()==maxValueInMap) {
	                System.out.println(entry.getKey() + ", " + entry.getValue() + ", " + imageRecognition.getParentNetwork().toString());
	            	resultsMap.put(entry.getKey(), entry.getValue());
	            }
	        }
			sortedMapAsc = sortByComparator(resultsMap);
//			log.log( Level.FINE, "Rezultaty z rozpoznawania dla sieci " + imageRecognition.getParentNetwork().toString() + ": " + resultsMap , resultsMap.size());
		}
		catch(Exception e) 
		{
//			log.log( Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
	}
	
	
// ---------------------------------------------------------------------------------------------------------------		
	void setFilePathes(){
		
		File folder = new File(filePathToFolder);
		File[] listOfFiles = folder.listFiles();

		for (@SuppressWarnings("unused") String s : filePathes) {
			for (File f : listOfFiles) {
				if (f.isFile()) {
					if(Image_Filter.getExtension(f).equals("nnet"))
						s = f.getPath();
				}
		    }
		}
	}
	
// ---------------------------------------------------------------------------------------------------------------		
	private void lookingForResult(){
		resultsAvrMap.clear();
		@SuppressWarnings("unused")
		Double maxValueInMap=(Collections.max(resultsMap.values()));
		for (Entry<String, Double> entry : resultsMap.entrySet()) {
            if (entry.getValue()>0.8) {
               	resultsAvrMap.put(entry.getKey(), entry.getValue());
            }
		}
//		log.log( Level.FINE, "Duplikowane wartosci: " + mostDuplKey() , mostDuplKey().size());
		System.out.println(mostDuplKey());
	}
	
// ---------------------------------------------------------------------------------------------------------------	
	private Set<String> mostDuplKey(){
		for (Entry<String, Double> entry : resultsAvrMap.entrySet()) {
            dupliList.add(entry.getKey());
		}
//		log.log( Level.FINE, "Zwraca mape z elementami duplikowanymi: " + dupliList , dupliList.size() );
		return findDuplicates(dupliList);
	}
	
// ---------------------------------------------------------------------------------------------------------------
	private Set<String> findDuplicates(List<String> listContainingDuplicates) {
			 
		Set<String> setToReturn = new HashSet<String>();
		Set<String> set1 = new HashSet<String>();
	 	for (String yourInt : listContainingDuplicates) {
			if (!set1.add(yourInt)) {
				setToReturn.add(yourInt);
			}
		}
//	 	log.log( Level.FINE, "Zwraca zbior duplikatw: " + setToReturn , setToReturn.size() );
		return setToReturn;
	}
	
// ---------------------------------------------------------------------------------------------------------------	
	private static Map<String, Double> sortByComparator(Map<String, Double> unsortMap)
    {
        List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Double>>()
        {
            public int compare(Entry<String, Double> o1,
                    Entry<String, Double> o2)
            {
                    return o1.getValue().compareTo(o2.getValue());
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Entry<String, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

// ---------------------------------------------------------------------------------------------------------------		
	public static void printMap(Map<String, Double> sortedMapAsc2)
    {
        for (Entry<String, Double> entry : sortedMapAsc2.entrySet())
        {
        	System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }
}