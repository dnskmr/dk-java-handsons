package com.dk.csv;

import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dinesh
 *
 */
public class CSVWriterClient {
    public static void main(String[] args) {
        String body="Dinesh\r\nkoki\r\n";
        writeCSV(body);
        write(body);
    }

    private static void writeCSV(String body) {
        List<String> strList = Arrays.asList(body.split("\r\n"));
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream("C:\\Temp\\test.csv"),StandardCharsets.UTF_8))) {
            for(String s: strList){
                String[] sArr = {s.replaceAll("\0","")};
                writer.writeNext(sArr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param body
     */
    private static void write(String body){
        try (PrintWriter writer = new PrintWriter("C:\\Temp\\test1.csv")) {
            writer.write(body);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


}
