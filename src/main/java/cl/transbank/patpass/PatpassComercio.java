package cl.transbank.patpass;

import cl.transbank.common.*;
import cl.transbank.model.Options;
import cl.transbank.patpass.model.PatpassOptions;

public class PatpassComercio  {
    private static Options options;

    public static class Inscription extends PatpassComercioInscription {
        public Inscription() {
            this.options = PatpassComercio.options!=null ? PatpassComercio.options : new PatpassOptions(IntegrationCommerceCodes.PATPASS_COMERCIO, IntegrationApiKeys.PATPASS_COMERCIO, IntegrationType.TEST);
        }

        public Inscription(Options options) {
            this.options = options;
        }

    }

    public static class Transaction extends PatpassComercioTransaction {
        public Transaction() {
            this.options = PatpassComercio.options!=null ? PatpassComercio.options : new PatpassOptions(IntegrationCommerceCodes.PATPASS_COMERCIO, IntegrationApiKeys.PATPASS_COMERCIO, IntegrationType.TEST);
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
        PatpassComercio.options = new PatpassOptions(commerceCode, apiKey, IntegrationType.TEST);
    }

    public static void configureForProduction(String commerceCode, String apiKey){
        PatpassComercio.options = new PatpassOptions(commerceCode, apiKey, IntegrationType.LIVE);
    }

    public static void configureForTesting(){
        configureForIntegration(IntegrationCommerceCodes.PATPASS_COMERCIO, IntegrationApiKeys.PATPASS_COMERCIO);
    }

    public static void configureForTestingDeferred(){
        configureForIntegration(IntegrationCommerceCodes.PATPASS_COMERCIO, IntegrationApiKeys.PATPASS_COMERCIO);
    }

    public static void configureForMock(){
        PatpassComercio.options = new PatpassOptions(IntegrationCommerceCodes.PATPASS_COMERCIO, IntegrationApiKeys.PATPASS_COMERCIO, IntegrationType.SERVER_MOCK);
    }
}
