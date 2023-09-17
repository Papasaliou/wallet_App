package sn.sir.wallet.sir_wallet_app.model;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionsDTO {

    private Long id;
    private TypeTransaction type;
    private Double montant;
    private LocalDate date;
    private Long comptes;
    private Long comptess;

}
