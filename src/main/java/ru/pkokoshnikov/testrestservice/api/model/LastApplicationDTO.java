package ru.pkokoshnikov.testrestservice.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ApiModel(description = "Describes a last application DTO")
@XmlRootElement(name = "application")
public class LastApplicationDTO {

    @JsonProperty(value = "application_id")
    @XmlElement(name = "application_id")
    @ApiModelProperty(value = "id of the application")
    private long id;

    @JsonProperty(value = "contact_id")
    @XmlElement(name = "contact_id")
    @ApiModelProperty(value = "id of the contact")
    private long contactId;

    @JsonProperty(value = "product_name")
    @XmlElement(name = "product_name")
    @ApiModelProperty(value = "product name", example = "credit")
    private String productName;


    @JsonProperty(value = "dt_created")
    @JsonSerialize(converter = ZoneDateTimeToStringConverter.class)
    @JsonDeserialize(converter = StringToZoneDateTimeConverter.class)
    @XmlElement(name = "dt_created")
    @XmlJavaTypeAdapter(ZonedDateTimeXmlAdapter.class)
    @ApiModelProperty(value = "creation date time in ISO-8601 with timezone")
    private ZonedDateTime zonedDateTime;
}
