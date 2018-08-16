
package com.transbank.webpay.wswebpay.service;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsCompleteTransactionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="wsCompleteTransactionType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="TR_COMPLETA_WS"/&gt;
 *     &lt;enumeration value="TR_COMPLETA_MALL_WS"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "wsCompleteTransactionType")
@XmlEnum
public enum WsCompleteTransactionType {

    TR_COMPLETA_WS,
    TR_COMPLETA_MALL_WS;

    public String value() {
        return name();
    }

    public static WsCompleteTransactionType fromValue(String v) {
        return valueOf(v);
    }

}
