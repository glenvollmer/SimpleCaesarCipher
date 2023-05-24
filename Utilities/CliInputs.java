package Utilities;

public class CliInputs
{
    public int key;
    public String action;
    public String text;
    public String filePath;


    public CliInputs(String[] args) 
    {
        String action = args[0];
        boolean setActionSuccess = setAction(action);
        handleError(setActionSuccess, "Error with action");

        boolean encryptOrDecrypt = action.equals("encrypt") || action.equals("decrypt");
        boolean encryptFileOrDecryptFile = action.equals("encryptFile") || action.equals("decryptFile");


        if (encryptOrDecrypt || encryptFileOrDecryptFile)
        {
            int key = Integer.parseInt(args[1]);
            boolean setKeySuccess = setKey(key);
            handleError(setKeySuccess, "Error with encryption key");
        }

        if (encryptOrDecrypt)
        {
            String text = args[2];
            boolean setTextSuccess = setText(text);
            handleError(setTextSuccess, "Error with text");

        }
        else if (encryptFileOrDecryptFile)
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


    private boolean setKey(int k) 
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
            key = keyValue;        
        }
        catch(Exception e) 
        {
            return false;
        }

        return true;
    }


    private boolean setAction(String a) 
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


    private boolean setText(String t)
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


    private boolean setFilePath(String p)
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