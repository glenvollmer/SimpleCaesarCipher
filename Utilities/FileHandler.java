package Utilities;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.lang.Character;

public class FileHandler
{
    private static String filepath;

    public FileHandler(String f)
    {
        boolean fileExists = setFile(f);
        if (!fileExists)
        {
            // handle error here
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
            boolean fileCreated = fileObj.createNewFile();

            if (!fileCreated) 
            {
                System.out.println("error creating output file");
            }
        } 
        catch (IOException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return outputFileName;
    }


    public static String writeOutputFile(String data)
    {
        String outputFile = createOutputFile();

        try 
        {
            FileWriter file = new FileWriter(outputFile);
            file.write(data);
            file.close();

        } 
        catch (IOException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return "file path goes here";
    }


    public static String readInputFile()
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
            System.out.println("An error occurred.");
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

