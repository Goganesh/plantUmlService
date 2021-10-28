package com.goganesh.packages.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;

@Service
public class FileWriterService {

    public void writeFile(String from, String to) throws IOException {
        FileReader fileReader = new FileReader(ResourceUtils.getFile(from));
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        FileWriter fileWriter = new FileWriter(to);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            bufferedWriter.write(line + System.lineSeparator());
        }
        bufferedWriter.flush();

        fileReader.close();
        bufferedReader.close();
        fileWriter.close();
        bufferedWriter.close();
    }
}
