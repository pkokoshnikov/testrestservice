package ru.pkokoshnikov.testrestservice.api;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pkokoshnikov.testrestservice.api.model.LastApplicationDTO;
import ru.pkokoshnikov.testrestservice.service.ApplicationService;

@RestController
@RequestMapping(value = "/rest")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @ApiOperation(value = "Get last application for the contactId", response = LastApplicationDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Application is found", response = LastApplicationDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Application or contactId is not found")
    })
    @GetMapping(value = "/lastApplication/{contactId}", headers = {"Accept=application/json,application/xml"},
            produces = {"application/json", "application/xml"})
    public LastApplicationDTO getLastApplication(@ApiParam(name = "contactId")
                                             @PathVariable(value = "contactId") Long contactId) {
        return applicationService.getLastApplicationForContact(contactId);
    }

    @ApiOperation(value = "Ping-pong", response = String.class)
    @ApiResponses(value = @ApiResponse(code = 200, message = "pong"))
    @GetMapping(value = "/ping")
    public String ping() {
        return "pong";
    }
}

