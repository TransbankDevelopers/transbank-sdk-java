
package com.transbank.webpay.wswebpay.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for transactionResultOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transactionResultOutput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="accountingDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="buyOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cardDetail" type="{http://service.wswebpay.webpay.transbank.com/}cardDetail" minOccurs="0"/&gt;
 *         &lt;element name="detailOutput" type="{http://service.wswebpay.webpay.transbank.com/}wsTransactionDetailOutput" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="transactionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="urlRedirection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VCI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transactionResultOutput", propOrder = {
    "accountingDate",
    "buyOrder",
    "cardDetail",
    "detailOutput",
    "sessionId",
    "transactionDate",
    "urlRedirection",
    "vci"
})
public class TransactionResultOutput {

    protected String accountingDate;
    protected String buyOrder;
    protected CardDetail cardDetail;
    @XmlElement(nillable = true)
    protected List<WsTransactionDetailOutput> detailOutput;
    protected String sessionId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar transactionDate;
    protected String urlRedirection;
    @XmlElement(name = "VCI")
    protected String vci;

    /**
     * Gets the value of the accountingDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountingDate() {
        return accountingDate;
    }

    /**
     * Sets the value of the accountingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountingDate(String value) {
        this.accountingDate = value;
    }

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
     * Gets the value of the cardDetail property.
     * 
     * @return
     *     possible object is
     *     {@link CardDetail }
     *     
     */
    public CardDetail getCardDetail() {
        return cardDetail;
    }

    /**
     * Sets the value of the cardDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardDetail }
     *     
     */
    public void setCardDetail(CardDetail value) {
        this.cardDetail = value;
    }

    /**
     * Gets the value of the detailOutput property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detailOutput property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetailOutput().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsTransactionDetailOutput }
     * 
     * 
     */
    public List<WsTransactionDetailOutput> getDetailOutput() {
        if (detailOutput == null) {
            detailOutput = new ArrayList<WsTransactionDetailOutput>();
        }
        return this.detailOutput;
    }

    /**
     * Gets the value of the sessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionId(String value) {
        this.sessionId = value;
    }

    /**
     * Gets the value of the transactionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the value of the transactionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTransactionDate(XMLGregorianCalendar value) {
        this.transactionDate = value;
    }

    /**
     * Gets the value of the urlRedirection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlRedirection() {
        return urlRedirection;
    }

    /**
     * Sets the value of the urlRedirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlRedirection(String value) {
        this.urlRedirection = value;
    }

    /**
     * Gets the value of the vci property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVCI() {
        return vci;
    }

    /**
     * Sets the value of the vci property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVCI(String value) {
        this.vci = value;
    }

}
