package com.rdc.microservice.organization.event;

import com.rdc.microservice.organization.model.OrganizationChangeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author asce
 * @since 2020/3/20
 */
@Component
public class SimpleSourceBean {

    private Source source;

    @Autowired
    public SimpleSourceBean(Source source) {
        this.source = source;
    }

    public void publishOrgChange(String action, String orgId) {
        System.out.println("Sending Kafka message {} for Organization Id: {}" + action + orgId);
        OrganizationChangeModel change =  new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action,
                orgId,
                "123");//UserContext.getCorrelationId());

        source.output().send(MessageBuilder.withPayload(change).build());
    }
}
