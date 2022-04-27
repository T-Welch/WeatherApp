import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.JsonElement;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WxController implements Initializable {
    @FXML
    private TextField txtZipCode;

    @FXML 
    private Button btnGetWeather;

    @FXML 
    private Label lblCityName;

    @FXML 
    private Label lblTime;

    @FXML 
    private Label lblWeather;

    @FXML 
    private Label lblTemperature;

    @FXML 
    private Label lblWindSpeed;

    @FXML 
    private Label lblWindDirection;

    @FXML 
    private Label lblPressure;

    @FXML 
    private Label lblHumdity;

    @FXML 
    private ImageView imgIcon;

    @FXML
    private void handleButtonAction(ActionEvent e) {
        // Create object to access the Model
        WxModel weather = new WxModel();
    
        // Get zipcode
        String zipcode = txtZipCode.getText();
    
        // Use the model to get the weather information
        try {
          if (weather.URLisValid(weather.urlForLatLongFromZip(zipcode)))
          {
            JsonElement jse = weather.getJSONwithAllInfo(weather.urlwithLatLon(weather.getLat(weather.getJSONObjWithLatLong(weather.urlForLatLongFromZip(zipcode))),
            weather.getLon(weather.getJSONObjWithLatLong(weather.urlForLatLongFromZip(zipcode)))));

            System.out.println(weather.getinfoSring(jse, "name"));
            lblCityName.setText( "City: "+ weather.getinfoSring(jse, "name"));
            lblTime.setText(weather.unixTimetoLocalTime(weather.getinfoLong(jse, "dt")));
            lblWeather.setText("Conditions: " + weather.getinfoArray(jse, "weather"));
            lblTemperature.setText("Temp: " + String.valueOf(weather.getinfoSring( weather.getJSONobjFromJSONobj(jse, "main"), "temp")) + "°F");
            lblWindSpeed.setText("Wind Speed: " + weather.getinfoSring(weather.getJSONobjFromJSONobj(jse, "wind"), "speed") + " MPH");
            // pressure is in hectapascals and we need it in inches of mercury. 1 Hectopascals = 0.0295 Inches of mercury. Need to do 
            // conversion in a function also need to limit digits after decimal points to 2 
            lblPressure.setText("Pressure: " + weather.pressureInInhg(weather.getinfoSring( weather.getJSONobjFromJSONobj(jse, "main"), "pressure")) + "inHg");
            lblWindDirection.setText("Wind Direction: " + weather.windDegreeAsDirection(weather.getinfoSring(weather.getJSONobjFromJSONobj(jse, "wind"), "deg")));
            lblHumdity.setText("Humidity: " + String.valueOf(weather.getinfoSring( weather.getJSONobjFromJSONobj(jse, "main"), "humidity")) + "%");
            // lblVisibility.setText(weather.getVisibility());
            System.out.println(weather.getIconID(jse));
            Image image = SwingFXUtils.toFXImage(weather.getImageFromIconID(weather.getIconID(jse)), null);
            imgIcon.setImage(image);
            

          }
          else
          {
            lblCityName.setText("Invalid Zipcode");
            lblTime.setText("");
            lblWeather.setText("");
            lblTemperature.setText("");
            // lblWind.setText("");
            // lblPressure.setText("");
            // lblVisibility.setText("");
            imgIcon.setImage(new Image("badzipcode.png"));
          }
        } catch (MalformedURLException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    
      @Override
      public void initialize(URL url, ResourceBundle rb) {
        // TODO
      }    
    
    }