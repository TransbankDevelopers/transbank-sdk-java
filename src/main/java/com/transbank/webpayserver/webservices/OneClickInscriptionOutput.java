
package com.transbank.webpayserver.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oneClickInscriptionOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oneClickInscriptionOutput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://webservices.webpayserver.transbank.com/}baseBean"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="urlWebpay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oneClickInscriptionOutput", propOrder = {
    "token",
    "urlWebpay"
})
public class OneClickInscriptionOutput
    extends BaseBean
{

    protected String token;
    protected String urlWebpay;

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
     * Gets the value of the urlWebpay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlWebpay() {
        return urlWebpay;
    }

    /**
     * Sets the value of the urlWebpay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlWebpay(String value) {
        this.urlWebpay = value;
    }

}
