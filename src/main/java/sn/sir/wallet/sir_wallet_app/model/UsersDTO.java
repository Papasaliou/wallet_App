package sn.sir.wallet.sir_wallet_app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsersDTO {

    private Long id;

    @Size(max = 255)
    private String prenom;

    @Size(max = 255)
    private String nom;

    @JsonProperty("cNI")
    private Long cNI;

    private Long telephone;

    private Long comptes;

}
