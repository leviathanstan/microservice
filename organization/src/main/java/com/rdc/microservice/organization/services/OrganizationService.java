package com.rdc.microservice.organization.services;

import com.rdc.microservice.organization.dao.OrganizationDao;
import com.rdc.microservice.organization.event.SimpleSourceBean;
import com.rdc.microservice.organization.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author asce
 * @since 2020/3/15
 */
@Service
public class OrganizationService {

    @Autowired
    private OrganizationDao orgRepository;
    @Autowired
    private SimpleSourceBean simpleSourceBean;

    public Organization getOrg(String organizationId) {
        return orgRepository.findById(organizationId);
    }

    public void saveOrg(Organization org){
        if (org == null)    org = new Organization();
        org.setId(UUID.randomUUID().toString());
        org.setName("myName");
        orgRepository.save(org.getId(), org.getName());
        simpleSourceBean.publishOrgChange("save", org.getId());
    }

//    public void updateOrg(Organization org){
//        orgRepository.save(org);
//    }
//
//    public void deleteOrg(Organization org){
//        orgRepository.delete( org.getId());
//    }
}
