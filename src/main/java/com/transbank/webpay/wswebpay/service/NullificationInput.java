
package com.transbank.webpay.wswebpay.service;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para nullificationInput complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="nullificationInput">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.wswebpay.webpay.transbank.com/}baseBean">
 *       &lt;sequence>
 *         &lt;element name="commerceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="buyOrder" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authorizedAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="authorizationCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nullifyAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nullificationInput", propOrder = {
    "commerceId",
    "buyOrder",
    "authorizedAmount",
    "authorizationCode",
    "nullifyAmount"
})
public class NullificationInput
    extends BaseBean
{

    protected long commerceId;
    @XmlElement(required = true)
    protected String buyOrder;
    @XmlElement(required = true)
    protected BigDecimal authorizedAmount;
    @XmlElement(required = true)
    protected String authorizationCode;
    @XmlElement(required = true)
    protected BigDecimal nullifyAmount;

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
     * Obtiene el valor de la propiedad authorizedAmount.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAuthorizedAmount() {
        return authorizedAmount;
    }

    /**
     * Define el valor de la propiedad authorizedAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAuthorizedAmount(BigDecimal value) {
        this.authorizedAmount = value;
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
     * Obtiene el valor de la propiedad nullifyAmount.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNullifyAmount() {
        return nullifyAmount;
    }

    /**
     * Define el valor de la propiedad nullifyAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNullifyAmount(BigDecimal value) {
        this.nullifyAmount = value;
    }

}
