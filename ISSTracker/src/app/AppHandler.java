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

    protected Location issloc;
    protected GeoInfo g;
    HttpClient client;
    Gson gson;
    
    Boolean consoleLog = false;
    
   

    public AppHandler() {
       client = HttpClient.newHttpClient();
       gson = new Gson();
       update();
    }

    
    public void setLog(Boolean enable) {
    	consoleLog = enable;
    }
    
    public Location getISSLoc() {
    	return issloc;
    }
    
    public GeoInfo getGeoInfo() {
    	return g;
    }
    
    //Update ISS info using online api response
    public void update() {
        
        try {
            HttpRequest get = HttpRequest.newBuilder()
            .uri(new URI("https://api.wheretheiss.at/v1/satellites/25544"))
            .GET()
            .build();
            HttpResponse<String> response = client.send(get,BodyHandlers.ofString());
            if(consoleLog) System.out.println(response.body());
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
            if(consoleLog) System.out.println(response.body());
            g = gson.fromJson(response.body(), GeoInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	



}
