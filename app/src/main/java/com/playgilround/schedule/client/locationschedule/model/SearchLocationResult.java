package com.playgilround.schedule.client.locationschedule.model;

import com.google.android.gms.maps.model.LatLng;

public class SearchLocationResult {

    private String title;
    private String snippet;
    private LatLng currentLocation;
    private LatLng searchResultLocation;
    private int zoomLevel;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public void setCurrentLocation(LatLng currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setSearchResultLocation(LatLng searchResultLocation) {
        this.searchResultLocation = searchResultLocation;
    }

    public void setZoomLevel(int zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    public LatLng getSearchResultLocation() {
        return searchResultLocation;
    }

    public int getZoomLevel() {
        return zoomLevel;
    }

}
