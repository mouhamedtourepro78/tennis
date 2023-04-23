package com.tennis.app.service;

import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.text.ParseException;

public interface DataSavingService {
    void parseCsvToMatchs(String fileName) throws IOException, CsvException, ParseException;
}
