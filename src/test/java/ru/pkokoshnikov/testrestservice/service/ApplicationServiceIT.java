package ru.pkokoshnikov.testrestservice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pkokoshnikov.testrestservice.IntegrationTestConfiguration;
import ru.pkokoshnikov.testrestservice.TestRestServiceJdbConfiguration;
import ru.pkokoshnikov.testrestservice.api.model.LastApplicationDTO;
import ru.pkokoshnikov.testrestservice.db.ApplicationRepo;
import ru.pkokoshnikov.testrestservice.db.ContactRepo;
import ru.pkokoshnikov.testrestservice.db.model.Application;
import ru.pkokoshnikov.testrestservice.db.model.Contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestRestServiceJdbConfiguration.class, ApplicationRepo.class, ContactRepo.class, IntegrationTestConfiguration.class})
public class ApplicationServiceIT {
    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    private ApplicationRepo applicationRepo;

    private ApplicationService applicationService;

    @Before
    public void init() {
        applicationService = new ApplicationServiceImpl(contactRepo, applicationRepo);
    }

    @Test
    public void getLastCreatedApplicationRepo() {
        Contact contact = contactRepo.save(new Contact("testCustomer"));

        long now = System.currentTimeMillis();
        Application expectedApplication = applicationRepo.save(new Application(contact.getId(), "credit", now));
        applicationRepo.save(new Application(contact.getId(), "credit", now - 1));

        LastApplicationDTO actualApplication = applicationService.getLastApplicationForContact(contact.getId());
        assertThat(actualApplication.getId()).isEqualTo(expectedApplication.getId());
        assertThat(actualApplication.getContactId()).isEqualTo(expectedApplication.getContactId());
        assertThat(actualApplication.getProductName()).isEqualTo(expectedApplication.getProductName());
        assertThat(actualApplication.getZonedDateTime().toInstant().toEpochMilli()).isEqualTo(expectedApplication.getCreatedDateTime());
    }
}
