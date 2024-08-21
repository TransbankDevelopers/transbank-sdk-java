package webpayplus;

import cl.transbank.webpay.webpayplus.responses.WebpayPlusMallTransactionStatusResponse;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WebpayPlusMallTestBase extends TestBase {
    protected String generateCommitJsonResponse(){
        Map<String, String> mapResponseCardDetail = new HashMap<String, String>();
        mapResponseCardDetail.put("card_number", "6623");

        Map<String, Object> mapResponseDetail1 = new HashMap<String, Object>();
        mapResponseDetail1.put("amount", 1000d);
        mapResponseDetail1.put("status", "AUTHORIZED");
        mapResponseDetail1.put("authorization_code", "1213");
        mapResponseDetail1.put("payment_type_code", "VN");
        mapResponseDetail1.put("response_code", (byte)0);
        mapResponseDetail1.put("installments_number", (byte)0);
        mapResponseDetail1.put("commerce_code", "597055555536");
        mapResponseDetail1.put("buy_order", "500894028");

        Map<String, Object> mapResponseDetail2 = new HashMap<String, Object>();
        mapResponseDetail2.put("amount", 2000d);
        mapResponseDetail2.put("status", "AUTHORIZED");
        mapResponseDetail2.put("authorization_code", "1213");
        mapResponseDetail2.put("payment_type_code", "VN");
        mapResponseDetail2.put("response_code", (byte)0);
        mapResponseDetail2.put("installments_number", (byte)0);
        mapResponseDetail2.put("commerce_code", "597055555537");
        mapResponseDetail2.put("buy_order", "1936357040");

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("vci", "TSY");
        mapResponse.put("buy_order", "1643997337");
        mapResponse.put("session_id", "1134425622");
        mapResponse.put("card_detail", mapResponseCardDetail);
        mapResponse.put("accounting_date", "0731");
        mapResponse.put("transaction_date", "2021-07-31T23:31:14.249Z");
        //details
        List<Map<String, Object>> lstDetail = new ArrayList<Map<String, Object>>();
        lstDetail.add(mapResponseDetail1);
        lstDetail.add(mapResponseDetail2);
        mapResponse.put("details", lstDetail);

        Gson gson = new GsonBuilder().create();
        return gson.toJson(mapResponse);
    }
    protected WebpayPlusMallTransactionStatusResponse generateStatusResponse() {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        return gson.fromJson(generateCommitJsonResponse(), WebpayPlusMallTransactionStatusResponse.class);
    }





}
