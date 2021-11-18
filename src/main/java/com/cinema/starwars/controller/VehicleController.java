package com.cinema.starwars.controller;

import com.cinema.starwars.model.Vehicle;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;


@RestController
@RequestMapping(path = "cinema/starwars/vehicles/")
public class VehicleController {

    private static String uri = "https://swapi.dev/api/vehicles/";

    @GetMapping(path = "getAll")
    public static ResponseEntity<ArrayList<Vehicle>> getAllVehicles() throws IOException, ParseException {

        ArrayList<Vehicle> vehiclesList;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            boolean nextPage;

            JSONParser parser = new JSONParser();
            ArrayList<JSONObject> resultsList = new ArrayList<>();

            do {
                nextPage = false;
                HttpGet request = new HttpGet(uri); // montando a request
                CloseableHttpResponse response = httpClient.execute(request); // onde faz a request

                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");

                JSONObject json = (JSONObject) parser.parse(responseString);
                resultsList.addAll((ArrayList<JSONObject>) json.get("results"));

            if ((json.get("next") != null)) {
                uri = json.get("next").toString();
                nextPage = true;

                }

                response.close();

            } while (Boolean.TRUE.equals(nextPage));

            vehiclesList = new ArrayList<>();

            for (JSONObject item : resultsList) {

                String urlWithId = (String) item.get("url");

                String[] parts = urlWithId.split("/");
                int id = Integer.parseInt(parts[parts.length - 1]);

                System.out.println(id);

                Vehicle vehicle = new Vehicle();

                vehicle.setId(id);
                vehicle.setName(item.get("name").toString());
                vehicle.setModel(item.get("model").toString());

                vehiclesList.add(vehicle);
            }

        }

        return new ResponseEntity<>(vehiclesList,HttpStatus.OK);

    }

    @GetMapping(path = "getOne/{id}")
    public ResponseEntity<Vehicle> getOneVehicle(@PathVariable int id) throws IOException, ParseException {

        JSONObject json;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            JSONParser parser = new JSONParser();
            HttpGet request = new HttpGet(uri + id);

            CloseableHttpResponse response = httpClient.execute(request); // onde dá o play

            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");

            json = (JSONObject) parser.parse(responseString);
            Vehicle vehicle = new Vehicle();

            vehicle.setId(id);
            vehicle.setName(json.get("name").toString());
            vehicle.setModel(json.get("model").toString());

            response.close();

            return new ResponseEntity<>(vehicle,HttpStatus.OK);
        }
    }

}


