package com.ruwan.csvtest.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CsvAnalyzerService {

    // @Value("${input.directory}")
    private String BASE_DIR = "F:\\AUK Development\\2025\\ruwan\\a\\"; // update if needed

    public List<Long> countTaGreaterThan20() throws Exception {
        List<Long> counts = new ArrayList<Long>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(BASE_DIR), "*.csv.gz")) {
            for (Path filePath : stream) {
                if (filePath.toString().endsWith(".sig.csv.gz")) {
                    System.out.println("Processing:sig " + filePath);

                } else {
                    System.out.println("Processing: " + filePath);
                    File csvFile = new File(filePath.toString());
                    List<String[]> rows = readGzipCsv(csvFile);
                    if (rows.isEmpty()) {
                        throw new Exception("CSV has no data.");
                    }

                    String[] header = rows.get(0);
                    int taIndex = -1;
                    for (int i = 0; i < header.length; i++) {
                        if (header[i].trim().equalsIgnoreCase("ta")) {
                            taIndex = i;
                            break;
                        }
                    }

                    if (taIndex == -1) {
                        throw new Exception("Column 'ta' not found.");
                    }

                    long count = 0;
                    for (int i = 1; i < rows.size(); i++) {
                        String taValueStr = rows.get(i)[taIndex];
                        try {
                            double taValue = Double.parseDouble(taValueStr);
                            if (taValue > 20) {
                                count++;
                            }
                        } catch (NumberFormatException ignored) {
                        }
                    }

                    counts.add(count);
                }
            }
        }
        return counts;
        // File csvFile = new File(csvPath);
        // File sigFile = new File(sigPath);

        // if (!csvFile.exists() || !sigFile.exists()) {
        // throw new FileNotFoundException("Required files not found: " + csvPath + " or
        // " + sigPath);
        // }

        // List<String[]> rows = readGzipCsv(csvFile);

        // if (rows.isEmpty()) {
        // throw new Exception("CSV has no data.");
        // }

        // String[] header = rows.get(0);
        // int taIndex = -1;
        // for (int i = 0; i < header.length; i++) {
        // if (header[i].trim().equalsIgnoreCase("ta")) {
        // taIndex = i;
        // break;
        // }
        // }

        // if (taIndex == -1) {
        // throw new Exception("Column 'ta' not found.");
        // }

        // long count = 0;
        // for (int i = 1; i < rows.size(); i++) {
        // String taValueStr = rows.get(i)[taIndex];
        // try {
        // double taValue = Double.parseDouble(taValueStr);
        // if (taValue > 20) {
        // count++;
        // }
        // } catch (NumberFormatException ignored) {
        // }
        // }

        // return count;
    }

    private List<String[]> readGzipCsv(File file) throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (GZIPInputStream gzipStream = new GZIPInputStream(new FileInputStream(file));
                BufferedReader reader = new BufferedReader(new InputStreamReader(gzipStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rows.add(line.split(","));
            }
        }
        return rows;
    }
}
