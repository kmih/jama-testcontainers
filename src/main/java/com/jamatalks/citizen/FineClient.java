package com.jamatalks.citizen;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FineClient {

    @Value("${service-fine.base-url}")
    private String baseUrl;

    private RestTemplate restTemplate = new RestTemplate();

    public List<Fine> getFines(Citizen citizen) {
        Fine[] fines = restTemplate.getForEntity(baseUrl + "/fines/" + citizen.getId(), Fine[].class).getBody();
        return Arrays.asList(fines);
    }
}
