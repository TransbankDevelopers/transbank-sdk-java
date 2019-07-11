package cl.transbank.webpay.webpayplus;

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

    public <T> T buildResponse(T dest) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return buildResponse(dest, null);
    }

    public <T> T buildResponse(T dest, Object source) throws IntrospectionException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        if (null == source)
            source = this;

        final PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(dest.getClass()).getPropertyDescriptors();
        for (PropertyDescriptor pd : propertyDescriptors) {
            if (null != pd.getReadMethod() && null != pd.getWriteMethod()) {
                PropertyDescriptor origin = null;
                try {origin = new PropertyDescriptor(pd.getName(), source.getClass());} catch (Exception e) {}
                if (null != origin) {
                    final Object originValue = origin.getReadMethod().invoke(source);
                    if (pd.getPropertyType() == origin.getPropertyType()) {
                        if (originValue instanceof Collection) {
                            final Type type = pd.getReadMethod().getGenericReturnType();
                            Class destType = null;
                            if (type instanceof ParameterizedType) {
                                ParameterizedType pType = (ParameterizedType) type;
                                final Type[] typeArguments = pType.getActualTypeArguments();
                                if (null != typeArguments && typeArguments.length > 0 && typeArguments[0] instanceof Class)
                                    destType = (Class) typeArguments[0];
                            }

                            if (null != destType) {
                                Collection c = (Collection) originValue;
                                Collection listDest = new ArrayList();
                                for (Object o : c) {
                                    listDest.add(buildResponse(destType.getDeclaredConstructor(dest.getClass()).newInstance(dest), o));
                                }
                                pd.getWriteMethod().invoke(dest, listDest);
                            }
                        } else {
                            pd.getWriteMethod().invoke(dest, originValue);
                        }
                    } else {
                        final Object destValue = pd.getPropertyType().newInstance();
                        pd.getWriteMethod().invoke(dest, buildResponse(destValue, originValue));
                    }
                }
            }
        }

        return dest;
    }
}
