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
 * @author PiotrAllaf
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
		this.addJunkImages();
	}
	
	private void preaperingImagesForTrainingSet(String filePathToFolder)
	{	
		folder = new File (filePathToFolder);
		for (File file : folder.listFiles ())
		{
			imageLabels.add (file.getName ().replace (".jpg", ""));
			imagesMap.put (file.getName (), ImageUtilities.resizeImage (ImageUtilities.loadImage (file), 20, 20));
		}
	}
	
	private void createDataSet()
	{
		Map<String, FractionRgbData> imageRgbData = ImageUtilities.getFractionRgbDataForImages (imagesMap);
		this.dataSet = ImageRecognitionHelper.createBlackAndWhiteTrainingSet (imageLabels, imageRgbData);
	}
	
	public List<String> getImageLabels()
	{
		return imageLabels;
	}
	
	public DataSet getDataSet()
	{
		return dataSet;
	}
	
	private void addJunkImages()
	{
		

		// Adding the black and white "not to recognize" images
		double [] inputWhite = new double [34 * 56];
		for (int i = 0; i < (34 * 56); ++i)
		{    
			inputWhite [i] = 1;
			}
		double [] outputWhite = { 0, 0, 0, 0, 0, 0 };
		dataSet.addRow (inputWhite, outputWhite);
		double [] inputBlack = new double [34 * 56];
		for (int i = 0; i < (34 * 56); ++i)
		{    
			inputBlack [i] = 0;
			}
		double [] outputBlack = { 0, 0, 0, 0, 0, 0 };
		dataSet.addRow (inputBlack, outputBlack);
	}
	
	public static void main(String args[])
	{
		new DataSetCreator(null);
	}
}
