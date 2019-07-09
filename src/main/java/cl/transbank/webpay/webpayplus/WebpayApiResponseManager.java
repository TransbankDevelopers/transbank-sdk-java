package cl.transbank.webpay.webpayplus;

import cl.transbank.webpay.webpayplus.model.CommitWebpayPlusMallTransactionResponse;
import cl.transbank.webpay.webpayplus.model.CreateWebpayPlusMallTransactionResponse;
import cl.transbank.webpay.webpayplus.model.CreateWebpayPlusTransactionResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter @ToString
class WebpayApiResponseManager {
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

    CommitWebpayPlusMallTransactionResponse buildCommitWebpayPlusMallTransactionResponse() {
        cl.transbank.webpay.model.CardDetail cardDetail = new cl.transbank.webpay.model.CardDetail(getCardDetail().getCardNumber());

        CommitWebpayPlusMallTransactionResponse response = new CommitWebpayPlusMallTransactionResponse();
        response.setVci(getVci());
        response.setAccountingDate(getAccountingDate());
        response.setBuyOrder(getBuyOrder());
        response.setCardDetail(cardDetail);
        response.setSessionId(getSessionId());
        response.setTransactionDate(getTransactionDate());

        final List<WebpayDetails> details = getDetails();
        if (null != details) {
            List<CommitWebpayPlusMallTransactionResponse.Detail> detailList = new ArrayList<>();
            for (WebpayDetails detail : details) {
                CommitWebpayPlusMallTransactionResponse.Detail d = response.new Detail();
                d.setAmount(detail.getAmount());
                d.setAuthorizationCode(detail.getAuthorizationCode());
                d.setBuyOrder(detail.getBuyOrder());
                d.setCommerceCode(detail.getCommerceCode());
                d.setInstallmentsNumber(detail.getInstallmentsNumber());
                d.setPaymentTypeCode(detail.getPaymentTypeCode());
                d.setResponseCode(detail.getResponseCode());
                d.setStatus(detail.getStatus());
                detailList.add(d);
            }

            response.setDetails(detailList);
        }

        return response;
    }

    CreateWebpayPlusTransactionResponse buildCreateWebpayPlusTransactionResponse() {
        CreateWebpayPlusTransactionResponse response = new CreateWebpayPlusTransactionResponse();
        response.setToken(getToken());
        response.setUrl(getUrl());

        return response;
    }

    CreateWebpayPlusMallTransactionResponse buildCreateWebpayPlusMallTransactionResponse() {
        CreateWebpayPlusMallTransactionResponse response = new CreateWebpayPlusMallTransactionResponse();
        response.setToken(getToken());
        response.setUrl(getUrl());

        return response;
    }
}
