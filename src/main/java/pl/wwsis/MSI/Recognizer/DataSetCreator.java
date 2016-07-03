/**
 * 
 */
package pl.wwsis.MSI.Recognizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neuroph.core.data.DataSet;
import org.neuroph.imgrec.FractionRgbData;
import org.neuroph.imgrec.ImageRecognitionHelper;
import org.neuroph.imgrec.ImageUtilities;

/**
 * 
 *
 */
public class DataSetCreator {

	private File folder;
	private List<String> imageFolderPaths = new ArrayList<String> ();
	private List<String> imageLabels = new ArrayList<String> ();
	private HashMap<String, BufferedImage> imagesMap = new HashMap<String, BufferedImage> ();
	private DataSet dataSet;
	
	DataSetCreator(String filePathToFolder)
	{
		this.preaperingImagesForTrainingSet(filePathToFolder);
		this.createDataSet();
	}
// ---------------------------------------------------------------------------------------------------------------	
	private void preaperingImagesForTrainingSet(String filePathToFolder)
	{	
		String extension = "";
		folder = new File (filePathToFolder);
		for (File file : folder.listFiles ())
		{
			if (Image_Filter.getExtension(file).equals("jpg")) {extension = ".jpg";};
			if (Image_Filter.getExtension(file).equals("png")) {extension = ".png";};
			imageLabels.add (file.getName ().replace (extension, ""));
			imagesMap.put (file.getName (), ImageUtilities.resizeImage (ImageUtilities.loadImage (file), 20, 20));
		}
	}
	
// ---------------------------------------------------------------------------------------------------------------	
	private void createDataSet()
	{
		Map<String, FractionRgbData> imageRgbData = ImageUtilities.getFractionRgbDataForImages (imagesMap);
		this.dataSet = ImageRecognitionHelper.createBlackAndWhiteTrainingSet (imageLabels, imageRgbData);
		addJunkImages(dataSet.getOutputSize());
	}

// ---------------------------------------------------------------------------------------------------------------	
	public List<String> getImageLabels()
	{
		return imageLabels;
	}

// ---------------------------------------------------------------------------------------------------------------	
	public DataSet getDataSet()
	{
		return dataSet;
	}

// ---------------------------------------------------------------------------------------------------------------	
	private void addJunkImages(int j)
	{
		// Adding the black and white "not to recognize" images
		double [] inputWhite = new double [20 * 20];
		for (int i = 0; i < (20 * 20); ++i)
		{    
			inputWhite [i] = 1;
			}
		double [] outputWhite = new double[j];
		for (int i = 0; i < j; ++i)
		{    
			outputWhite [i] = 0;
		}
		dataSet.addRow (inputWhite, outputWhite);
		double [] inputBlack = new double [20 * 20];
		for (int i = 0; i < (20 * 20); ++i)
		{    
			inputBlack [i] = 0;
			}
		double [] outputBlack = new double[j];
		for (int i = 0; i < j; ++i)
		{    
			outputBlack [i] = 0;
		}
		dataSet.addRow (inputBlack, outputBlack);
	}

// ---------------------------------------------------------------------------------------------------------------	
//	public static void main(String args[])
//	{
//		new DataSetCreator(null);
//	}
}
