package cl.transbank.patpass;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.BaseTransaction;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.patpass.requests.PatpassComercioTransactionStatusRequest;
import cl.transbank.patpass.responses.PatpassComercioInscriptionStartResponse;
import cl.transbank.patpass.requests.PatpassComercioInscriptionStartRequest;
import cl.transbank.patpass.responses.PatpassComercioTransactionStatusResponse;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.exception.InscriptionStartException;
import cl.transbank.webpay.exception.TransactionStatusException;

import java.io.IOException;

public class PatpassComercioInscription extends BaseTransaction {

    public PatpassComercioInscriptionStartResponse start(String url,
                                                                String name,
                                                                String firstLastName,
                                                                String secondLastName,
                                                                String rut,
                                                                String serviceId,
                                                                String finalUrl,
                                                                Double maxAmount,
                                                                String phoneNumber,
                                                                String mobileNumber,
                                                                String patpassName,
                                                                String personEmail,
                                                                String commerceEmail,
                                                                String address,
                                                                String city) throws IOException, InscriptionStartException {

        String endpoint = String.format("%s/patInscription", ApiConstants.PATPASS_COMERCIO_ENDPOINT);
        final WebpayApiRequest request = new PatpassComercioInscriptionStartRequest(url, name, firstLastName, secondLastName, rut, serviceId, finalUrl, options.getCommerceCode(), maxAmount,
                phoneNumber, mobileNumber, patpassName, personEmail, commerceEmail, address, city);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, PatpassComercioInscriptionStartResponse.class);
        } catch (TransbankException e) {
            throw new InscriptionStartException(e);
        }
    }

    public PatpassComercioTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
        String endpoint = String.format("%s/status", ApiConstants.PATPASS_COMERCIO_ENDPOINT);
        final WebpayApiRequest request = new PatpassComercioTransactionStatusRequest(token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, PatpassComercioTransactionStatusResponse.class);
        } catch (TransbankException e) {
            throw new TransactionStatusException(e);
        }
    }

}
