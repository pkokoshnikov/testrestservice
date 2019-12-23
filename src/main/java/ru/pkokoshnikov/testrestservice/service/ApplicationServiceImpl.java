package ru.pkokoshnikov.testrestservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pkokoshnikov.testrestservice.api.model.LastApplicationDTO;
import ru.pkokoshnikov.testrestservice.db.ApplicationRepo;
import ru.pkokoshnikov.testrestservice.db.ContactRepo;
import ru.pkokoshnikov.testrestservice.db.model.Application;
import ru.pkokoshnikov.testrestservice.exception.ApplicationServiceException;

import java.time.Instant;
import java.time.ZoneId;


@Service
public class ApplicationServiceImpl implements ApplicationService {
    public static final String CONTACT_NOT_FOUND_FOR_ID = "Contact not found for id:";
    public static final String APPLICATION_IS_NOT_FOUND_FOR_CONTACT_ID = "Application is not found for contact id:";

    private ApplicationRepo applicationRepo;
    private ContactRepo contactRepo;

    @Autowired
    public ApplicationServiceImpl(ContactRepo contactRepo, ApplicationRepo applicationRepo) {
        this.applicationRepo = applicationRepo;
        this.contactRepo = contactRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public LastApplicationDTO getLastApplicationForContact(long contactId) {
        if (!contactRepo.existsById(contactId)) {
            throw new ApplicationServiceException(CONTACT_NOT_FOUND_FOR_ID + contactId);
        }

        Application foundApplication = applicationRepo.findLastApplicationByContactId(contactId);
        if (foundApplication == null) {
            throw new ApplicationServiceException(APPLICATION_IS_NOT_FOUND_FOR_CONTACT_ID + contactId);
        }

        return new LastApplicationDTO(foundApplication.getId(),
                foundApplication.getContactId(), foundApplication.getProductName(),
                Instant.ofEpochMilli(foundApplication.getCreatedDateTime()).atZone(ZoneId.systemDefault()));
    }
}

