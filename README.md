# ISSTracker
  This is a Java Swing application that graphically displays real-time data regarding the ISS and it's position above the earth using this [API](https://wheretheiss.at/w/developer). 
  
  This is a work in progress and I hope to add some more related features to extend the functionality and usefulness of the application.
  

# Installation
### Windows
  1. After Downloading the zip file and extracting the contents, open command prompt/powershell

  2. Navigate to where the ISSTracker folder was installed and use following commands to create a runnable jar and to run it using java (Requires Maven and Java)

  ```
  mvn compile
  
  mvn package
  
  java -jar target\ISSTracker-0.01-SNAPSHOT-jar-with-dependencies.jar
  ```

# How To Use

  Currently there is only a single functional page. After running the application you will see two buttons on the bottom, the left button takes you to a page which will display the realtime data for the ISS. 
  
  This page also includes a google maps link and an update button, which will update the information with the latest data provided by the api. This data includes the longitude, latitude, altitude, velocity, timezone, and country code directly from the API. It also includes the orbital period and quadrant which is calculated inside of the app using some of these values.
  
