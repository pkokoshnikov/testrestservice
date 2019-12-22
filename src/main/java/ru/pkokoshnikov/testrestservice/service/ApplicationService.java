package ru.pkokoshnikov.testrestservice.service;

import ru.pkokoshnikov.testrestservice.api.model.LastApplicationDTO;

public interface ApplicationService {
    LastApplicationDTO getLastApplicationForContact(long id);
}
