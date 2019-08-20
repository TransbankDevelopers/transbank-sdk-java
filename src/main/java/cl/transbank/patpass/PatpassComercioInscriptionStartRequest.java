package cl.transbank.patpass;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class PatpassComercioInscriptionStartRequest extends WebpayApiRequest {

    @SerializedName("url") @NonNull private String buyOrder;
    @SerializedName("nombre") @NonNull private String name;
    @SerializedName("pApellido") @NonNull private String firstLastName;
    @SerializedName("sApellido") @NonNull private String secondLastName;
    @SerializedName("rut") @NonNull private String rut;
    @SerializedName("serviceId") @NonNull private String serviceId;
    @SerializedName("finalUrl") @NonNull private String finalUrl;
    @SerializedName("commerceCode") @NonNull private String commerceCode;
    @SerializedName("montoMaximo") @NonNull private int maxAmount;
    @SerializedName("telefonoFijo") @NonNull private String phoneNumber;
    @SerializedName("telefonoCelular") @NonNull private String mobileNumber;
    @SerializedName("nombrePatPass") @NonNull private String patpassName;
    @SerializedName("correoPersona") @NonNull private String personEmail;
    @SerializedName("correoComercio") @NonNull private String commerceEmail;
    @SerializedName("direccion") @NonNull private String address;
    @SerializedName("ciudad") @NonNull private String city;
}
