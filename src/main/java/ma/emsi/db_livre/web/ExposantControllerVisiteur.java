package ma.emsi.db_livre.web;

import ma.emsi.db_livre.entities.Exposant;
import ma.emsi.db_livre.entities.User;
import ma.emsi.db_livre.repositories.ExposantRepositoryVisiteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ExposantControllerVisiteur {

    @Autowired
    ExposantRepositoryVisiteur exposantRepositoryVisiteur;

    @GetMapping(path = "/listExposantss")
    public String exposants(Model model,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "5") int size,
                            @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        Page<Exposant> pageExposants = exposantRepositoryVisiteur.findByNomContains(keyword, PageRequest.of(page, size));
        List<Exposant> exposants = pageExposants.getContent();

        for (Exposant exposant : exposants) {

            if (exposant.getNom() == null || exposant.getNom().isEmpty()) {
                exposant.setNom("Indisponible");
            }
            if (exposant.getPays() == null || exposant.getPays().isEmpty()) {
                exposant.setPays("Indisponible");
            }
            if (exposant.getTelephone() == null || exposant.getTelephone().isEmpty()) {
                exposant.setTelephone("Indisponible");
            }
            if (exposant.getSiteWeb() == null || exposant.getSiteWeb().isEmpty()) {
                exposant.setSiteWeb("Indisponible");
            }
            if (exposant.getAdresse() == null || exposant.getAdresse().isEmpty()) {
                exposant.setAdresse("Indisponible");
            }
            if (exposant.getResponsableSalle() == null || exposant.getResponsableSalle().isEmpty()) {
                exposant.setResponsableSalle("Indisponible");
            }
            if (exposant.getResponsable() == null || exposant.getResponsable().isEmpty()) {
                exposant.setResponsable("Indisponible");
            }
            if (exposant.getSpecialite() == null || exposant.getSpecialite().isEmpty()) {
                exposant.setSpecialite("Indisponible");
            }
            if (exposant.getLocalisation() == null || exposant.getLocalisation().isEmpty()) {
                exposant.setLocalisation("Indisponible");
            }
        }

        model.addAttribute("listExposantss", exposants);
        model.addAttribute("pages", new int[pageExposants.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "exposantsVisiteur";
    }


    @GetMapping("/exposantdetailss/{id}")
    public String exposantDetails(@PathVariable(name = "id") Long id, Model model) {
        // Récupérer l'exposant à partir de l'id
        Exposant exposant = exposantRepositoryVisiteur.findById(id).orElse(null);

        // Récupérer l'utilisateur associé à l'exposant (si vous avez une relation entre User et Exposant)
        User user = exposant.getUser();

        // Ajouter l'exposant et l'utilisateur au modèle pour les afficher dans la page "exposantdetails.html"
        model.addAttribute("exposant", exposant);
        model.addAttribute("user", user);

        // Rediriger vers la page "exposantdetails.html"
        return "exposantdetailss";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/home")
    public String index() {
        return "home";
    }

}
