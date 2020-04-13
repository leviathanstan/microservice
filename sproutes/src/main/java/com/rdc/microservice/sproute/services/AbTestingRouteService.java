package com.rdc.microservice.sproute.services;


import com.rdc.microservice.sproute.exception.NoRouteFound;
import com.rdc.microservice.sproute.model.AbTestingRoute;
import org.springframework.stereotype.Service;


@Service
public class AbTestingRouteService {

    public AbTestingRoute getRoute(String serviceName) {
        AbTestingRoute route = new AbTestingRoute();
        route.setActive("Y");
        route.setWeight(3);
        route.setServiceName("organization");
        route.setEndpoint("http://orgservice-new:8083");

        if (route == null){
            throw new NoRouteFound();
        }

        return route;
    }

}
