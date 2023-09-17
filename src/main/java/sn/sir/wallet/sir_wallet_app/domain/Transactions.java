package sn.sir.wallet.sir_wallet_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import sn.sir.wallet.sir_wallet_app.model.TypeTransaction;


@Entity
@Getter
@Setter
public class Transactions {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TypeTransaction type;

    @Column
    private Double montant;

    @Column
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comptes_id")
    private Comptes comptes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comptess_id")
    private Comptes comptess;

}
