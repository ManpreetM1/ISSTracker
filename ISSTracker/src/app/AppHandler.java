package app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.google.gson.Gson;

import data.GeoInfo;
import data.Location;



public class AppHandler {

    public Location issloc;
    public GeoInfo g;

    public AppHandler() {
       update();
    }

    //Update ISS info using online api response
    public void update() {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        try {
            HttpRequest get = HttpRequest.newBuilder()
            .uri(new URI("https://api.wheretheiss.at/v1/satellites/25544"))
            .GET()
            .build();
            HttpResponse<String> response = client.send(get,BodyHandlers.ofString());
            System.out.println(response.body());
            issloc = gson.fromJson(response.body(), Location.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            HttpRequest get = HttpRequest.newBuilder()
            .uri(new URI("https://api.wheretheiss.at/v1/coordinates/" + this.issloc.getLat() + "," + this.issloc.getLon()))
            .GET()
            .build();
            HttpResponse<String> response = client.send(get,BodyHandlers.ofString());
            System.out.println(response.body());
            g = gson.fromJson(response.body(), GeoInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	



}
