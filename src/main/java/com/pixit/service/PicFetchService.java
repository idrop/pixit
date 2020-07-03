package com.pixit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixit.model.PixabayResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PicFetchService {

    public static void main(String[] args) throws IOException, InterruptedException {
        PicFetchService picFetchService = new PicFetchService();
        picFetchService.test();
    }

    public void test() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://pixabay.com/api/?key=16728140-8ba743c35ebcaef1633322bc7&q=yellow+flowers&image_type=photo"))
                .build();

        HttpResponse<InputStream> response =
                client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        System.out.println(response.body());

        ObjectMapper objectMapper = new ObjectMapper();

        PixabayResponse pixabayResponse = objectMapper.readValue(response.body(), PixabayResponse.class);
        System.out.println(pixabayResponse);
//        System.out.println(list);


    }

}
