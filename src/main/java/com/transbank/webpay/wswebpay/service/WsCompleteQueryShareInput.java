
package com.transbank.webpay.wswebpay.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsCompleteQueryShareInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsCompleteQueryShareInput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="idQueryShare" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="deferredPeriodIndex" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsCompleteQueryShareInput", propOrder = {
    "idQueryShare",
    "deferredPeriodIndex"
})
public class WsCompleteQueryShareInput {

    protected long idQueryShare;
    protected Integer deferredPeriodIndex;

    /**
     * Gets the value of the idQueryShare property.
     * 
     */
    public long getIdQueryShare() {
        return idQueryShare;
    }

    /**
     * Sets the value of the idQueryShare property.
     * 
     */
    public void setIdQueryShare(long value) {
        this.idQueryShare = value;
    }

    /**
     * Gets the value of the deferredPeriodIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDeferredPeriodIndex() {
        return deferredPeriodIndex;
    }

    /**
     * Sets the value of the deferredPeriodIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDeferredPeriodIndex(Integer value) {
        this.deferredPeriodIndex = value;
    }

}
