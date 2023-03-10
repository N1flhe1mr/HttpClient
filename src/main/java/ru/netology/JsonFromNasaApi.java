package ru.netology;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonFromNasaApi {
    private final String date;
    private final String explanation;
    private final String hdurl;
    private final String mediaType;
    private final String serviceVersion;
    private final String title;
    private final String url;

    public JsonFromNasaApi (
            @JsonProperty("date") String date,
            @JsonProperty("explanation") String explanation,
            @JsonProperty("hdurl") String hdurl,
            @JsonProperty("media_type") String mediaType,
            @JsonProperty("service_version") String serviceVersion,
            @JsonProperty("title") String title,
            @JsonProperty("url") String url
    ) {
        this.date = date;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.mediaType = mediaType;
        this.serviceVersion = serviceVersion;
        this.title = title;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "{" +
                "\n date: " + date +
                "\n explanation: " + explanation +
                "\n hdurl: " + hdurl +
                "\n media_type: " + mediaType +
                "\n service_version: " + serviceVersion +
                "\n title: " + title +
                "\n url: " + url +
                "\n }";
    }
}
