package ru.pkokoshnikov.testrestservice.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pkokoshnikov.testrestservice.IntegrationTestConfiguration;
import ru.pkokoshnikov.testrestservice.api.model.LastApplicationDTO;
import ru.pkokoshnikov.testrestservice.db.ApplicationRepo;
import ru.pkokoshnikov.testrestservice.db.ContactRepo;
import ru.pkokoshnikov.testrestservice.db.model.Application;
import ru.pkokoshnikov.testrestservice.db.model.Contact;
import ru.pkokoshnikov.testrestservice.service.ApplicationServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {IntegrationTestConfiguration.class})
public class ApplicationControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ApplicationRepo applicationRepo;

    @Autowired
    private ContactRepo contactRepo;

    @Test
    public void getLastTaskTest() {
        Contact contact = contactRepo.save(new Contact("testCustomer"));

        long now = System.currentTimeMillis();
        Application expectedApplication = applicationRepo.save(new Application(contact.getId(), "credit", now));
        applicationRepo.save(new Application(contact.getId(), "credit", now - 1));

        ResponseEntity<LastApplicationDTO> actualApplication = restTemplate.getForEntity("http://localhost:" + port + "/rest/lastApplication/"
                + contact.getId(), LastApplicationDTO.class);


        assertThat(actualApplication.getBody().getId()).isEqualTo(expectedApplication.getId());
        assertThat(actualApplication.getBody().getContactId()).isEqualTo(expectedApplication.getContactId());
        assertThat(actualApplication.getBody().getProductName()).isEqualTo(expectedApplication.getProductName());
        assertThat(actualApplication.getBody().getZonedDateTime().toInstant().toEpochMilli()).isEqualTo(expectedApplication.getCreatedDateTime());

        assertThat(actualApplication.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void contactIsNotFound() {
        applicationRepo.deleteAll();
        contactRepo.deleteAll();

        ResponseEntity<String> errorMessage = restTemplate.getForEntity("http://localhost:" + port + "/rest/lastApplication/" + 1,
                String.class);

        assertThat(errorMessage.getBody()).isEqualTo(ApplicationServiceImpl.CONTACT_NOT_FOUND_FOR_ID + "1");
        assertThat(errorMessage.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void applicationIsNotFound() {
        Contact contact = contactRepo.save(new Contact("testCustomer"));

        ResponseEntity<String> errorMessage = restTemplate.getForEntity("http://localhost:" + port + "/rest/lastApplication/"
                + contact.getId(), String.class);

        assertThat(errorMessage.getBody()).isEqualTo(ApplicationServiceImpl.APPLICATION_IS_NOT_FOUND_FOR_CONTACT_ID + contact.getId());
        assertThat(errorMessage.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
