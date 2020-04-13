package com.rdc.microservice.license.dao;

import com.rdc.microservice.license.model.License;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author asce
 * @since 2020/3/15
 */
@Mapper
@Repository
public interface LicenseDao {

    @Select("select * from license")
    List<License> select();

    @Select("select * from license where organization_id = #{organizationId} and license_id = #{licenseId}")
    License findByOrganizationIdAndLicenseId(String organizationId,String licenseId);
}
