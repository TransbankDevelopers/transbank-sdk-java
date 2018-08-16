
package com.transbank.webpay.wswebpay.service;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsTransactionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="wsTransactionType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="TR_NORMAL_WS"/&gt;
 *     &lt;enumeration value="TR_NORMAL_WS_WPM"/&gt;
 *     &lt;enumeration value="TR_MALL_WS"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "wsTransactionType")
@XmlEnum
public enum WsTransactionType {

    TR_NORMAL_WS,
    TR_NORMAL_WS_WPM,
    TR_MALL_WS;

    public String value() {
        return name();
    }

    public static WsTransactionType fromValue(String v) {
        return valueOf(v);
    }

}
