
package com.transbank.webpay.wswebpay.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for queryShare complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="queryShare"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="buyOrder" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="shareNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryShare", propOrder = {
    "token",
    "buyOrder",
    "shareNumber"
})
public class QueryShare {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String buyOrder;
    protected int shareNumber;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
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
     * Gets the value of the shareNumber property.
     * 
     */
    public int getShareNumber() {
        return shareNumber;
    }

    /**
     * Sets the value of the shareNumber property.
     * 
     */
    public void setShareNumber(int value) {
        this.shareNumber = value;
    }

}
