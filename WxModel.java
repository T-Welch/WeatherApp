import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import javax.imageio.ImageIO;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.*;

import javafx.scene.image.Image;

public class WxModel
{
public String API_KEY = "b5af7904a7026b0fb5b554e38d8fde0a";


	private JsonElement json;

	public void getWx(String zipCode) {
		URL wuURL;
		try {
			wuURL = new URL("https://api.openweathermap.org/data/2.5/weather?zip=" + zipCode + ",US&appid=" + API_KEY
					+ "&units=imperial");
			System.out.println(wuURL);

			InputStream is = wuURL.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			json = new JsonParser().parse(br);
			is.close();
			br.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getTemp() {
		String temp = "";
		temp = json.getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString();
		Double dbltemp = Double.parseDouble(temp); 
		temp = String.format("%.1f°F",dbltemp);

		return temp;

	}

	public URL urlForLatLongFromZip(String zipcode) throws MalformedURLException {

		URL formedURL = new URL("http://api.openweathermap.org/geo/1.0/zip?zip=" + zipcode + ",US&appid=" + API_KEY + "&units=imperial");
		
		
		System.out.println(formedURL);
		return formedURL;
	}




	public JsonElement getJSONObjWithLatLong(URL urlWithZipCode) throws IOException {
		InputStream inputStream = urlWithZipCode.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

		JsonElement jse = new JsonParser().parse(br);
		return jse;
	} 

	public String getLat() {

		String lat = json.getAsJsonObject().get("lat").getAsString();
		return lat;


	}

	public String getLon() {

		String lon = json.getAsJsonObject().get("lon").getAsString();
		return lon;


	}

	public URL urlwithLatLon(String lat, String lon) throws MalformedURLException {
		URL formedURL = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY + "&units=imperial");
		System.out.println(formedURL);
		return formedURL;
	}
	
	public JsonElement getJSONwithAllInfo(URL urlWithLatLong) {
		InputStream inputStream;
		JsonElement jse = null;
		try {
			inputStream = urlWithLatLong.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

			jse = new JsonParser().parse(br);
			
		} catch (IOException e) {
			System.out.println("This is really broken if this breaks...");
			e.printStackTrace();

		}
		return jse;


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

	public BufferedImage getImageFromIconID(String IconID) throws IOException {
		BufferedImage bufferedImage = null;
		URL url = null;
		try {
			url = new URL("https://openweathermap.org/img/w/" + IconID + ".png");
			InputStream in = url.openStream();
			bufferedImage = ImageIO.read(in);
			System.out.println(url);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		
return bufferedImage;
	}



	public JsonElement getJSONobjFromJSONobj(String key) {
		JsonElement newObj = null;

		newObj = json.getAsJsonObject().get(key);

		return newObj;
	}


		public boolean URLisValid(URL urlWithZip) throws IOException {
			HttpURLConnection connection = (HttpURLConnection) urlWithZip.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			int code = connection.getResponseCode();
			//System.out.println(code);
			if (code == 404) {
				return false;
			} else {
			return true;
			}
		}

		public String pressureInInhg (String hectopascals) {
			String strPressure = "";
			double pressure = Double.parseDouble(hectopascals); 
			pressure = pressure * 0.0295;
			strPressure = String.format("%.2f",pressure);

			return strPressure;

		}

		public String windSpeed (String speed) {
			String strSpeed = "";
			Double spd = Double.parseDouble(speed); 
			strSpeed = String.format("%.1fMPH",spd);

			return strSpeed;

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

		public String windDegreeAsDirection(String StrDegrees) {
			Integer degrees = Integer.parseInt(StrDegrees);
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

		// public String getLocation() {

		// 	return getinfoSring(jse, "name");

		// }

		

	// public static void main(String[] args) throws IOException
	// {
	// 	WxModel b = new WxModel();
	// 	try {
	// 		if (args.length == 0 || !b.URLisValid(b.urlForLatLongFromZip(args[0]))) {
	// 			System.out.println("Please run the program again and enter a valid US zip code");
	// 			return;
	// 		}
	// 	} catch (MalformedURLException e1) {
	// 		e1.printStackTrace();
	// 	} catch (IOException e1) {

	// 		e1.printStackTrace();
	// 	}

	// 	JsonElement jse = b.getJSONwithAllInfo(b.urlwithLatLon(b.getLat(b.getJSONObjWithLatLong(b.urlForLatLongFromZip(args[0]))),
	// 	b.getLon(b.getJSONObjWithLatLong(b.urlForLatLongFromZip(args[0])))));

	// 	System.out.println("Location:       " + b.getinfoSring(jse, "name"));
	// 	System.out.println("Time:           " + b.unixTimetoLocalTime(b.getinfoLong(jse, "dt")));
	// 	System.out.println("Weather:        " + b.getinfoArray(jse, "weather"));
	// 	System.out.println("Temperature F:  " + b.getinfoSring( b.getJSONobjFromJSONobj(jse, "main"), "temp"));
	// 	System.out.println("Wind:           " + b.getinfoSring(b.getJSONobjFromJSONobj(jse, "wind"), "speed") + " MPH");
	// 	System.out.println("Wind direction: " + b.getinfoSring(b.getJSONobjFromJSONobj(jse, "wind"), "deg") + " degrees");
	// 	System.out.println("Pressure inHG:  " + b.getinfoSring( b.getJSONobjFromJSONobj(jse, "main"), "pressure"));
	// }
}

