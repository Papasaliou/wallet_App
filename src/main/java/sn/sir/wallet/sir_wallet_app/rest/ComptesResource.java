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
import sn.sir.wallet.sir_wallet_app.model.ComptesDTO;
import sn.sir.wallet.sir_wallet_app.service.ComptesService;


@RestController
@RequestMapping(value = "/api/comptess", produces = MediaType.APPLICATION_JSON_VALUE)
public class ComptesResource {

    private final ComptesService comptesService;

    public ComptesResource(final ComptesService comptesService) {
        this.comptesService = comptesService;
    }

    @GetMapping
    public ResponseEntity<List<ComptesDTO>> getAllComptess() {
        return ResponseEntity.ok(comptesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComptesDTO> getComptes(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(comptesService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createComptes(@RequestBody @Valid final ComptesDTO comptesDTO) {
        final Long createdId = comptesService.create(comptesDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateComptes(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ComptesDTO comptesDTO) {
        comptesService.update(id, comptesDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteComptes(@PathVariable(name = "id") final Long id) {
        comptesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
