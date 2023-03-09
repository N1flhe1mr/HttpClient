package ru.netology;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.List;

public class Main {
    public static final String NASA_API =
            "https://api.nasa.gov/planetary/apod?api_key=dHNu8FoYEN8qpBhIIOOONsv18XmiKgH947qezkOT";
    public static final String REMOTE_SERVICE_URI =
            "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(REMOTE_SERVICE_URI);
        CloseableHttpResponse response = httpClient.execute(request);

        List<Animal> animals = mapper.readValue(
                response.getEntity().getContent(),
                new TypeReference<>() {
                });

        animals.stream()
                .filter(value -> value.getUpvotes() != null && value.getUpvotes() > 0)
                .forEach(System.out::println);

        response.close();
        httpClient.close();
        downloadFileFromNasaApi();
    }

    public static void downloadFileFromNasaApi() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(NASA_API);
        CloseableHttpResponse response = httpClient.execute(request);
        JsonFromNasaApi jsonFromNasaApi = mapper.readValue(
                response.getEntity().getContent(),
                new TypeReference<>() {
                });

        request = new HttpGet(jsonFromNasaApi.getUrl());
        response = httpClient.execute(request);
        String[] slitedUrl = jsonFromNasaApi.getUrl().split("/");
        String fileName = slitedUrl[slitedUrl.length - 1];
        byte[] buffer = response.getEntity().getContent().readAllBytes();
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(buffer, 0, buffer.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        response.close();
        httpClient.close();
    }
}