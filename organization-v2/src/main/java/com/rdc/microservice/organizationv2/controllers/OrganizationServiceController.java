package com.rdc.microservice.organizationv2.controllers;


import com.rdc.microservice.organizationv2.model.Organization;
import com.rdc.microservice.organizationv2.services.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value="v1/organizations")
public class OrganizationServiceController {
    @Autowired
    private OrganizationService orgService;


    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceController.class);

    @RequestMapping(value="/{organizationId}",method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        logger.debug("Looking up data for org {}",organizationId );

        Organization org = orgService.getOrg(organizationId);
        org.setContactName( "NEW::" + org.getContactName() );
        return org;
    }
}
