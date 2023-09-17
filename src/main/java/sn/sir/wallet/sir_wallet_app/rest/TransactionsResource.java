package sn.sir.wallet.sir_wallet_app.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.sir.wallet.sir_wallet_app.model.TransactionsDTO;
import sn.sir.wallet.sir_wallet_app.service.TransactionsService;


@RestController
@RequestMapping(value = "/api/transactionss", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionsResource {

    private final TransactionsService transactionsService;

    public TransactionsResource(final TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionsDTO>> getAllTransactionss() {
        return ResponseEntity.ok(transactionsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionsDTO> getTransactions(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(transactionsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTransactions(
            @RequestBody @Valid final TransactionsDTO transactionsDTO) {
        final Long createdId = transactionsService.create(transactionsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTransactions(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final TransactionsDTO transactionsDTO) {
        transactionsService.update(id, transactionsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTransactions(@PathVariable(name = "id") final Long id) {
        transactionsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
