package com.lcydream.project.saverule;

import javax.jws.WebMethod;
import javax.jws.WebService;


public interface HelloWorldWs {

    @WebMethod
    String sayHello(String name);
}
