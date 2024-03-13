package cl.transbank.patpass.requests;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * This class represents a request to start an inscription for a Patpass Comercio transaction.
 */
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class PatpassComercioInscriptionStartRequest extends WebpayApiRequest {

    @NonNull private String url;
    @SerializedName("nombre")  private String nombre;
    @SerializedName("pApellido")  private String pApellido;
    @SerializedName("sApellido")  private String sApellido;
    @SerializedName("rut")  private String rut;
    @SerializedName("serviceId") @NonNull private String serviceId;
    @SerializedName("finalUrl") @NonNull private String finalUrl;
    @SerializedName("commerceCode") @NonNull private String commerceCode;
    @SerializedName("montoMaximo")  private Double montoMaximo;
    @SerializedName("telefonoFijo")  private String telefonoFijo;
    @SerializedName("telefonoCelular")  private String telefonoCelular;
    @SerializedName("nombrePatPass")  private String nombrePatPass;
    @SerializedName("correoPersona")  private String correoPersona;
    @SerializedName("correoComercio")  private String correoComercio;
    @SerializedName("direccion")  private String direccion;
    @SerializedName("ciudad")  private String ciudad;

}
