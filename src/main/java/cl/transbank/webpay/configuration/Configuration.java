/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.transbank.webpay.configuration;

import java.util.ArrayList;

/**
 *
 * @author jguerrero
 */
public class Configuration {
   
    
    private String private_key;
    private String public_cert;
    private String webpay_cert ;
    private String commerce_code;
    @Deprecated
    private ArrayList store_codes;
    private String environment = "INTEGRACION";

    public Configuration() {
    }

    public Configuration(String private_key, String public_cert, String webpay_cert, String commerce_code, String environment) {
        this.private_key = private_key;
        this.public_cert = public_cert;
        this.webpay_cert = webpay_cert;
        this.commerce_code = commerce_code;
        this.environment = environment;
    }
    
    @Deprecated
    public Configuration(String private_key, String public_cert, String webpay_cert, String commerce_code, String environment, ArrayList store_codes) {
        this.private_key = private_key;
        this.public_cert = public_cert;
        this.webpay_cert = webpay_cert;
        this.commerce_code = commerce_code;
        this.environment = environment;
        this.store_codes = store_codes;
    }

    public String getPrivateKey() {
        return private_key;
    }

    public void setPrivateKey(String private_key) {
        this.private_key = private_key;
    }

    public String getPublicCert() {
        return public_cert;
    }

    public void setPublicCert(String public_cert) {
        this.public_cert = public_cert;
    }

    public String getWebpayCert() {
        return webpay_cert;
    }

    public void setWebpayCert(String webpay_cert) {
        this.webpay_cert = webpay_cert;
    }

    public String getCommerceCode() {
        return commerce_code;
    }

    public void setCommerceCode(String commerce_code) {
        this.commerce_code = commerce_code;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Deprecated
    public ArrayList getStoreCodes() {
        return store_codes;
    }

    @Deprecated
    public void setStoreCodes(ArrayList store_codes) {
        this.store_codes = store_codes;
    }
    
    
    




    
}
