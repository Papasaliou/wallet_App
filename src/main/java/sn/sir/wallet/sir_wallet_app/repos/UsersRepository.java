package sn.sir.wallet.sir_wallet_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.sir.wallet.sir_wallet_app.domain.Comptes;
import sn.sir.wallet.sir_wallet_app.domain.Users;


public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByComptesId(Long id);

    Users findFirstByComptes(Comptes comptes);

}
