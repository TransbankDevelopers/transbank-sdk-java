package cl.transbank.webpay.oneclick;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.BaseTransaction;
import cl.transbank.model.Options;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.exception.InscriptionDeleteException;
import cl.transbank.webpay.exception.InscriptionFinishException;
import cl.transbank.webpay.exception.InscriptionStartException;
import cl.transbank.webpay.oneclick.requests.InscriptionDeleteRequest;
import cl.transbank.webpay.oneclick.requests.InscriptionStartRequest;
import cl.transbank.webpay.oneclick.responses.OneclickMallInscriptionFinishResponse;
import cl.transbank.webpay.oneclick.responses.OneclickMallInscriptionStartResponse;

import java.io.IOException;

public class
OneclickMallInscription extends BaseTransaction {
    public OneclickMallInscription(Options options){
        this.options = options;
    }

    public OneclickMallInscriptionStartResponse start(String username, String email, String responseUrl) throws IOException, InscriptionStartException {
        final WebpayApiRequest request = new InscriptionStartRequest(username, email, responseUrl);
        String endpoint = String.format("%s/inscriptions", ApiConstants.ONECLICK_ENDPOINT);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, OneclickMallInscriptionStartResponse.class);
        } catch (TransbankException e) {
            throw new InscriptionStartException(e);
        }
    }

    public OneclickMallInscriptionFinishResponse finish(String token) throws IOException, InscriptionFinishException {
        String endpoint = String.format("%s/inscriptions/%s", ApiConstants.ONECLICK_ENDPOINT, token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, options, OneclickMallInscriptionFinishResponse.class);
        } catch (TransbankException e) {
            throw new InscriptionFinishException(e);
        }
    }

    public void delete(String username, String tbkUser) throws IOException, InscriptionDeleteException {
        WebpayApiRequest request = new InscriptionDeleteRequest(username, tbkUser);
        String endpoint = String.format("%s/inscriptions", ApiConstants.ONECLICK_ENDPOINT);
        try {
            WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.DELETE, request, options);
        } catch (TransbankException e) {
            throw new InscriptionDeleteException(e);
        }
    }
}
