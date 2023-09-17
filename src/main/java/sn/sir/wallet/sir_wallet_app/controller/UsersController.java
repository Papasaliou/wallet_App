package sn.sir.wallet.sir_wallet_app.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.sir.wallet.sir_wallet_app.domain.Comptes;
import sn.sir.wallet.sir_wallet_app.model.UsersDTO;
import sn.sir.wallet.sir_wallet_app.repos.ComptesRepository;
import sn.sir.wallet.sir_wallet_app.service.UsersService;
import sn.sir.wallet.sir_wallet_app.util.CustomCollectors;
import sn.sir.wallet.sir_wallet_app.util.WebUtils;


@Controller
@RequestMapping("/userss")
public class UsersController {

    private final UsersService usersService;
    private final ComptesRepository comptesRepository;

    public UsersController(final UsersService usersService,
            final ComptesRepository comptesRepository) {
        this.usersService = usersService;
        this.comptesRepository = comptesRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("comptesValues", comptesRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Comptes::getId, Comptes::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("userss", usersService.findAll());
        return "users/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("users") final UsersDTO usersDTO) {
        return "users/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("users") @Valid final UsersDTO usersDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("comptes") && usersDTO.getComptes() != null && usersService.comptesExists(usersDTO.getComptes())) {
            bindingResult.rejectValue("comptes", "Exists.users.comptes");
        }
        if (bindingResult.hasErrors()) {
            return "users/add";
        }
        usersService.create(usersDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("users.create.success"));
        return "redirect:/userss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("users", usersService.get(id));
        return "users/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("users") @Valid final UsersDTO usersDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final UsersDTO currentUsersDTO = usersService.get(id);
        if (!bindingResult.hasFieldErrors("comptes") && usersDTO.getComptes() != null &&
                !usersDTO.getComptes().equals(currentUsersDTO.getComptes()) &&
                usersService.comptesExists(usersDTO.getComptes())) {
            bindingResult.rejectValue("comptes", "Exists.users.comptes");
        }
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        usersService.update(id, usersDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("users.update.success"));
        return "redirect:/userss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        usersService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("users.delete.success"));
        return "redirect:/userss";
    }

}
