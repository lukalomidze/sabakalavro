package ge.edu.cu.l_lomidze2.sabakalavro.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    String token;
}
