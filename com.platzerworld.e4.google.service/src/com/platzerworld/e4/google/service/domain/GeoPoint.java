package com.platzerworld.e4.google.service.domain;

public class GeoPoint {
	double fromLatitude;
	double fromLongitude;
	double toLatitude;
    double toLongitude;
    
    public GeoPoint(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude){
    	this.fromLatitude = fromLatitude;
    	this.fromLongitude = fromLongitude;
    	this.toLatitude = toLatitude;
    	this.toLongitude = toLongitude;
    }
    
    public GeoPoint(double latitude, double longitude){
    	
    }

	public double getFromLatitude() {
		return fromLatitude;
	}

	public void setFromLatitude(double fromLatitude) {
		this.fromLatitude = fromLatitude;
	}

	public double getFromLongitude() {
		return fromLongitude;
	}

	public void setFromLongitude(double fromLongitude) {
		this.fromLongitude = fromLongitude;
	}

	public double getToLatitude() {
		return toLatitude;
	}

	public void setToLatitude(double toLatitude) {
		this.toLatitude = toLatitude;
	}

	public double getToLongitude() {
		return toLongitude;
	}

	public void setToLongitude(double toLongitude) {
		this.toLongitude = toLongitude;
	}
    
    

}
