
package com.transbank.webpayserver.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for baseBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baseBean"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseBean")
@XmlSeeAlso({
    OneClickRemoveUserInput.class,
    OneClickInscriptionInput.class,
    OneClickInscriptionOutput.class,
    OneClickFinishInscriptionInput.class,
    OneClickFinishInscriptionOutput.class,
    OneClickReverseInput.class,
    OneClickReverseOutput.class,
    OneClickPayInput.class,
    OneClickPayOutput.class
})
public class BaseBean {


}
