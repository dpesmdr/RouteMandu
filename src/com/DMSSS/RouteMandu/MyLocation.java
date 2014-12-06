package com.DMSSS.RouteMandu;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class MyLocation implements LocationListener {

	private LocationManager manager;
	private String provider;
	private Location location, myLocation;

	public MyLocation(Context context) {
		manager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		provider = manager.getBestProvider(new Criteria(), false);
		location = manager.getLastKnownLocation(provider);
		if (location != null)
			onLocationChanged(location);
		else {
			Toast.makeText(context,
					"Sorry something wrong with your GPS provider",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		setMyLocation(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	public Location getMyLocation() {
		return myLocation;
	}

	public void setMyLocation(Location myLocation) {
		this.myLocation = myLocation;
	}

}
