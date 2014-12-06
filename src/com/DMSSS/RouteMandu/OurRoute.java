package com.DMSSS.RouteMandu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OurRoute {

	private double totalDistance, totalTime;
	private String instructions, points;

	public OurRoute(String data) throws JSONException {
		parseJSON(data);
	}

	private void parseJSON(String data) throws JSONException {

		String routePnts = "";
		String routeinst = "";
		double distance = 0;
		double duration = 0;
		JSONObject jsonObj = new JSONObject(data);
		JSONArray routes = jsonObj.getJSONArray("routes");
		JSONObject routesObj = routes.getJSONObject(0);

		// set Encrypt points
		JSONObject overviewObj = routesObj.getJSONObject("overview_polyline");
		routePnts = overviewObj.getString("points");
		setPoints(routePnts);

		JSONArray legs = routesObj.getJSONArray("legs");
		JSONObject legsObj = legs.getJSONObject(0);

		// set Total Distance
		JSONObject distObj = legsObj.getJSONObject("distance");
		distance = distObj.getDouble("value");
		setTotalDistance(distance);

		// set Total time
		JSONObject durationObj = legsObj.getJSONObject("duration");
		duration = durationObj.getDouble("value");
		setTotalTime(duration);

		// set Descriptions
		JSONArray stepsArray = legsObj.getJSONArray("steps");
		for (int i = 0; i < stepsArray.length(); i++) {
			JSONObject stepsObj = stepsArray.getJSONObject(i);
			routeinst += stepsObj.getString("html_instructions") + "\n";
			String temp = routeinst.replace("\u003cb\u003e", "");
			routeinst = temp.replace("\u003c/b\u003e", "");
		}
		setInstructions(routeinst);
		return;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}
}
