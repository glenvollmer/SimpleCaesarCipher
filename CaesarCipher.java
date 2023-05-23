import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.lang.Character;


class CliInputs
{
    public static int key;
    public static String action;
    public static String text;
    public static String filePath;


    public CliInputs(String[] args) 
    {
        String action = args[0];
        boolean setActionSuccess = setAction(action);
        handleError(setActionSuccess, "Error with action");

        int key = Integer.parseInt(args[1]);
        boolean setKeySuccess = setKey(key);
        handleError(setKeySuccess, "Error with encryption key");

        if (action.equals("encrypt")
        || action.equals("decrypt"))
        {
            String text = args[2];
            boolean setTextSuccess = setText(text);
            handleError(setTextSuccess, "Error with text");

        }
        else if (action.equals("encryptFile")
        || action.equals("decryptFile"))
        {
            String filePath = args[2];
            boolean setFilePathSuccess = setFilePath(filePath);
            handleError(setFilePathSuccess, "Error with file path");
        }
    }


    private void handleError(boolean status, String msg)
    {
        // figure out how to properly do this
        if (!status)
        {
            // throw new Exception(msg);
        }
    }


    private static boolean setKey(int k) 
    {
        try 
        {
            int keyValue;
            if (k > 26)
            {
                keyValue = k - 26;
            }
            else
            {
                keyValue = k;
            }
            key = k;        
        }
        catch(Exception e) 
        {
            return false;
        }

        return true;
    }


    private static boolean setAction(String a) 
    {
        // check to see if the action is valid
        try 
        {
            action = a;
        }
        catch(Exception e) 
        {
            return false;
        }

        return true;
    }


    private static boolean setText(String t)
    {
        try 
        {
            text = t;
        }
        catch(Exception e) 
        {
            return false;
        }

        return true;
    }


    private static boolean setFilePath(String p)
    {
        // check to see if file exists/valid file path
         try 
        {
            filePath = p;
        }
        catch(Exception e) 
        {
            return false;
        }

        return true;
    }
}

class FileHandler
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



public class CaesarCipher 
{
    private static FileHandler fileHandler;


    private static char[] getAlphabet()
    {
        return "abcdefghijklmnopqrstuvwxyz".toCharArray();
    }

    private static char[] getCipherAlphabet(int key)
    {
        int alphabetLength = 26;
        int firstHalfLength = key;
        int secondHalfLength = alphabetLength - key;
        
        char[] plainAlphabet = getAlphabet();
        char[] ciphertextAlphabet = new char[alphabetLength];
        char[] firstHalf = new char[firstHalfLength];
        char[] secondHalf = new char[secondHalfLength];
        
        System.arraycopy(
            plainAlphabet, 
            0, 
            firstHalf, 
            0, 
            firstHalfLength);

        System.arraycopy(
            plainAlphabet, 
            key, 
            secondHalf, 
            0, 
            secondHalfLength);

        System.arraycopy(
            secondHalf, 
            0, 
            ciphertextAlphabet, 
            0, 
            secondHalfLength);

        System.arraycopy(
            firstHalf, 
            0, 
            ciphertextAlphabet, 
            secondHalfLength, 
            firstHalfLength);
 
        return ciphertextAlphabet;
    }


    private static String encrypt(int key, String text)
    {
        String cipher;
        String lowerCaseText = text.toLowerCase();        
        char[] plainAlphabet = getAlphabet();
        char[] ciphertextAlphabet = getCipherAlphabet(key);
        
        StringBuilder sb = new StringBuilder();

        for (char ch : lowerCaseText.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                sb.append(ch);
                continue;
            }

            int i = new String(plainAlphabet).indexOf(ch);
            char c = ciphertextAlphabet[i];
            sb.append(c);
        }

        cipher = sb.toString();

        return cipher;
    }


    private static String decrypt(int key, String text)
    {
        String message;
        char[] plainAlphabet = getAlphabet();
        char[] ciphertextAlphabet = getCipherAlphabet(key);

        StringBuilder sb = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                sb.append(ch);
                continue;
            }
            
            int i = new String(ciphertextAlphabet).indexOf(ch);

            char c = plainAlphabet[i];
            sb.append(c);
        }

        message = sb.toString();

        return message;
    }


    private static String encryptFile(int key)
    {
        String data = fileHandler.readInputFile();
        String encryptedData = encrypt(key, data);
        String outputFilePath = fileHandler.writeOutputFile(encryptedData);

        return outputFilePath;
    }


    private static String decryptFile(int key)
    {
        String data = fileHandler.readInputFile();
        String decryptedData = decrypt(key, data);
        String outputFilePath = fileHandler.writeOutputFile(decryptedData);

        return outputFilePath;
    }


    private static String handleCliInput(CliInputs cliInputs)
    {
        String result = "";
        
        int key = cliInputs.key;
        String text = cliInputs.text;
        String action = cliInputs.action;
        String filePath = cliInputs.filePath;


        // handle other characters besides just standard lowercase abc's
        switch(action) 
        {
            case "encrypt":
                result = encrypt(key, text);
                break;
            
            case "decrypt":
                result = decrypt(key, text);
                break;
            
            case "encryptFile":
                fileHandler = new FileHandler(filePath);
                result = encryptFile(key);
                break;
            
            case "decryptFile":
                fileHandler = new FileHandler(filePath);
                result = decryptFile(key);
                break;                                
            
            default:
                break;
        }

        return result;
    }


    public static void main(String[] args)
    {
        CliInputs cliInputs = new CliInputs(args);
        
        String result = handleCliInput(cliInputs);
        System.out.println(result);

    }
}