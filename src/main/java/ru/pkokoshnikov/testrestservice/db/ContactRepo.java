package ru.pkokoshnikov.testrestservice.db;

import org.springframework.data.repository.CrudRepository;
import ru.pkokoshnikov.testrestservice.db.model.Contact;

public interface ContactRepo extends CrudRepository<Contact, Long> {
}
