package sn.sir.wallet.sir_wallet_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.sir.wallet.sir_wallet_app.domain.Comptes;


public interface ComptesRepository extends JpaRepository<Comptes, Long> {
}
