//import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.net.URLEncoder;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

//import javax.imageio.ImageIO;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.*;

import javafx.scene.image.Image;

public class WxModel
{
public String API_KEY = "b5af7904a7026b0fb5b554e38d8fde0a";


	private JsonElement json;
	private URL wuURL;

	public void getWx(String zipCode) {
		try {
			wuURL = new URL("https://api.openweathermap.org/data/2.5/weather?zip=" + zipCode + ",US&appid=" + API_KEY
					+ "&units=imperial");

			InputStream is = wuURL.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			json = new JsonParser().parse(br);
			is.close();
			br.close();
		} catch (MalformedURLException e) {
			
		} catch (IOException e) {
			
		}

	}

	public String getTemp() {
		String temp = "";
		temp = json.getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString();
		Double dbltemp = Double.parseDouble(temp); 
		temp = String.format("%.1f°F",dbltemp);

		return temp;

	}

	public String getHumidity() {
		String humidity ="Humidity: " + json.getAsJsonObject().get("main").getAsJsonObject().get("humidity").getAsString() + "%";


		return humidity;
	}

	public String getPressure() {
		Double dblPressure = Double.parseDouble(json.getAsJsonObject().get("main").getAsJsonObject().get("pressure").getAsString());
		dblPressure = dblPressure * 0.0295;
		String pressure = String.format("%.2f", dblPressure);
		return pressure;
	}
	public String getWindSpeed () {
		String strSpeed = "";
		Double spd = Double.parseDouble(json.getAsJsonObject().get("wind").getAsJsonObject().get("speed").getAsString()); 
		strSpeed = String.format("%.1fMPH",spd);

		return strSpeed;

	}

	public String getWindDIrection() {
		Integer degrees = Integer.parseInt(json.getAsJsonObject().get("wind").getAsJsonObject().get("deg").getAsString());
		String direction = "";

		if (degrees >= 337.5 || degrees < 22.5) {
			direction = "N";
		}
		if (degrees >= 22.5 && degrees < 67.5 ) {
			direction =  "NE";
		}
		if (degrees >= 67.5 && degrees < 112.5) {
			direction =  "E";
		}
		if (degrees >= 112.5 && degrees < 157.5 ) {
			direction =  "SE";
		}
		if (degrees >= 157.5 && degrees < 202.5) {
			direction =  "S";
		}
		if (degrees >= 202.5 && degrees < 247.5) {
			direction =  "SW";
		}
		if (degrees >= 247.5 && degrees < 292.5) {
			direction =  "W";
		}
		if (degrees >= 292.5 && degrees < 337.5) {
			direction =  "NW";
		}
		return direction;


	}
	public String getWeatherConditions() {
		return json.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();
	}

	public String getTime() {
		final DateTimeFormatter formatter = 
		DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		ZoneId zone = ZoneId.of(TimeZone.getDefault().getID());

		final String formattedDateTime = Instant.ofEpochSecond(json.getAsJsonObject().get("dt").getAsLong())
		.atZone(zone).format(formatter);

		return formattedDateTime;
	}


	public String getCityName() {
		
		String city = json.getAsJsonObject().get("name").getAsString();
		
		return city;
	}


	public Long getinfoLong(String key) {
		Long info = json.getAsJsonObject().get(key).getAsLong();

		return info;

	}


	public String getinfoSring(String key) {
		String info = json.getAsJsonObject().get(key).getAsString();

		return info;

	}
	public String getinfoArray(String key) {
		String info = json.getAsJsonObject().get(key).getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();
		return info;
	}

	public String getIconID() {

		String info = json.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
		return info;
	}

	public Image getImage() {
		String IconID = json.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
		String urlOfImage = "https://openweathermap.org/img/w/" + IconID + ".png";
		return new Image(urlOfImage);

	}

	public Image errorImage() {
		return new Image("badzipcode.png");
	}



	public JsonElement getJSONobjFromJSONobj(String key) {
		JsonElement newObj = null;

		newObj = json.getAsJsonObject().get(key);

		return newObj;
	}


		public boolean URLisValid() throws IOException {
			HttpURLConnection connection = (HttpURLConnection) wuURL.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			int code = connection.getResponseCode();
			if (code == 404) {
				return false;
			} else {
			return true;
			}
		}






		public String unixTimetoLocalTime(long unixSeconds) {
			final DateTimeFormatter formatter = 
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			ZoneId zone = ZoneId.of(TimeZone.getDefault().getID());
			// final String formattedDateTime = Instant.ofEpochSecond(unixSeconds)
			// .atZone(ZoneId.of(TimeZone.getDefault().getID()).format(formatter));

			final String formattedDateTime = Instant.ofEpochSecond(unixSeconds)
			.atZone(zone).format(formatter);

			return formattedDateTime;


		}


}

