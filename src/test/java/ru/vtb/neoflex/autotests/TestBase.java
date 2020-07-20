package ru.vtb.neoflex.autotests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.neoflex.model.RequestPutPrice;
import ru.neoflex.model.RequestSaveTestimony;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TestBase {

    public static Iterator<Object[]> validRequestSave(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(path), StandardCharsets.UTF_8))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<RequestSaveTestimony> request = gson.fromJson(json, new TypeToken<List<RequestSaveTestimony>>() {
            }.getType());
            return request.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
        }
    }
    public static Iterator<Object[]> validRequestChange(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(path), StandardCharsets.UTF_8))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<RequestPutPrice> request = gson.fromJson(json, new TypeToken<List<RequestPutPrice>>() {
            }.getType());
            return request.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
        }
    }

}