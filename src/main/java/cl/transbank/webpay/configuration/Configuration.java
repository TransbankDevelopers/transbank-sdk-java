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
   
    
    String private_key; 
    String public_cert;
    String webpay_cert;
    String commerce_code;
    ArrayList store_codes;
    String environment;

    public Configuration() {
    }

    public Configuration(String private_key, String public_cert, String webpay_cert, String commerce_code, String environment) {
        this.private_key = private_key;
        this.public_cert = public_cert;
        this.webpay_cert = webpay_cert;
        this.commerce_code = commerce_code;
        this.environment = environment;
    }
    
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

    public ArrayList getStoreCodes() {
        return store_codes;
    }

    public void setStoreCodes(ArrayList store_codes) {
        this.store_codes = store_codes;
    }
    
    
    




    
}
