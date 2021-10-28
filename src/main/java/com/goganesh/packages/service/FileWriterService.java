package com.goganesh.packages.service;

import java.io.*;

public interface FileWriterService {

    void writeFile(String from, String to) throws IOException;
}
