package ru.home.m3u8gen.model;

public class Song {

    private String path;
    private String url;
    private String name;

    public Song(String name, String path, String url) {
        this.path = path;
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
