
package com.transbank.webpay.wswebpay.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsInitTransactionInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsInitTransactionInput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="wSTransactionType" type="{http://service.wswebpay.webpay.transbank.com/}wsTransactionType"/&gt;
 *         &lt;element name="commerceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="buyOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="returnURL" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
 *         &lt;element name="finalURL" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
 *         &lt;element name="transactionDetails" type="{http://service.wswebpay.webpay.transbank.com/}wsTransactionDetail" maxOccurs="unbounded"/&gt;
 *         &lt;element name="wPMDetail" type="{http://service.wswebpay.webpay.transbank.com/}wpmDetailInput" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsInitTransactionInput", propOrder = {
    "wsTransactionType",
    "commerceId",
    "buyOrder",
    "sessionId",
    "returnURL",
    "finalURL",
    "transactionDetails",
    "wpmDetail"
})
public class WsInitTransactionInput {

    @XmlElement(name = "wSTransactionType", required = true)
    @XmlSchemaType(name = "string")
    protected WsTransactionType wsTransactionType;
    protected String commerceId;
    protected String buyOrder;
    protected String sessionId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String returnURL;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String finalURL;
    @XmlElement(required = true)
    protected List<WsTransactionDetail> transactionDetails;
    @XmlElement(name = "wPMDetail")
    protected WpmDetailInput wpmDetail;

    /**
     * Gets the value of the wsTransactionType property.
     * 
     * @return
     *     possible object is
     *     {@link WsTransactionType }
     *     
     */
    public WsTransactionType getWSTransactionType() {
        return wsTransactionType;
    }

    /**
     * Sets the value of the wsTransactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsTransactionType }
     *     
     */
    public void setWSTransactionType(WsTransactionType value) {
        this.wsTransactionType = value;
    }

    /**
     * Gets the value of the commerceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommerceId() {
        return commerceId;
    }

    /**
     * Sets the value of the commerceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommerceId(String value) {
        this.commerceId = value;
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
     * Gets the value of the sessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionId(String value) {
        this.sessionId = value;
    }

    /**
     * Gets the value of the returnURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnURL() {
        return returnURL;
    }

    /**
     * Sets the value of the returnURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnURL(String value) {
        this.returnURL = value;
    }

    /**
     * Gets the value of the finalURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinalURL() {
        return finalURL;
    }

    /**
     * Sets the value of the finalURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinalURL(String value) {
        this.finalURL = value;
    }

    /**
     * Gets the value of the transactionDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transactionDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransactionDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsTransactionDetail }
     * 
     * 
     */
    public List<WsTransactionDetail> getTransactionDetails() {
        if (transactionDetails == null) {
            transactionDetails = new ArrayList<WsTransactionDetail>();
        }
        return this.transactionDetails;
    }

    /**
     * Gets the value of the wpmDetail property.
     * 
     * @return
     *     possible object is
     *     {@link WpmDetailInput }
     *     
     */
    public WpmDetailInput getWPMDetail() {
        return wpmDetail;
    }

    /**
     * Sets the value of the wpmDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link WpmDetailInput }
     *     
     */
    public void setWPMDetail(WpmDetailInput value) {
        this.wpmDetail = value;
    }

}
