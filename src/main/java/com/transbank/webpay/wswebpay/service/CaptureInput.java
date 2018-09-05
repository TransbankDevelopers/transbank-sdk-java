
package com.transbank.webpay.wswebpay.service;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para captureInput complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="captureInput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="commerceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="buyOrder" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authorizationCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="captureAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "captureInput", propOrder = {
    "commerceId",
    "buyOrder",
    "authorizationCode",
    "captureAmount"
})
public class CaptureInput {

    protected long commerceId;
    @XmlElement(required = true)
    protected String buyOrder;
    @XmlElement(required = true)
    protected String authorizationCode;
    @XmlElement(required = true)
    protected BigDecimal captureAmount;

    /**
     * Obtiene el valor de la propiedad commerceId.
     * 
     */
    public long getCommerceId() {
        return commerceId;
    }

    /**
     * Define el valor de la propiedad commerceId.
     * 
     */
    public void setCommerceId(long value) {
        this.commerceId = value;
    }

    /**
     * Obtiene el valor de la propiedad buyOrder.
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
     * Define el valor de la propiedad buyOrder.
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
     * Obtiene el valor de la propiedad authorizationCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationCode() {
        return authorizationCode;
    }

    /**
     * Define el valor de la propiedad authorizationCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationCode(String value) {
        this.authorizationCode = value;
    }

    /**
     * Obtiene el valor de la propiedad captureAmount.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCaptureAmount() {
        return captureAmount;
    }

    /**
     * Define el valor de la propiedad captureAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCaptureAmount(BigDecimal value) {
        this.captureAmount = value;
    }

}
