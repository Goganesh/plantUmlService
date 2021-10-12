package service;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class CsvReaderService {

    public <T> List readCsv(String path, char delimiter, Class<T> clazz) throws FileNotFoundException {
        return new CsvToBeanBuilder(new FileReader(path))
                .withType(clazz)
                .withSeparator(delimiter)
                .build()
                .parse();
    }
}
