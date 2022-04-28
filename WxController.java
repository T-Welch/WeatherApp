import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

// import com.google.gson.JsonElement;

// import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
// import javafx.scene.image.Image;
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
          if (weather.URLisValid())
          {
            btnGetWeather.setText("Get Weather");
            lblCityName.setText( "City: "+ weather.getCityName());
            lblTime.setText("Date and Time: " + weather.getTime());
            lblWeather.setText("Conditions: " + weather.getWeatherConditions());
            lblTemperature.setText("Temperature: " + weather.getTemp());
            lblWindSpeed.setText("Wind Speed: " + weather.getWindSpeed());
            lblPressure.setText("Pressure: " + weather.getPressure() + "inHg");
            lblWindDirection.setText("Wind Direction: " + weather.getWindDIrection());
            lblHumdity.setText(weather.getHumidity());
            weather.getTemp();
            imgIcon.setImage(weather.getImage());
            

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
            imgIcon.setImage(weather.errorImage());
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