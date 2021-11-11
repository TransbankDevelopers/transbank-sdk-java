package cl.transbank.patpass.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatpassComercioInscriptionStartResponse {
    private String token;
    private String url;
}
