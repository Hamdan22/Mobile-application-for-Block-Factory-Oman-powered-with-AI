package com.example.binsapp;


import android.content.Context;
import android.content.Context;
import android.content.res.AssetManager;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
public class CsvReader {
    public static List<DataPoint> readCsvDataFromAssets(Context context, String filename) {
        List<DataPoint> data = new ArrayList<>();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            CSVReader csvReader = new CSVReader(inputStreamReader);
            List<String[]> csvData = csvReader.readAll();

            // Skip header row
            for (int i = 1; i < csvData.size(); i++) {
                String[] row = csvData.get(i);
                double width = Double.parseDouble(row[0]);
                double height = Double.parseDouble(row[1]);
                double totalBricksRequired = Double.parseDouble(row[2]);

                DataPoint dataPoint = new DataPoint(width, height, totalBricksRequired);
                data.add(dataPoint);
            }

            csvReader.close();
            inputStreamReader.close();
            inputStream.close();

        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

        return data;
    }
}