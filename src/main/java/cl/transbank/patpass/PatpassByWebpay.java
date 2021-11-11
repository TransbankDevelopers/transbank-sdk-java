package cl.transbank.patpass;

import cl.transbank.common.*;
import cl.transbank.model.Options;
import cl.transbank.patpass.model.PatpassOptions;
import cl.transbank.webpay.common.WebpayOptions;

public class PatpassByWebpay {
    private static Options options;

    public static class Transaction extends PatpassByWebpayTransaction {
        public Transaction() {
            this.options = PatpassByWebpay.options!=null ? PatpassByWebpay.options : new WebpayOptions(IntegrationCommerceCodes.PATPASS_BY_WEBPAY, IntegrationApiKeys.WEBPAY, IntegrationType.TEST);
        }
        public Transaction(Options options) {
            this.options = options;
        }

    }

    /*
    |--------------------------------------------------------------------------
    | Environment Configuration
    |--------------------------------------------------------------------------
    */

    public static void configureForIntegration(String commerceCode, String apiKey){
        PatpassByWebpay.options = new PatpassOptions(commerceCode, apiKey, IntegrationType.TEST);
    }

    public static void configureForProduction(String commerceCode, String apiKey){
        PatpassByWebpay.options = new PatpassOptions(commerceCode, apiKey, IntegrationType.LIVE);
    }

    public static void configureForTesting(){
        configureForIntegration(IntegrationCommerceCodes.PATPASS_COMERCIO, IntegrationApiKeys.PATPASS_COMERCIO);
    }

    public static void configureForTestingDeferred(){
        configureForIntegration(IntegrationCommerceCodes.PATPASS_COMERCIO, IntegrationApiKeys.PATPASS_COMERCIO);
    }

    public static void configureForMock(){
        PatpassByWebpay.options = new PatpassOptions(IntegrationCommerceCodes.PATPASS_COMERCIO, IntegrationApiKeys.PATPASS_COMERCIO, IntegrationType.SERVER_MOCK);
    }
}
