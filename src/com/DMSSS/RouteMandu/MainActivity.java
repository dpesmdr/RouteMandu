package com.DMSSS.RouteMandu;

import android.content.ContentResolver;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements
		OnMapClickListener {

	static TextView tv;
	static GoogleMap gMap;
	Double srcLat1, srcLong1, dstLat1, dstLong1;

	int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv = (TextView) findViewById(R.id.details);

		setUpMapIfNeeded();

		setMyLocation();

		// markMyLocation();

		gMap.setOnMapClickListener(this);

	}

	public String getPath(Double srcLat, Double srcLng, Double dstLat,
			Double dstLng) {

		// actual path is like
		// "http://maps.googleapis.com/maps/api/directions/json?origin=27.62908333333333,85.52714666666667&destination=27.653627145138,85.51046624779701&sensor=fasle&units=metric&mode=walking"
		String path = "http://maps.googleapis.com/maps/api/directions/json?origin="
				+ srcLat
				+ ","
				+ srcLng
				+ "&destination="
				+ dstLat
				+ ","
				+ dstLng + "&sensor=false&units=metric&mode=walking";
		return path;
	}

	private void showRoute(Double srcLat, Double srcLng, Double dstLat,
			Double dstLng) {
		new RouteDownloader(this).execute(getPath(srcLat, srcLng, dstLat,
				dstLng));
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (gMap == null) {
			// Try to obtain the map from the MapFragment.
			gMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
		}
	}

	private void setMyLocation() {
		gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				27.7000, 85.3333), 16));
		// gMap.addMarker(new MarkerOptions().position(latlng).title(
		// "You are Here"));
		// showMarkerFromDB();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onMapClick(LatLng point) {

		gMap.addMarker(new MarkerOptions().position(point).title("Marked"));

		++index;
		if (index == 1) {
			srcLat1 = point.latitude;
			srcLong1 = point.longitude;
		}
		if (index == 2) {
			dstLat1 = point.latitude;
			dstLong1 = point.longitude;
			showRoute(srcLat1, srcLong1, dstLat1, dstLong1);
			index = 0;
		}
	}
}
