package sn.sir.wallet.sir_wallet_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.sir.wallet.sir_wallet_app.domain.Comptes;
import sn.sir.wallet.sir_wallet_app.domain.Transactions;


public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    Transactions findFirstByComptes(Comptes comptes);

    Transactions findFirstByComptess(Comptes comptes);

}
