package cl.transbank.patpass.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the response of starting an inscription in Patpass Comercio.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatpassComercioInscriptionStartResponse {

  private String token;
  private String url;
}
