package org.example.service;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CSVManager {
    public static List<Animal> readFromCSVRequest(InputStream file) throws IOException {

        try (InputStreamReader reader = new InputStreamReader(file)) {
            List<Animal> animals = new CsvToBeanBuilder(reader)
                    .withType(Animal.class)
                    .build()
                    .parse();

            return animals;

        }
    }

}
