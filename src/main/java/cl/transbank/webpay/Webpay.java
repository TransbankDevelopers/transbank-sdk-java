/**
  * @brief      Ecommerce Plugin for chilean Webpay
  * @category   Plugins/SDK
  * @author     Allware Ltda. (http://www.allware.cl)
  * @copyright  2015 Transbank S.A. (http://www.tranbank.cl)
  * @date       Jan 2016
  * @license    GNU LGPL
  * @version    2.0.1
  * @link       http://transbankdevelopers.cl/
  *
  * This software was created for easy integration of ecommerce
  * portals with Transbank Webpay solution.
  *
  * Required:
  *  - Java Runtime 7
  *
  * See documentation and how to install at link site
  *
  */

package cl.transbank.webpay;

import cl.transbank.webpay.configuration.Configuration;
import cl.transbank.webpay.security.SoapSignature;
import cl.transbank.webpay.security.WebpayCertificateHelper;

/**
 *
 * @author rbertuzzi
 */
public class Webpay {    
    public static enum Environment {        
        INTEGRACION,
        CERTIFICACION,
        PRODUCCION;
    }    
    
    
    SoapSignature signature;
    Environment mode;
    String commerceCode;
    
    WebpayNormal normalTransaction;
    WebpayOneClick oneClickTransaction;
    WebpayMallNormal mallNormalTransaction;
    WebpayComplete completeTransaction;
    WebpayCapture captureTransaction;
    WebpayNullify nullifyTransaction;
    
    public Webpay(Environment env, String commerceCode, SoapSignature signature){
        this(env, commerceCode);
        setSignature(signature);
    }
    
    public Webpay(Environment env, String commerceCode){
        this.mode = env;
        this.commerceCode = commerceCode;        
    }
    
    public Webpay(Configuration conf){
        
        if(conf.getEnvironment().equalsIgnoreCase("INTEGRACION")){
            this.mode = Webpay.Environment.INTEGRACION;
        }else if(conf.getEnvironment().equalsIgnoreCase("CERTIFICACION")){
            this.mode = Webpay.Environment.CERTIFICACION;
        }else if(conf.getEnvironment().equalsIgnoreCase("PRODUCCION")){
            this.mode = Webpay.Environment.PRODUCCION;
        }
        this.commerceCode = conf.getCommerceCode();
        
        SoapSignature signature = new SoapSignature();
        signature.setPrivateCertificate(conf.getPrivateKey(), conf.getPublicCert());
        signature.setWebpayCertificate(conf.getWebpayCert());
        setSignature(signature);
        
    }
    
    public void setSignature(SoapSignature signature){
        this.signature = signature;
        WebpayCertificateHelper.checkCertificate(mode, signature);
    }
    
    public synchronized WebpayNormal getNormalTransaction() throws Exception {
        if (normalTransaction == null){
            normalTransaction = new WebpayNormal(mode, commerceCode, signature);
        }
        return normalTransaction;
    }
    
    public synchronized WebpayOneClick getOneClickTransaction() throws Exception {
        if (oneClickTransaction == null){
            oneClickTransaction = new WebpayOneClick(mode, commerceCode, signature);
        }
        return oneClickTransaction;
    }
    
    public synchronized WebpayMallNormal getMallNormalTransaction() throws Exception {
        if (mallNormalTransaction == null){
            mallNormalTransaction = new WebpayMallNormal(mode, commerceCode, signature);
        }
        return mallNormalTransaction;
    }
    
    public synchronized WebpayComplete getCompleteTransaction() throws Exception {
        if (completeTransaction == null){
            completeTransaction = new WebpayComplete(mode, commerceCode, signature);
        }
        return completeTransaction;
    }
    
    public synchronized WebpayCapture getCaptureTransaction() throws Exception {
        if (captureTransaction == null){
            captureTransaction = new WebpayCapture(mode, commerceCode, signature);
        }
        return captureTransaction;
    }
    
    public synchronized WebpayNullify getNullifyTransaction() throws Exception {
        if (nullifyTransaction == null){
            nullifyTransaction = new WebpayNullify(mode, commerceCode, signature);
        }
        return nullifyTransaction;
    }
    
}
