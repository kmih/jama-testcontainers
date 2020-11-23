package com.jamatalks.citizen;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.server.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CitizenResourceTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getCitizenStatistic_forHelenZheludOK() {

        new MockServerClient(mockServer.getHost(), mockServer.getServerPort())
                .when(request()
                        .withPath("/fines/1"))
                .respond(response()
                        .withBody("[]").withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE));

        ResponseEntity<CitizenStatisticDto> entity = restTemplate
                .getForEntity("http://localhost:" + port + "/citizens/98y783y7438uyegbkyg37yrg", CitizenStatisticDto.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isNotNull();
        assertThat(entity.getBody().finesTotal).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void getCitizenStatistic_forMikeBerlin() {

        new MockServerClient(mockServer.getHost(), mockServer.getServerPort())
                .when(request()
                        .withPath("/fines/2"))
                .respond(response()
                        .withBody("[{\"sum\":1499.50}]").withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE));

        ResponseEntity<CitizenStatisticDto> entity = restTemplate
                .getForEntity("http://localhost:" + port + "/citizens/9284hf294fb29248fkds", CitizenStatisticDto.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isNotNull();
        assertThat(entity.getBody().finesTotal).isEqualTo(BigDecimal.valueOf(1499.50));
    }

    @Test
    void getCitizenStatistic_forUnknown() {
        ResponseEntity<CitizenStatisticDto> entity = restTemplate
                .getForEntity("http://localhost:" + port + "/citizens/1", CitizenStatisticDto.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
