
package com.transbank.webpay.wswebpay.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for completeCardDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="completeCardDetail"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://service.wswebpay.webpay.transbank.com/}cardDetail"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cvv" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "completeCardDetail", propOrder = {
    "cvv"
})
public class CompleteCardDetail
    extends CardDetail
{

    protected int cvv;

    /**
     * Gets the value of the cvv property.
     * 
     */
    public int getCvv() {
        return cvv;
    }

    /**
     * Sets the value of the cvv property.
     * 
     */
    public void setCvv(int value) {
        this.cvv = value;
    }

}
