package pl.wwsis.MSI.Recognizer;
import java.io.*; 

public class Image_Filter extends javax.swing.filechooser.FileFilter
{

// ---------------------------------------------------------------------------------------------------------------	
	protected boolean isImageFile(String extension)
	{
		return (extension.equals("jpg")||extension.equals("png"));
	}
	
// ---------------------------------------------------------------------------------------------------------------	
	public boolean accept(File file)
	{
	    if (file.isDirectory())
	    {
			return true;
	    }

	    String extension = getExtension(file);
		if (extension.equals("jpg")||extension.equals("png"))
		{
			return true;
		}
		return false;
	}
	
// ---------------------------------------------------------------------------------------------------------------	
	public String getDescription()
	{
		return "Supported Image Files";
	}
	
// ---------------------------------------------------------------------------------------------------------------	
	protected static String getExtension(File file)
	{
		String name = file.getName();
		int i = name.lastIndexOf('.');
		if (i > 0 &&  i < name.length() - 1) 
		  return name.substring(i+1).toLowerCase();
		return "";
	}	
}