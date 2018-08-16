
package com.transbank.webpay.wswebpay.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for initCompleteTransaction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="initCompleteTransaction"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="wsCompleteInitTransactionInput" type="{http://service.wswebpay.webpay.transbank.com/}wsCompleteInitTransactionInput"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "initCompleteTransaction", propOrder = {
    "wsCompleteInitTransactionInput"
})
public class InitCompleteTransaction {

    @XmlElement(required = true)
    protected WsCompleteInitTransactionInput wsCompleteInitTransactionInput;

    /**
     * Gets the value of the wsCompleteInitTransactionInput property.
     * 
     * @return
     *     possible object is
     *     {@link WsCompleteInitTransactionInput }
     *     
     */
    public WsCompleteInitTransactionInput getWsCompleteInitTransactionInput() {
        return wsCompleteInitTransactionInput;
    }

    /**
     * Sets the value of the wsCompleteInitTransactionInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsCompleteInitTransactionInput }
     *     
     */
    public void setWsCompleteInitTransactionInput(WsCompleteInitTransactionInput value) {
        this.wsCompleteInitTransactionInput = value;
    }

}
