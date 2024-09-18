package com.devfolio.banner_gen.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleAIService {
    private static final String API_KEY = "AIzaSyC-UL5nGmMqJay4w7Dxra5HVE1mu09NkOI";
    private static final String ENDPOINT = "https://aiplatform.googleapis.com/v1/projects/bb-hackathon-app/locations/YOUR_LOCATION/models/YOUR_MODEL_ID:predict";

    public static void main(String[] args) {
        try {
            // Construct URL with API key appended as a query parameter
            URL url = new URL(ENDPOINT + "?key=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Define your JSON request body here (e.g., input data for prediction)
            String jsonInputString = "{ \"instances\": [ { \"input\": \"your input data\" } ] }";

            // Write the JSON request to the output stream
            try (java.io.OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read the API response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Print the response
            System.out.println("Response: " + content.toString());

            // Disconnect the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
