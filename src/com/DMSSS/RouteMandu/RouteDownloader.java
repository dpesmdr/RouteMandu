package com.DMSSS.RouteMandu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class RouteDownloader extends
		AsyncTask<String, Integer, ArrayList<LatLng>> {

	private ProgressDialog dialog;
	private Context context;
	private OurRoute ourRoute;

	public RouteDownloader(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		// Showing ProgressDialog when this AsyncTask executed
		dialog = ProgressDialog
				.show(context, "Loading Route", "Please Wait...");
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.show();
	}

	@Override
	protected ArrayList<LatLng> doInBackground(String... params) {
		String data = "";
		try {
			// Connecting to the web for getting JSON data
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(params[0]);
			HttpResponse response = client.execute(request);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			while (line != null) {
				line = reader.readLine();
				data += line;
			}
			ourRoute = new OurRoute(data);
			// Converting JSON to Java
			data = ourRoute.getPoints();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// return points by decrypting
		return decrypt(data);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		// dialog.setProgress(values[0]);
	}

	@Override
	protected void onPostExecute(ArrayList<LatLng> result) {

		// result = data return from doInBackground
		super.onPostExecute(result);
		PolylineOptions options = new PolylineOptions();
		options.addAll(result);

		// adding lines to make route inside map
		MainActivity.gMap.addPolyline(options);
		MainActivity.tv.setText("Total Distance: "
				+ ourRoute.getTotalDistance() + " m\nTotal Time: "
				+ getMinFromSec(ourRoute.getTotalTime()) + " min\n"
				+ ourRoute.getInstructions());

		// Dismiss progress dialog when successfully route drawn
		dialog.dismiss();
	}

	private Double getMinFromSec(double totalTime) {
		return (totalTime / 60);
	}

	private ArrayList<LatLng> decrypt(String encrypt) {

		// This method decrypt the encrypted data

		ArrayList<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encrypt.length();
		int lat = 0, lng = 0;
		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encrypt.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;
			shift = 0;
			result = 0;
			do {
				b = encrypt.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng position = new LatLng((double) lat / 1E5, (double) lng / 1E5);
			poly.add(position);
		}
		return poly;
	}
}
