package cl.transbank.onepay.util;

import cl.transbank.onepay.exception.SignatureException;
import cl.transbank.onepay.model.Options;
import cl.transbank.onepay.model.ShoppingCart;
import cl.transbank.onepay.net.GetTransactionNumberRequest;
import cl.transbank.onepay.net.NullifyTransactionRequest;
import cl.transbank.onepay.net.SendTransactionRequest;

public interface RequestBuilder {
    SendTransactionRequest buildSendTransactionRequest(ShoppingCart cart, String channel, Options options)
            throws SignatureException;
    GetTransactionNumberRequest buildGetTransactionNumberRequest(String occ, String externalUniqueNumber, Options options)
            throws SignatureException;
    NullifyTransactionRequest buildNullifyTransactionRequest(long amount, String occ, String externalUniqueNumber,
                                                             String authorizationCode, Options options) throws SignatureException;
}
