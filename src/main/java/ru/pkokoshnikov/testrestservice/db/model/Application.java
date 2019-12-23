package ru.pkokoshnikov.testrestservice.db.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@NoArgsConstructor
@Getter
public class Application {
    @Id
    private long id;

    private long contactId;

    private String productName;

    private Long createdDateTime;

    public Application(long contactId, String productName, Long createdDateTime) {
        this.contactId = contactId;
        this.productName = productName;
        this.createdDateTime = createdDateTime;
    }
}

