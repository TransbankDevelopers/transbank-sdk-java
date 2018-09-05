
package com.transbank.webpay.wswebpay.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for initTransaction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="initTransaction"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="wsInitTransactionInput" type="{http://service.wswebpay.webpay.transbank.com/}wsInitTransactionInput"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "initTransaction", propOrder = {
    "wsInitTransactionInput"
})
public class InitTransaction {

    @XmlElement(required = true)
    protected WsInitTransactionInput wsInitTransactionInput;

    /**
     * Gets the value of the wsInitTransactionInput property.
     * 
     * @return
     *     possible object is
     *     {@link WsInitTransactionInput }
     *     
     */
    public WsInitTransactionInput getWsInitTransactionInput() {
        return wsInitTransactionInput;
    }

    /**
     * Sets the value of the wsInitTransactionInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsInitTransactionInput }
     *     
     */
    public void setWsInitTransactionInput(WsInitTransactionInput value) {
        this.wsInitTransactionInput = value;
    }

}
