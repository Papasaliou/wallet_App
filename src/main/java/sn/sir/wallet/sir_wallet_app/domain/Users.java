package sn.sir.wallet.sir_wallet_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Users {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String prenom;

    @Column
    private String nom;

    @Column
    private Long cNI;

    @Column
    private Long telephone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comptes_id", unique = true)
    private Comptes comptes;

}
