package Utilities;

import java.io.*;
import java.util.*;

public class FileHandler
{
    private static String filepath;

    public FileHandler(String f)
    {
        boolean fileExists = setFile(f);
        if (!fileExists)
        {
            System.out.println("An error occurred while trying to read the input file.");
            return;
        }
    }


    private static String createOutputFile()
    {
        String outputFileName = "";
        String filenameNoExtension = filepath.replace(".txt" ,"");
        
        try 
        {
            outputFileName = filenameNoExtension + "_output.txt";
            File fileObj = new File(outputFileName);
            fileObj.createNewFile();
        } 
        catch (IOException e) 
        {
            System.out.println("An error occurred while creating the output file.");
            e.printStackTrace();
        }

        return outputFileName;
    }


    public String writeOutputFile(String data)
    {
        String outputFile = createOutputFile();
        try 
        {
            FileWriter file = new FileWriter(outputFile);
            file.write(data);
            file.close();
            
            String outputMsg = "file output: " + outputFile;
            return outputMsg;

        } 
        catch (IOException e) 
        {
            String errMsg = "An error occurred while creating the output file.";
            e.printStackTrace();
            return errMsg;
        }
    }


    public String readInputFile()
    {
        String data = "";

        try
        {
            File fileObj = new File(filepath);
            Scanner fileReader = new Scanner(fileObj);
            
            while (fileReader.hasNextLine()) 
            {
                data += fileReader.nextLine();
            }

            fileReader.close();
        
        }
        catch (FileNotFoundException e)
        {
            System.out.println("An error occurred while reading the input file.");
            e.printStackTrace();

        }
        return data;
    }


    private static boolean setFile(String filePath)
    {
        File f = new File(filePath);
        boolean fileExists = f.exists();
        boolean fileCanRead = f.canRead();

        if (fileExists && fileCanRead)
        {
            filepath = filePath;
            return true;
        }
        else
        {
            return false;
        }
    }
}

