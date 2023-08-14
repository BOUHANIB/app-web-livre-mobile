package ma.emsi.db_livre.web;

import jakarta.validation.Valid;
import ma.emsi.db_livre.entities.Livre;
import ma.emsi.db_livre.repositories.ExposantRepositoryVisiteur;
import ma.emsi.db_livre.repositories.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LivreController {

    @Autowired
    LivreRepository livreRepository;

    @GetMapping(path = "/listLivres")
    public String livres(Model model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "5") int size,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Livre> pageLivres = livreRepository.findByTitreContains(keyword, PageRequest.of(page, size));
        List<Livre> livres = pageLivres.getContent();

        // Pour chaque livre, vérifier les champs et attribuer "Indisponible" si nécessaire
        for (Livre livre : livres) {
            if (livre.getTitre() == null || livre.getTitre().isEmpty()) {
                livre.setTitre("Indisponible");
            }
            if (livre.getAuteur() == null || livre.getAuteur().isEmpty()) {
                livre.setAuteur("Indisponible");
            }
            if (livre.getEditeur() == null || livre.getEditeur().isEmpty()) {
                livre.setEditeur("Indisponible");
            }
            // Vous pouvez faire de même pour les autres champs si nécessaire
        }

        model.addAttribute("listLivres", livres);
        model.addAttribute("pages", new int[pageLivres.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "livres";
    }



    @GetMapping("/livredetails")
    public String livreDetails(@RequestParam(name = "id") Long id, Model model) {
        Livre livre = livreRepository.findById(id).orElse(null);
        if (livre == null) {
            return "redirect:/listLivres";
        }
        model.addAttribute("livre", livre);
        return "livredetails";
    }

}
