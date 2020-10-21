package cl.transbank.patpass;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class PatpassComercioInscriptionStartRequest extends WebpayApiRequest {

    @SerializedName("url") @NonNull private String url;
    @SerializedName("nombre")  private String name;
    @SerializedName("pApellido")  private String firstLastName;
    @SerializedName("sApellido")  private String secondLastName;
    @SerializedName("rut")  private String rut;
    @SerializedName("serviceId") @NonNull private String serviceId;
    @SerializedName("finalUrl") @NonNull private String finalUrl;
    @SerializedName("commerceCode") @NonNull private String commerceCode;
    @SerializedName("montoMaximo")  private Double maxAmount;
    @SerializedName("telefonoFijo")  private String phoneNumber;
    @SerializedName("telefonoCelular")  private String mobileNumber;
    @SerializedName("nombrePatPass")  private String patpassName;
    @SerializedName("correoPersona")  private String personEmail;
    @SerializedName("correoComercio")  private String commerceEmail;
    @SerializedName("direccion")  private String address;
    @SerializedName("ciudad")  private String city;

}
