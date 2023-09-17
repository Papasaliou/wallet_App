package sn.sir.wallet.sir_wallet_app.model;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ComptesDTO {

    private Long id;
    private Double solde;
    private LocalDate dateOuverture;

}
