package webpayplus;

import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionStatusResponse;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;

public abstract class WebpayPlusTestBase extends TestBase {
    protected String generateCommitJsonResponse(){
        Map<String, String> mapResponseDetail = new HashMap<String, String>();
        mapResponseDetail.put("card_number", "6623");

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("vci", "TSY");
        mapResponse.put("amount", 1000d);
        mapResponse.put("status", "AUTHORIZED");
        mapResponse.put("buy_order", "1643997337");
        mapResponse.put("session_id", "1134425622");
        mapResponse.put("card_detail", mapResponseDetail);
        mapResponse.put("accounting_date", "0731");
        mapResponse.put("transaction_date", "2021-07-31T23:31:14.249Z");
        mapResponse.put("authorization_code", "1213");
        mapResponse.put("payment_type_code", "VD");
        mapResponse.put("response_code", (byte)0);
        mapResponse.put("installments_number", (byte)0);

        Gson gson = new GsonBuilder().create();
        return gson.toJson(mapResponse);
    }
    protected WebpayPlusTransactionStatusResponse generateStatusResponse() {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        return gson.fromJson(generateCommitJsonResponse(), WebpayPlusTransactionStatusResponse.class);
    }

}
