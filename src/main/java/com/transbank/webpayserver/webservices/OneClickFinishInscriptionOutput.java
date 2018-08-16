
package com.transbank.webpayserver.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oneClickFinishInscriptionOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oneClickFinishInscriptionOutput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://webservices.webpayserver.transbank.com/}baseBean"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="authCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="creditCardType" type="{http://webservices.webpayserver.transbank.com/}creditCardType" minOccurs="0"/&gt;
 *         &lt;element name="last4CardDigits" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="responseCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="tbkUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oneClickFinishInscriptionOutput", propOrder = {
    "authCode",
    "creditCardType",
    "last4CardDigits",
    "responseCode",
    "tbkUser"
})
public class OneClickFinishInscriptionOutput
    extends BaseBean
{

    protected String authCode;
    @XmlSchemaType(name = "string")
    protected CreditCardType creditCardType;
    protected String last4CardDigits;
    protected int responseCode;
    protected String tbkUser;

    /**
     * Gets the value of the authCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthCode() {
        return authCode;
    }

    /**
     * Sets the value of the authCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthCode(String value) {
        this.authCode = value;
    }

    /**
     * Gets the value of the creditCardType property.
     * 
     * @return
     *     possible object is
     *     {@link CreditCardType }
     *     
     */
    public CreditCardType getCreditCardType() {
        return creditCardType;
    }

    /**
     * Sets the value of the creditCardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditCardType }
     *     
     */
    public void setCreditCardType(CreditCardType value) {
        this.creditCardType = value;
    }

    /**
     * Gets the value of the last4CardDigits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLast4CardDigits() {
        return last4CardDigits;
    }

    /**
     * Sets the value of the last4CardDigits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLast4CardDigits(String value) {
        this.last4CardDigits = value;
    }

    /**
     * Gets the value of the responseCode property.
     * 
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Sets the value of the responseCode property.
     * 
     */
    public void setResponseCode(int value) {
        this.responseCode = value;
    }

    /**
     * Gets the value of the tbkUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTbkUser() {
        return tbkUser;
    }

    /**
     * Sets the value of the tbkUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTbkUser(String value) {
        this.tbkUser = value;
    }

}
