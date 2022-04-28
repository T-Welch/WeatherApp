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
        weather.getWx(zipcode);
    
        // Use the model to get the weather information
        try {
          if (weather.URLisValid(weather.urlForLatLongFromZip(zipcode)))
          {
            btnGetWeather.setText("Get Weather");
            // JsonElement jse = weather.getJSONwithAllInfo(weather.urlwithLatLon(weather.getLat(weather.getJSONObjWithLatLong(weather.urlForLatLongFromZip(zipcode))),
            // weather.getLon(weather.getJSONObjWithLatLong(weather.urlForLatLongFromZip(zipcode)))));
            // weather.getWx(zipcode);

            // System.out.println(weather.getinfoSring("name"));
            // lblCityName.setText( "City: "+ weather.getinfoSring("name"));
            // lblTime.setText(weather.unixTimetoLocalTime(weather.getinfoLong("dt")));
            // lblWeather.setText("Conditions: " + weather.getinfoArray("weather"));
            lblTemperature.setText("Temperature: " + weather.getTemp());
            //lblWindSpeed.setText("Wind Speed: " + weather.windSpeed(weather.getinfoSring(weather.getJSONobjFromJSONobj("wind"), "speed")));
            // pressure is in hectapascals and we need it in inches of mercury. 1 Hectopascals = 0.0295 Inches of mercury. Need to do 
            // conversion in a function also need to limit digits after decimal points to 2 
            //lblPressure.setText("Pressure: " + weather.pressureInInhg(weather.getinfoSring( weather.getJSONobjFromJSONobj("main"), "pressure")) + "inHg");
            //lblWindDirection.setText("Wind Direction: " + weather.windDegreeAsDirection(weather.getinfoSring(weather.getJSONobjFromJSONobj("wind"), "deg")));
            //lblHumdity.setText("Humidity: " + String.valueOf(weather.getinfoSring( weather.getJSONobjFromJSONobj("main"), "humidity")) + "%");
            // lblVisibility.setText(weather.getVisibility());
            weather.getTemp();
            // System.out.println(weather.getIconID());
            // Image image = SwingFXUtils.toFXImage(weather.getImageFromIconID(weather.getIconID()), null);
            // imgIcon.setImage(image);
            

          }
          else
          {
            lblCityName.setText("Invalid Zipcode");
            lblTime.setText("Invalid Zipcode");
            lblWeather.setText("Invalid Zipcode");
            lblTemperature.setText("Invalid Zipcode");
            lblWindSpeed.setText("Invalid Zipcode");
            lblPressure.setText("Invalid Zipcode");          
            lblHumdity.setText("Invalid Zipcode");
            lblWindDirection.setText("Invalid Zipcode");
            btnGetWeather.setText("Try again");
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