package com.example.sberultramarket;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


class AppLogicHandler {
    public static JSONArray getProductsList()
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<JSONArray> data = executor.submit(() -> {
            try {
                URL url = new URL("https://simple-grocery-store-api.glitch.me/products");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                int responseCode = 0;
                responseCode = conn.getResponseCode();
                Log.d("STATUS_CODE", String.valueOf(responseCode));
                if (responseCode != 200) {
                    throw new RuntimeException(String.format("Error fetching. Status code: %d", responseCode));
                } else {
                    String inline = "";
                    Scanner scanner = new Scanner(url.openStream());
                    while (scanner.hasNext()) {
                        inline += scanner.nextLine();
                    }
                    scanner.close();
                    return (JSONArray) new JSONParser().parse(inline);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        try {
            return data.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}