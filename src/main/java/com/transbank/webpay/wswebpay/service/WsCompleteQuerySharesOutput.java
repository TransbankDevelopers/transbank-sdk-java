
package com.transbank.webpay.wswebpay.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsCompleteQuerySharesOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsCompleteQuerySharesOutput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="buyOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="deferredPeriods" type="{http://service.wswebpay.webpay.transbank.com/}completeDeferredPeriod" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="queryId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="shareAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsCompleteQuerySharesOutput", propOrder = {
    "buyOrder",
    "deferredPeriods",
    "queryId",
    "shareAmount",
    "token"
})
public class WsCompleteQuerySharesOutput {

    protected String buyOrder;
    @XmlElement(nillable = true)
    protected List<CompleteDeferredPeriod> deferredPeriods;
    protected long queryId;
    protected BigDecimal shareAmount;
    protected String token;

    /**
     * Gets the value of the buyOrder property.
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
     * Sets the value of the buyOrder property.
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
     * Gets the value of the deferredPeriods property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deferredPeriods property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeferredPeriods().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CompleteDeferredPeriod }
     * 
     * 
     */
    public List<CompleteDeferredPeriod> getDeferredPeriods() {
        if (deferredPeriods == null) {
            deferredPeriods = new ArrayList<CompleteDeferredPeriod>();
        }
        return this.deferredPeriods;
    }

    /**
     * Gets the value of the queryId property.
     * 
     */
    public long getQueryId() {
        return queryId;
    }

    /**
     * Sets the value of the queryId property.
     * 
     */
    public void setQueryId(long value) {
        this.queryId = value;
    }

    /**
     * Gets the value of the shareAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getShareAmount() {
        return shareAmount;
    }

    /**
     * Sets the value of the shareAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setShareAmount(BigDecimal value) {
        this.shareAmount = value;
    }

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

}
