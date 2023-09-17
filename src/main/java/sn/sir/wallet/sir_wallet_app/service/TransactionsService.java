package sn.sir.wallet.sir_wallet_app.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.sir.wallet.sir_wallet_app.domain.Comptes;
import sn.sir.wallet.sir_wallet_app.domain.Transactions;
import sn.sir.wallet.sir_wallet_app.model.TransactionsDTO;
import sn.sir.wallet.sir_wallet_app.repos.ComptesRepository;
import sn.sir.wallet.sir_wallet_app.repos.TransactionsRepository;
import sn.sir.wallet.sir_wallet_app.util.NotFoundException;


@Service
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;
    private final ComptesRepository comptesRepository;

    public TransactionsService(final TransactionsRepository transactionsRepository,
            final ComptesRepository comptesRepository) {
        this.transactionsRepository = transactionsRepository;
        this.comptesRepository = comptesRepository;
    }

    public List<TransactionsDTO> findAll() {
        final List<Transactions> transactionss = transactionsRepository.findAll(Sort.by("id"));
        return transactionss.stream()
                .map(transactions -> mapToDTO(transactions, new TransactionsDTO()))
                .toList();
    }

    public TransactionsDTO get(final Long id) {
        return transactionsRepository.findById(id)
                .map(transactions -> mapToDTO(transactions, new TransactionsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TransactionsDTO transactionsDTO) {
        final Transactions transactions = new Transactions();
        mapToEntity(transactionsDTO, transactions);
        return transactionsRepository.save(transactions).getId();
    }

    public void update(final Long id, final TransactionsDTO transactionsDTO) {
        final Transactions transactions = transactionsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(transactionsDTO, transactions);
        transactionsRepository.save(transactions);
    }

    public void delete(final Long id) {
        transactionsRepository.deleteById(id);
    }

    private TransactionsDTO mapToDTO(final Transactions transactions,
            final TransactionsDTO transactionsDTO) {
        transactionsDTO.setId(transactions.getId());
        transactionsDTO.setType(transactions.getType());
        transactionsDTO.setMontant(transactions.getMontant());
        transactionsDTO.setDate(transactions.getDate());
        transactionsDTO.setComptes(transactions.getComptes() == null ? null : transactions.getComptes().getId());
        transactionsDTO.setComptess(transactions.getComptess() == null ? null : transactions.getComptess().getId());
        return transactionsDTO;
    }

    private Transactions mapToEntity(final TransactionsDTO transactionsDTO,
            final Transactions transactions) {
        transactions.setType(transactionsDTO.getType());
        transactions.setMontant(transactionsDTO.getMontant());
        transactions.setDate(transactionsDTO.getDate());
        final Comptes comptes = transactionsDTO.getComptes() == null ? null : comptesRepository.findById(transactionsDTO.getComptes())
                .orElseThrow(() -> new NotFoundException("comptes not found"));
        transactions.setComptes(comptes);
        final Comptes comptess = transactionsDTO.getComptess() == null ? null : comptesRepository.findById(transactionsDTO.getComptess())
                .orElseThrow(() -> new NotFoundException("comptess not found"));
        transactions.setComptess(comptess);
        return transactions;
    }

}
