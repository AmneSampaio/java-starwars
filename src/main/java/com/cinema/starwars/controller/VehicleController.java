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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping(path = "cinema/starwars/vehicles/")
public class VehicleController {

    private static String uri = "https://swapi.dev/api/vehicles";

    @GetMapping(path = "getAll")
    public ResponseEntity getAllVehicles() throws IOException, InterruptedException, ParseException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        Boolean nextPage;

        JSONParser parser = new JSONParser();
        ArrayList<JSONObject> resultsList = new ArrayList<JSONObject>();

        do {
            nextPage = false;
            HttpGet request = new HttpGet(uri); // montando a request
            CloseableHttpResponse response = httpClient.execute(request); // onde dá o play

            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");

            JSONObject json = (JSONObject) parser.parse(responseString);
            resultsList.addAll((ArrayList<JSONObject>) json.get("results"));

            if (!(json.get("next") == null)) {
                uri = json.get("next").toString();
                nextPage = true;

            }

            response.close();

        } while (nextPage);

        ArrayList<Vehicle> vehiclesList = new ArrayList<Vehicle>();

        for (JSONObject item : resultsList ) {

            String urlWithId = (String) item.get("url");

            String[] parts = urlWithId.split("/");
            int id = Integer.parseInt(parts[parts.length-1]);

            System.out.println(id);

            Vehicle vehicle = new Vehicle();

            vehicle.setId(id);
            vehicle.setName(item.get("name").toString());
            vehicle.setModel(item.get("model").toString());

            vehiclesList.add(vehicle);
        }

        httpClient.close();

        return new ResponseEntity(vehiclesList,HttpStatus.OK);

    }

    @GetMapping(path = "getOne/{id}")
    public ResponseEntity getOneVehicle() throws IOException, InterruptedException, ParseException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        Boolean nextPage;

        JSONParser parser = new JSONParser();
        ArrayList<JSONObject> resultsList = new ArrayList<JSONObject>();

        do {
            nextPage = false;
            HttpGet request = new HttpGet(uri); // montando a request
            CloseableHttpResponse response = httpClient.execute(request); // onde dá o play

            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");

            JSONObject json = (JSONObject) parser.parse(responseString);
            resultsList.addAll((ArrayList<JSONObject>) json.get("results"));

            if (!(json.get("next") == null)) {
                uri = json.get("next").toString();
                nextPage = true;

            }

            response.close();

        } while (nextPage);

        ArrayList<Vehicle> vehiclesList = new ArrayList<Vehicle>();

        for (JSONObject item : resultsList ) {

            String urlWithId = (String) item.get("url");

            String[] parts = urlWithId.split("/");
            int id = Integer.parseInt(parts[parts.length-1]);

            System.out.println(id);

            Vehicle vehicle = new Vehicle();

            vehicle.setId(id);
            vehicle.setName(item.get("name").toString());
            vehicle.setModel(item.get("model").toString());

            vehiclesList.add(vehicle);
        }

        httpClient.close();

        return new ResponseEntity(vehiclesList,HttpStatus.OK);

    }



}


