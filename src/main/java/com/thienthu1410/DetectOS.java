package com.thubui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class DetectOS {
    

    public String detectOS() {
        String os = "";
          try {
            // get python script
            InputStream inputStream = DetectOS.class.getClassLoader().getResourceAsStream("python/GetOS.py");
            if (inputStream == null) {
                throw new FileNotFoundException("Python script not found in resources!");
            }

            // Create temp file to store script Python
            File tempScript = File.createTempFile("GetOS", ".py");
            tempScript.deleteOnExit(); // Delete the temp file when exit

            // Write the python script to temp file
            try (OutputStream outputStream = new FileOutputStream(tempScript)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // Call python script from temp file
            String pythonCmd = "python";
            ProcessBuilder processBuilder = new ProcessBuilder(pythonCmd, tempScript.getAbsolutePath());
            processBuilder.redirectErrorStream(true);

            // Run python script and get ouput
            Process process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                os = reader.readLine();                                                    
            }

            // Wait for python script finish
            int exitCode = process.waitFor();
            System.out.println("Python script exited with code: " + exitCode);            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return os; // return the real OS if no Exception
    }


}
