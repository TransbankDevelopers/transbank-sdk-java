package cl.transbank;

import cl.transbank.transaccioncompleta.model.FullTransactionInstallmentResponse;
import cl.transbank.webpay.webpayplus.WebpayDetails;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Getter @Setter @ToString
public class WebpayApiResponseManager {
    @SerializedName("error_message") private String errorMessage;

    @SerializedName("token") private String token;
    @SerializedName("url") private String url;

    @SerializedName("vci") private String vci;
    @SerializedName("buy_order") private String buyOrder;
    @SerializedName("session_id") private String sessionId;
    @SerializedName("card_detail") private cl.transbank.webpay.webpayplus.CardDetail cardDetail;
    @SerializedName("accounting_date") private String accountingDate;
    @SerializedName("transaction_date") private String transactionDate;

    @SerializedName("details") private List<WebpayDetails> details;

    @SerializedName("amount") private double amount;
    @SerializedName("status") private String status;
    @SerializedName("authorization_code") private String authorizationCode;
    @SerializedName("payment_type_code") private String paymentTypeCode;
    @SerializedName("response_code") private byte responseCode;
    @SerializedName("installments_amount") private double installmentsAmount;
    @SerializedName("installments_number") private byte installmentsNumber;
    @SerializedName("balance") private double balance;

    @SerializedName("type") private String type;
    @SerializedName("authorization_date") private String authorizationDate;
    @SerializedName("nullified_amount") private double nullifiedAmount;

    @SerializedName("captured_amount") private double capturedAmount;

    @SerializedName("url_webpay") private String urlWebpay;

    @SerializedName("tbk_user") private String tbkUser;
    @SerializedName("card_type") private String cardType;
    @SerializedName("card_number") private String cardNumber;

    @SerializedName("id_query_installments") private Long idQueryInstallments;
    @SerializedName("deferred_periods") private List<FullTransactionInstallmentResponse.DeferredPeriod> deferredPeriods;

    @SerializedName("authorized") private boolean authorized;
    @SerializedName("voucherUrl") private String voucherUrl;



}
