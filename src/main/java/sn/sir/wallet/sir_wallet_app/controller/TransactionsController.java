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
import sn.sir.wallet.sir_wallet_app.model.TransactionsDTO;
import sn.sir.wallet.sir_wallet_app.model.TypeTransaction;
import sn.sir.wallet.sir_wallet_app.repos.ComptesRepository;
import sn.sir.wallet.sir_wallet_app.service.TransactionsService;
import sn.sir.wallet.sir_wallet_app.util.CustomCollectors;
import sn.sir.wallet.sir_wallet_app.util.WebUtils;


@Controller
@RequestMapping("/transactionss")
public class TransactionsController {

    private final TransactionsService transactionsService;
    private final ComptesRepository comptesRepository;

    public TransactionsController(final TransactionsService transactionsService,
            final ComptesRepository comptesRepository) {
        this.transactionsService = transactionsService;
        this.comptesRepository = comptesRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("typeValues", TypeTransaction.values());
        model.addAttribute("comptesValues", comptesRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Comptes::getId, Comptes::getId)));
        model.addAttribute("comptessValues", comptesRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Comptes::getId, Comptes::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("transactionss", transactionsService.findAll());
        return "transactions/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("transactions") final TransactionsDTO transactionsDTO) {
        return "transactions/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("transactions") @Valid final TransactionsDTO transactionsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "transactions/add";
        }
        transactionsService.create(transactionsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("transactions.create.success"));
        return "redirect:/transactionss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("transactions", transactionsService.get(id));
        return "transactions/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("transactions") @Valid final TransactionsDTO transactionsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "transactions/edit";
        }
        transactionsService.update(id, transactionsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("transactions.update.success"));
        return "redirect:/transactionss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        transactionsService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("transactions.delete.success"));
        return "redirect:/transactionss";
    }

}
