
package com.transbank.webpay.wswebpay.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsCompleteInitTransactionInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsCompleteInitTransactionInput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="transactionType" type="{http://service.wswebpay.webpay.transbank.com/}wsCompleteTransactionType"/&gt;
 *         &lt;element name="commerceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="buyOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cardDetail" type="{http://service.wswebpay.webpay.transbank.com/}completeCardDetail"/&gt;
 *         &lt;element name="transactionDetails" type="{http://service.wswebpay.webpay.transbank.com/}wsCompleteTransactionDetail" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsCompleteInitTransactionInput", propOrder = {
    "transactionType",
    "commerceId",
    "buyOrder",
    "sessionId",
    "cardDetail",
    "transactionDetails"
})
public class WsCompleteInitTransactionInput {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected WsCompleteTransactionType transactionType;
    protected String commerceId;
    protected String buyOrder;
    protected String sessionId;
    @XmlElement(required = true)
    protected CompleteCardDetail cardDetail;
    @XmlElement(required = true)
    protected List<WsCompleteTransactionDetail> transactionDetails;

    /**
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link WsCompleteTransactionType }
     *     
     */
    public WsCompleteTransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsCompleteTransactionType }
     *     
     */
    public void setTransactionType(WsCompleteTransactionType value) {
        this.transactionType = value;
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
     * Gets the value of the cardDetail property.
     * 
     * @return
     *     possible object is
     *     {@link CompleteCardDetail }
     *     
     */
    public CompleteCardDetail getCardDetail() {
        return cardDetail;
    }

    /**
     * Sets the value of the cardDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompleteCardDetail }
     *     
     */
    public void setCardDetail(CompleteCardDetail value) {
        this.cardDetail = value;
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
     * {@link WsCompleteTransactionDetail }
     * 
     * 
     */
    public List<WsCompleteTransactionDetail> getTransactionDetails() {
        if (transactionDetails == null) {
            transactionDetails = new ArrayList<WsCompleteTransactionDetail>();
        }
        return this.transactionDetails;
    }

}
