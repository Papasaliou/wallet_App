package sn.sir.wallet.sir_wallet_app.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.sir.wallet.sir_wallet_app.domain.Comptes;
import sn.sir.wallet.sir_wallet_app.domain.Transactions;
import sn.sir.wallet.sir_wallet_app.domain.Users;
import sn.sir.wallet.sir_wallet_app.model.ComptesDTO;
import sn.sir.wallet.sir_wallet_app.repos.ComptesRepository;
import sn.sir.wallet.sir_wallet_app.repos.TransactionsRepository;
import sn.sir.wallet.sir_wallet_app.repos.UsersRepository;
import sn.sir.wallet.sir_wallet_app.util.NotFoundException;
import sn.sir.wallet.sir_wallet_app.util.WebUtils;


@Service
public class ComptesService {

    private final ComptesRepository comptesRepository;
    private final UsersRepository usersRepository;
    private final TransactionsRepository transactionsRepository;

    public ComptesService(final ComptesRepository comptesRepository,
            final UsersRepository usersRepository,
            final TransactionsRepository transactionsRepository) {
        this.comptesRepository = comptesRepository;
        this.usersRepository = usersRepository;
        this.transactionsRepository = transactionsRepository;
    }

    public List<ComptesDTO> findAll() {
        final List<Comptes> comptess = comptesRepository.findAll(Sort.by("id"));
        return comptess.stream()
                .map(comptes -> mapToDTO(comptes, new ComptesDTO()))
                .toList();
    }

    public ComptesDTO get(final Long id) {
        return comptesRepository.findById(id)
                .map(comptes -> mapToDTO(comptes, new ComptesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ComptesDTO comptesDTO) {
        final Comptes comptes = new Comptes();
        mapToEntity(comptesDTO, comptes);
        return comptesRepository.save(comptes).getId();
    }

    public void update(final Long id, final ComptesDTO comptesDTO) {
        final Comptes comptes = comptesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(comptesDTO, comptes);
        comptesRepository.save(comptes);
    }

    public void delete(final Long id) {
        comptesRepository.deleteById(id);
    }

    private ComptesDTO mapToDTO(final Comptes comptes, final ComptesDTO comptesDTO) {
        comptesDTO.setId(comptes.getId());
        comptesDTO.setSolde(comptes.getSolde());
        comptesDTO.setDateOuverture(comptes.getDateOuverture());
        return comptesDTO;
    }

    private Comptes mapToEntity(final ComptesDTO comptesDTO, final Comptes comptes) {
        comptes.setSolde(comptesDTO.getSolde());
        comptes.setDateOuverture(comptesDTO.getDateOuverture());
        return comptes;
    }

    public String getReferencedWarning(final Long id) {
        final Comptes comptes = comptesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Users comptesUsers = usersRepository.findFirstByComptes(comptes);
        if (comptesUsers != null) {
            return WebUtils.getMessage("comptes.users.comptes.referenced", comptesUsers.getId());
        }
        final Transactions comptesTransactions = transactionsRepository.findFirstByComptes(comptes);
        if (comptesTransactions != null) {
            return WebUtils.getMessage("comptes.transactions.comptes.referenced", comptesTransactions.getId());
        }
        final Transactions comptessTransactions = transactionsRepository.findFirstByComptess(comptes);
        if (comptessTransactions != null) {
            return WebUtils.getMessage("comptes.transactions.comptess.referenced", comptessTransactions.getId());
        }
        return null;
    }

}
