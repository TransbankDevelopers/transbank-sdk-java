
package com.transbank.webpayserver.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oneClickReverseOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oneClickReverseOutput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://webservices.webpayserver.transbank.com/}baseBean"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="reverseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="reversed" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oneClickReverseOutput", propOrder = {
    "reverseCode",
    "reversed"
})
public class OneClickReverseOutput
    extends BaseBean
{

    protected Long reverseCode;
    protected boolean reversed;

    /**
     * Gets the value of the reverseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getReverseCode() {
        return reverseCode;
    }

    /**
     * Sets the value of the reverseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setReverseCode(Long value) {
        this.reverseCode = value;
    }

    /**
     * Gets the value of the reversed property.
     * 
     */
    public boolean isReversed() {
        return reversed;
    }

    /**
     * Sets the value of the reversed property.
     * 
     */
    public void setReversed(boolean value) {
        this.reversed = value;
    }

}
