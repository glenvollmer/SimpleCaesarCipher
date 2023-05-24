import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.lang.Character;

import Utilities.CliInputs;
import Utilities.FileHandler;

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