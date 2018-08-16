
package com.transbank.webpay.wswebpay.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsCompletePaymentTypeInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsCompletePaymentTypeInput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="commerceCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="buyOrder" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="queryShareInput" type="{http://service.wswebpay.webpay.transbank.com/}wsCompleteQueryShareInput" minOccurs="0"/&gt;
 *         &lt;element name="gracePeriod" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsCompletePaymentTypeInput", propOrder = {
    "commerceCode",
    "buyOrder",
    "queryShareInput",
    "gracePeriod"
})
public class WsCompletePaymentTypeInput {

    @XmlElement(required = true)
    protected String commerceCode;
    @XmlElement(required = true)
    protected String buyOrder;
    protected WsCompleteQueryShareInput queryShareInput;
    protected Boolean gracePeriod;

    /**
     * Gets the value of the commerceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommerceCode() {
        return commerceCode;
    }

    /**
     * Sets the value of the commerceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommerceCode(String value) {
        this.commerceCode = value;
    }

    /**
     * Gets the value of the buyOrder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyOrder() {
        return buyOrder;
    }

    /**
     * Sets the value of the buyOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyOrder(String value) {
        this.buyOrder = value;
    }

    /**
     * Gets the value of the queryShareInput property.
     * 
     * @return
     *     possible object is
     *     {@link WsCompleteQueryShareInput }
     *     
     */
    public WsCompleteQueryShareInput getQueryShareInput() {
        return queryShareInput;
    }

    /**
     * Sets the value of the queryShareInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsCompleteQueryShareInput }
     *     
     */
    public void setQueryShareInput(WsCompleteQueryShareInput value) {
        this.queryShareInput = value;
    }

    /**
     * Gets the value of the gracePeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGracePeriod() {
        return gracePeriod;
    }

    /**
     * Sets the value of the gracePeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGracePeriod(Boolean value) {
        this.gracePeriod = value;
    }

}
