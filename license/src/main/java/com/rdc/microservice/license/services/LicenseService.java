package com.rdc.microservice.license.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rdc.microservice.license.client.OrganizationDiscoveryClient;
import com.rdc.microservice.license.client.OrganizationFeignClient;
import com.rdc.microservice.license.client.OrganizationRestTemplateClient;
import com.rdc.microservice.license.dao.LicenseDao;
import com.rdc.microservice.license.model.License;
import com.rdc.microservice.license.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author asce
 * @since 2020/3/15
 */
@Service
public class LicenseService {

    @Autowired
    private LicenseDao licenseDao;

    @Autowired
    OrganizationFeignClient organizationFeignClient;

    @Autowired
    OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;


    public List<License> getLicenses(){
        return licenseDao.select();
    }

    public License getLicense(String organizationId,String licenseId, String clientType) {
        License license = licenseDao.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        Organization org = retrieveOrgInfo(organizationId, clientType);

        return license;
//                .withOrganizationName(org.getName())
//                .withContactName(org.getContactName())
//                .withContactEmail(org.getContactEmail())
//                .withContactPhone(org.getContactPhone());
    }

    private Organization retrieveOrgInfo(String organizationId, String clientType){
        Organization organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }

    @HystrixCommand
    public Organization getOrganization(String organizationId) {
        randomSleep();
        return organizationRestClient.getOrganization(organizationId);
    }

    private void randomSleep() {
        Random random = new Random();
        int res = random.nextInt(4);
        try {
            if (res == 3)   Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    设置超时时间12s
     */
    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(
                            name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "12000")})
    public Organization getOrganizationWithTimeOut(String organizationId) {
        return organizationRestClient.getOrganization(organizationId);
    }
    /*
    后备处理
     */
    @HystrixCommand(fallbackMethod = "buildFallbackLicense")
    public Organization getOrganizationWithFailback(String organizationId) {
        randomSleep();
        return organizationRestClient.getOrganization(organizationId);
    }

    private Organization buildFallbackLicense(String organizationId){
        return new Organization("myId");
    }
    /*
    舱壁模式
     */
    @HystrixCommand(threadPoolKey = "licensePool",threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "30"),
            @HystrixProperty(name = "maxQueueSize", value = "10")})
    public Organization getOrganizationWithPool(String organizationId) {
        return organizationRestClient.getOrganization(organizationId);
    }

    /*
    配置短路由行为
     */
    @HystrixCommand(
            fallbackMethod = "buildFallbackLicenseList",
            threadPoolKey = "liencePool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")},
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")
            })
    public Organization getOrganizationWithConfig(String organizationId) {
        return organizationRestClient.getOrganization(organizationId);
    }

}
