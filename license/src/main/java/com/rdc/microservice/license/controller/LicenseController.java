package com.rdc.microservice.license.controller;

import com.rdc.microservice.license.model.Organization;
import com.rdc.microservice.license.services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rdc.microservice.license.model.License;

import java.util.List;

/**
 * @author asce
 * @since 2020/3/13
 */
@RestController
@RequestMapping(value = "/v1/licenses")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicense(@PathVariable("licenseId") String licenseId) {
        return new License().withId(licenseId).withProductName("Teleco").
                withLicenseType("Seat").withOrganizationId("TestOrg");
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value="/get",method = RequestMethod.GET)
    public List<License> getLicenses() {
        return licenseService.getLicenses();
    }

    @RequestMapping(value="/{licenseId}/{clientType}/{organizationId}",method = RequestMethod.GET)
    public License getLicensesWithClient(@PathVariable("organizationId") String organizationId,
                                          @PathVariable("licenseId") String licenseId,
                                          @PathVariable("clientType") String clientType) {
        return licenseService.getLicense(organizationId,licenseId, clientType);
    }

    @RequestMapping(value = "/organ/{organizationId}", method = RequestMethod.GET)
    public Organization getOrganizationId(@PathVariable("organizationId") String organizationId) {
        return licenseService.getOrganizationWithFailback(organizationId);
    }
}
