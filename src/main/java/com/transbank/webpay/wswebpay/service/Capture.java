
package com.transbank.webpay.wswebpay.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para capture complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="capture">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="captureInput" type="{http://service.wswebpay.webpay.transbank.com/}captureInput"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "capture", propOrder = {
    "captureInput"
})
public class Capture {

    @XmlElement(required = true)
    protected CaptureInput captureInput;

    /**
     * Obtiene el valor de la propiedad captureInput.
     * 
     * @return
     *     possible object is
     *     {@link CaptureInput }
     *     
     */
    public CaptureInput getCaptureInput() {
        return captureInput;
    }

    /**
     * Define el valor de la propiedad captureInput.
     * 
     * @param value
     *     allowed object is
     *     {@link CaptureInput }
     *     
     */
    public void setCaptureInput(CaptureInput value) {
        this.captureInput = value;
    }

}
