package com.platzerworld.e4.google.service.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.platzerworld.e4.google.service.domain.GeoPoint;

public class GoogleUtils {
	private String url = null;

	public static void authenticate() {
		
	}
	
	public static String getUrl(double fromLat, double fromLon, double toLat, double toLon) {// connect to map web service
	    StringBuffer urlString = new StringBuffer();    
	    urlString.append("http://maps.googleapis.com/maps/api/directions/json");
	    urlString.append("?origin=");// from
	    urlString.append(Double.toString(fromLat));
	    urlString.append(",");
	    urlString.append(Double.toString(fromLon));
	    urlString.append("&destination=");// to
	    urlString.append(Double.toString(toLat));
	    urlString.append(",");
	    urlString.append(Double.toString(toLon));
	    urlString.append("&sensor=false");
	    return urlString.toString();
	}
	
	private void connect(GeoPoint geoPoint){
		url = getUrl(geoPoint.getFromLatitude(), geoPoint.getFromLongitude(), geoPoint.getToLatitude(), geoPoint.getToLongitude());

	       URL aUrl;
			try {
				
				aUrl = new URL(url);			
				final URLConnection conn = aUrl.openConnection();
				conn.setReadTimeout(15 * 1000);  // timeout for reading the google maps data: 15 secs
				conn.connect();
				
				InputStream is = null;
 		       	is = aUrl.openStream();
 		       
 		       	BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
 		       	StringBuilder sb = new StringBuilder();
 		       	sb.append(reader.readLine() + "\n");
 		       	String line = "0";
 		       	while ((line = reader.readLine()) != null) {
 		       		sb.append(line + "\n");
 		       	}
 		       	is.close();
 		       	reader.close();
 		       	
 		       String result = sb.toString();
 		       Gson gson = new Gson();
 		       
		       //JSONObject jsonObject = new JSONObject(result);
		       //JSONArray routeArray = jsonObject.getJSONArray("routes");
		       //JSONObject routes = routeArray.getJSONObject(0);
		       //JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
		       //String encodedString = overviewPolylines.getString("points");
 		       //List<GeoPoint> pointToDraw = decodePoly(encodedString);
			} catch (Exception e) {
				
			}
	}
	
	private List<GeoPoint> decodePoly(String encoded) {

        List<GeoPoint> poly = new ArrayList<GeoPoint>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            GeoPoint p = new GeoPoint(
            		(int) (((double) lat / 1E5) * 1E6), 
            		(int) (((double) lng / 1E5) * 1E6));
            poly.add(p);
        }

        return poly;
    }
}
