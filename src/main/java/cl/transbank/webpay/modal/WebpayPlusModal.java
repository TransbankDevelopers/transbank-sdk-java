package cl.transbank.webpay.modal;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.model.Options;
import cl.transbank.webpay.common.WebpayOptions;

public class WebpayPlusModal {
    private static Options options;

    public static class Transaction extends WebpayModalTransaction {
        public Transaction() {
            super(WebpayPlusModal.options!=null ? WebpayPlusModal.options : new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_MODAL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
        }
        public Transaction(Options options) {
            super(options);
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Environment Configuration
    |--------------------------------------------------------------------------
    */

    public static void configureForIntegration(String commerceCode, String apiKey){
        options = new WebpayOptions(commerceCode, apiKey, IntegrationType.TEST);
    }

    public static void configureForProduction(String commerceCode, String apiKey){
        options = new WebpayOptions(commerceCode, apiKey, IntegrationType.LIVE);
    }

    public static void configureForTesting(){
        configureForIntegration(IntegrationCommerceCodes.WEBPAY_PLUS_MODAL, IntegrationApiKeys.WEBPAY);
    }

    public static void configureForMock(){
        options = new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_MODAL, IntegrationApiKeys.WEBPAY, IntegrationType.SERVER_MOCK);
    }
}