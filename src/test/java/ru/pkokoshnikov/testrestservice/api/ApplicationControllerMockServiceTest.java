package ru.pkokoshnikov.testrestservice.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pkokoshnikov.testrestservice.IntegrationTestConfiguration;
import ru.pkokoshnikov.testrestservice.service.ApplicationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {IntegrationTestConfiguration.class})
public class ApplicationControllerMockServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ApplicationService applicationService;

    @Test
    public void testInternalServerError() {
        doThrow(new RuntimeException()).when(applicationService).getLastApplicationForContact(anyLong());

        ResponseEntity<String> errorMessage = restTemplate.getForEntity("http://localhost:" + port + "/rest/lastApplication/"
                + 1, String.class);
        assertThat(errorMessage.getBody()).isEqualTo("Internal server error");
        assertThat(errorMessage.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
