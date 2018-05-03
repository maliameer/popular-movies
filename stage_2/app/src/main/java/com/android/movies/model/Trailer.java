package com.android.movies.model;

public class Trailer {

    private String name;
    private String key;
    private String site;

    public Trailer(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Movie Trailer Details:\n");
        stringBuilder.append("\tName = " + name + "\n");
        stringBuilder.append("\tKey = " + key + "\n");
        stringBuilder.append("\tSite = " + site);

        return stringBuilder.toString();

    }

}