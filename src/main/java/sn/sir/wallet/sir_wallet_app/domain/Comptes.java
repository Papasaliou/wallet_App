package sn.sir.wallet.sir_wallet_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Comptes {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double solde;

    @Column
    private LocalDate dateOuverture;

    @OneToOne(mappedBy = "comptes", fetch = FetchType.LAZY)
    private Users users;

    @OneToMany(mappedBy = "comptes")
    private Set<Transactions> transactions;

    @OneToMany(mappedBy = "comptess")
    private Set<Transactions> transactionss;

}
