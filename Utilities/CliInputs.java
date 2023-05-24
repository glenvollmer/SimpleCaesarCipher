package Utilities;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.lang.Character;

public class CliInputs
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