package com.rdc.microservice.organization.dao;

import com.rdc.microservice.organization.model.Organization;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author asce
 * @since 2020/3/15
 */
@Mapper
@Repository
public interface OrganizationDao {

    @Select("select * from organizations where organization_id = #{organizationId}")
    Organization findById(String organizationId);

    @Insert("insert into organizations(organization_id,name) values(#{organizationId},#{name})")
    int save(String organizationId, String name);
}
