package com.rdc.microservice.organization.controller;

import com.rdc.microservice.organization.model.Organization;
import com.rdc.microservice.organization.services.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author asce
 * @since 2020/3/15
 */
@RestController
@RequestMapping(value = "/v1/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService orgService;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @RequestMapping(value="/{organizationId}",method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        logger.debug("Entering the getOrganization() method for the organizationId: {}",organizationId);
        return orgService.getOrg(organizationId);
    }

//    @RequestMapping(value="/{organizationId}",method = RequestMethod.PUT)
//    public void updateOrganization( @PathVariable("organizationId") String orgId, @RequestBody Organization org) {
//        orgService.updateOrg( org );
//    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.POST)
    public void saveOrganization(Organization org) {
        orgService.saveOrg(org);
    }

//    @RequestMapping(value="/{organizationId}",method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteOrganization( @PathVariable("orgId") String orgId,  @RequestBody Organization org) {
//        orgService.deleteOrg( org );
//    }
}
