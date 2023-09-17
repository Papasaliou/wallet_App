package sn.sir.wallet.sir_wallet_app.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.sir.wallet.sir_wallet_app.model.ComptesDTO;
import sn.sir.wallet.sir_wallet_app.service.ComptesService;
import sn.sir.wallet.sir_wallet_app.util.WebUtils;


@Controller
@RequestMapping("/comptess")
public class ComptesController {

    private final ComptesService comptesService;

    public ComptesController(final ComptesService comptesService) {
        this.comptesService = comptesService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("comptess", comptesService.findAll());
        return "comptes/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("comptes") final ComptesDTO comptesDTO) {
        return "comptes/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("comptes") @Valid final ComptesDTO comptesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "comptes/add";
        }
        comptesService.create(comptesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("comptes.create.success"));
        return "redirect:/comptess";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("comptes", comptesService.get(id));
        return "comptes/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("comptes") @Valid final ComptesDTO comptesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "comptes/edit";
        }
        comptesService.update(id, comptesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("comptes.update.success"));
        return "redirect:/comptess";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = comptesService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            comptesService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("comptes.delete.success"));
        }
        return "redirect:/comptess";
    }

}
