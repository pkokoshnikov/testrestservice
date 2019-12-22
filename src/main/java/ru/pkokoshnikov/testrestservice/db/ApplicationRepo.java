package ru.pkokoshnikov.testrestservice.db;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.pkokoshnikov.testrestservice.db.model.Application;

public interface ApplicationRepo extends CrudRepository<Application, Long> {
    @Query("select id, contact_id, product_name, created_date_time from application where contact_id=(:customerId) " +
            "order by created_date_time desc limit 1")
    Application findLastApplicationByContactId(@Param("customerId") long customerId);
}
