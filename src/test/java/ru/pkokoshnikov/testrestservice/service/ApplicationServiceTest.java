package ru.pkokoshnikov.testrestservice.service;

import org.junit.Test;
import ru.pkokoshnikov.testrestservice.db.ApplicationRepo;
import ru.pkokoshnikov.testrestservice.db.ContactRepo;
import ru.pkokoshnikov.testrestservice.exception.ApplicationServiceException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ApplicationServiceTest {

    @Test
    public void testContactIsNotFoundException() {
        ContactRepo contactRepo = mock(ContactRepo.class);
        ApplicationRepo applicationRepo = mock(ApplicationRepo.class);

        ApplicationService applicationService = new ApplicationServiceImpl(contactRepo, applicationRepo);
        doReturn(false).when(contactRepo).existsById(1L);

        assertThatThrownBy(() -> applicationService.getLastApplicationForContact(1L))
                .isInstanceOf(ApplicationServiceException.class)
                .hasMessage(ApplicationServiceImpl.CONTACT_NOT_FOUND_FOR_ID + 1);
    }

    @Test
    public void testApplicationIsNotFoundException() {
        ContactRepo contactRepo = mock(ContactRepo.class);
        ApplicationRepo applicationRepo = mock(ApplicationRepo.class);

        ApplicationService applicationService = new ApplicationServiceImpl(contactRepo, applicationRepo);
        doReturn(true).when(contactRepo).existsById(1L);
        doReturn(null).when(applicationRepo).findLastApplicationByContactId(1L);

        assertThatThrownBy(() -> applicationService.getLastApplicationForContact(1L))
                .isInstanceOf(ApplicationServiceException.class)
                .hasMessage(ApplicationServiceImpl.APPLICATION_IS_NOT_FOUND_FOR_CONTACT_ID + 1);
    }
}
