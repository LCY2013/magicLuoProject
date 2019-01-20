
package com.lcydream.project.client.jdk;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.lcydream.project.client.jdk package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CheckNickName_QNAME = new QName("http://impl.jdk.server.project.lcydream.com/", "checkNickName");
    private final static QName _CheckNickNameResponse_QNAME = new QName("http://impl.jdk.server.project.lcydream.com/", "checkNickNameResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.lcydream.project.client.jdk
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CheckNickName }
     * 
     */
    public CheckNickName createCheckNickName() {
        return new CheckNickName();
    }

    /**
     * Create an instance of {@link CheckNickNameResponse }
     * 
     */
    public CheckNickNameResponse createCheckNickNameResponse() {
        return new CheckNickNameResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckNickName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.jdk.server.project.lcydream.com/", name = "checkNickName")
    public JAXBElement<CheckNickName> createCheckNickName(CheckNickName value) {
        return new JAXBElement<CheckNickName>(_CheckNickName_QNAME, CheckNickName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckNickNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.jdk.server.project.lcydream.com/", name = "checkNickNameResponse")
    public JAXBElement<CheckNickNameResponse> createCheckNickNameResponse(CheckNickNameResponse value) {
        return new JAXBElement<CheckNickNameResponse>(_CheckNickNameResponse_QNAME, CheckNickNameResponse.class, null, value);
    }

}
