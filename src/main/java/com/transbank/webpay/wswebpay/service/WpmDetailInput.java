
package com.transbank.webpay.wswebpay.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for wpmDetailInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wpmDetailInput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="serviceId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cardHolderId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cardHolderName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cardHolderLastName1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cardHolderLastName2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cardHolderMail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cellPhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="commerceMail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ufFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wpmDetailInput", propOrder = {
    "serviceId",
    "cardHolderId",
    "cardHolderName",
    "cardHolderLastName1",
    "cardHolderLastName2",
    "cardHolderMail",
    "cellPhoneNumber",
    "expirationDate",
    "commerceMail",
    "ufFlag"
})
public class WpmDetailInput {

    @XmlElement(required = true)
    protected String serviceId;
    @XmlElement(required = true)
    protected String cardHolderId;
    @XmlElement(required = true)
    protected String cardHolderName;
    @XmlElement(required = true)
    protected String cardHolderLastName1;
    @XmlElement(required = true)
    protected String cardHolderLastName2;
    @XmlElement(required = true)
    protected String cardHolderMail;
    @XmlElement(required = true)
    protected String cellPhoneNumber;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expirationDate;
    @XmlElement(required = true)
    protected String commerceMail;
    protected boolean ufFlag;

    /**
     * Gets the value of the serviceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * Sets the value of the serviceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceId(String value) {
        this.serviceId = value;
    }

    /**
     * Gets the value of the cardHolderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardHolderId() {
        return cardHolderId;
    }

    /**
     * Sets the value of the cardHolderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardHolderId(String value) {
        this.cardHolderId = value;
    }

    /**
     * Gets the value of the cardHolderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardHolderName() {
        return cardHolderName;
    }

    /**
     * Sets the value of the cardHolderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardHolderName(String value) {
        this.cardHolderName = value;
    }

    /**
     * Gets the value of the cardHolderLastName1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardHolderLastName1() {
        return cardHolderLastName1;
    }

    /**
     * Sets the value of the cardHolderLastName1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardHolderLastName1(String value) {
        this.cardHolderLastName1 = value;
    }

    /**
     * Gets the value of the cardHolderLastName2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardHolderLastName2() {
        return cardHolderLastName2;
    }

    /**
     * Sets the value of the cardHolderLastName2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardHolderLastName2(String value) {
        this.cardHolderLastName2 = value;
    }

    /**
     * Gets the value of the cardHolderMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardHolderMail() {
        return cardHolderMail;
    }

    /**
     * Sets the value of the cardHolderMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardHolderMail(String value) {
        this.cardHolderMail = value;
    }

    /**
     * Gets the value of the cellPhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    /**
     * Sets the value of the cellPhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCellPhoneNumber(String value) {
        this.cellPhoneNumber = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the commerceMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommerceMail() {
        return commerceMail;
    }

    /**
     * Sets the value of the commerceMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommerceMail(String value) {
        this.commerceMail = value;
    }

    /**
     * Gets the value of the ufFlag property.
     * 
     */
    public boolean isUfFlag() {
        return ufFlag;
    }

    /**
     * Sets the value of the ufFlag property.
     * 
     */
    public void setUfFlag(boolean value) {
        this.ufFlag = value;
    }

}
