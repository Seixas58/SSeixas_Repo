package com.example.library2024.model;

public class Cover {
    private String largeUrl;
    private String mediumUrl;
    private String smallUrl;

    public Cover(String coverUrl){}

    public Cover(String largeUrl, String mediumUrl, String smallUrl) {
        this.largeUrl = largeUrl.replace("/api", "http://193.136.62.24");
        this.mediumUrl = mediumUrl.replace("/api", "http://193.136.62.24");
        this.smallUrl = smallUrl.replace("/api", "http://193.136.62.24");
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }
}
