
package com.transbank.webpayserver.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oneClickReverseInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oneClickReverseInput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://webservices.webpayserver.transbank.com/}baseBean"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="buyorder" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oneClickReverseInput", propOrder = {
    "buyorder"
})
public class OneClickReverseInput
    extends BaseBean
{

    protected Long buyorder;

    /**
     * Gets the value of the buyorder property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBuyorder() {
        return buyorder;
    }

    /**
     * Sets the value of the buyorder property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBuyorder(Long value) {
        this.buyorder = value;
    }

}
