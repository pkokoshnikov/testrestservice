package ru.pkokoshnikov.testrestservice.db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Getter
public class Contact {
    @Id
    private long id;
    private String name;

    public Contact(String name) {
        this.name = name;
    }
}
