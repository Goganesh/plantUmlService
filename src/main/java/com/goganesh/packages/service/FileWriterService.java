package com.goganesh.packages.service;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public interface FileWriterService {

    void writeFile(String from, String to) throws IOException;
}
