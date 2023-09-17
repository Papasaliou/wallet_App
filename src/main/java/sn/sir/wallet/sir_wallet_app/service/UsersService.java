package sn.sir.wallet.sir_wallet_app.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.sir.wallet.sir_wallet_app.domain.Comptes;
import sn.sir.wallet.sir_wallet_app.domain.Users;
import sn.sir.wallet.sir_wallet_app.model.UsersDTO;
import sn.sir.wallet.sir_wallet_app.repos.ComptesRepository;
import sn.sir.wallet.sir_wallet_app.repos.UsersRepository;
import sn.sir.wallet.sir_wallet_app.util.NotFoundException;


@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final ComptesRepository comptesRepository;

    public UsersService(final UsersRepository usersRepository,
            final ComptesRepository comptesRepository) {
        this.usersRepository = usersRepository;
        this.comptesRepository = comptesRepository;
    }

    public List<UsersDTO> findAll() {
        final List<Users> userss = usersRepository.findAll(Sort.by("id"));
        return userss.stream()
                .map(users -> mapToDTO(users, new UsersDTO()))
                .toList();
    }

    public UsersDTO get(final Long id) {
        return usersRepository.findById(id)
                .map(users -> mapToDTO(users, new UsersDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UsersDTO usersDTO) {
        final Users users = new Users();
        mapToEntity(usersDTO, users);
        return usersRepository.save(users).getId();
    }

    public void update(final Long id, final UsersDTO usersDTO) {
        final Users users = usersRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usersDTO, users);
        usersRepository.save(users);
    }

    public void delete(final Long id) {
        usersRepository.deleteById(id);
    }

    private UsersDTO mapToDTO(final Users users, final UsersDTO usersDTO) {
        usersDTO.setId(users.getId());
        usersDTO.setPrenom(users.getPrenom());
        usersDTO.setNom(users.getNom());
        usersDTO.setCNI(users.getCNI());
        usersDTO.setTelephone(users.getTelephone());
        usersDTO.setComptes(users.getComptes() == null ? null : users.getComptes().getId());
        return usersDTO;
    }

    private Users mapToEntity(final UsersDTO usersDTO, final Users users) {
        users.setPrenom(usersDTO.getPrenom());
        users.setNom(usersDTO.getNom());
        users.setCNI(usersDTO.getCNI());
        users.setTelephone(usersDTO.getTelephone());
        final Comptes comptes = usersDTO.getComptes() == null ? null : comptesRepository.findById(usersDTO.getComptes())
                .orElseThrow(() -> new NotFoundException("comptes not found"));
        users.setComptes(comptes);
        return users;
    }

    public boolean comptesExists(final Long id) {
        return usersRepository.existsByComptesId(id);
    }

}
