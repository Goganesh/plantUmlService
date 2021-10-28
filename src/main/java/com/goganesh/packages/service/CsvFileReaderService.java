package com.goganesh.packages.service;

import java.io.*;
import java.util.List;


public interface CsvFileReaderService {
    <T> List readCsv(String path, char delimiter, Class<T> clazz) throws FileNotFoundException;
}
