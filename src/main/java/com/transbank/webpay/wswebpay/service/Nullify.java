
package com.transbank.webpay.wswebpay.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para nullify complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="nullify">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nullificationInput" type="{http://service.wswebpay.webpay.transbank.com/}nullificationInput"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nullify", propOrder = {
    "nullificationInput"
})
public class Nullify {

    @XmlElement(required = true)
    protected NullificationInput nullificationInput;

    /**
     * Obtiene el valor de la propiedad nullificationInput.
     * 
     * @return
     *     possible object is
     *     {@link NullificationInput }
     *     
     */
    public NullificationInput getNullificationInput() {
        return nullificationInput;
    }

    /**
     * Define el valor de la propiedad nullificationInput.
     * 
     * @param value
     *     allowed object is
     *     {@link NullificationInput }
     *     
     */
    public void setNullificationInput(NullificationInput value) {
        this.nullificationInput = value;
    }

}
