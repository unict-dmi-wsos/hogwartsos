package wiz.hogwartsos.sortinghack.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import wiz.hogwartsos.sortinghack.models.langhouse;
import wiz.hogwartsos.sortinghack.models.wizardev;
import wiz.hogwartsos.sortinghack.repositories.langhouseRepository;
import wiz.hogwartsos.sortinghack.repositories.wizardevRepository;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class wizardevController {

    private final wizardevRepository repository;
    private final langhouseRepository lrepository;
    List<Integer> years = Arrays.asList(1,2,3,4,5,6,7);

    public wizardevController(wizardevRepository repository,langhouseRepository lrepository) {
        this.repository = repository;
        this.lrepository = lrepository;
    }


    @GetMapping("/wizardevs")
    public String read(Model model) {
        
        model.addAttribute("wizardevs", repository.findAll());
        model.addAttribute("years", years);
        return("wizardevs/list");
    }

    @GetMapping("/wizardevs/new")
    public String create(Model model) {
        model.addAttribute("wizardev", new wizardev());
        model.addAttribute("langhouses", lrepository.findAll());  ;      
        return("wizardevs/edit");
    }

    @GetMapping("/wizardevs/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("wizardev", repository.getReferenceById(id));
        model.addAttribute("langhouses", lrepository.findAll());        
        return("wizardevs/edit");
    }

    @GetMapping("/wizardevs/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        wizardev wizardev = repository.getReferenceById(id);
        repository.delete(wizardev);
        return("redirect:/wizardevs");
    }

    
    @PostMapping("/wizardevs")
    public String cr(@ModelAttribute wizardev wizardev,Model model) {
        repository.save(wizardev);
        return("redirect:/wizardevs");
    }

    @GetMapping("/wizardevs/langhouse/{langhouse_id}")
    public String searchByLanghouseId(Model model,@PathVariable Long langhouse_id) {
        langhouse langhouse = lrepository.getReferenceById(langhouse_id);
        model.addAttribute("wizardevs", repository.findByLanghouseId(langhouse));
        model.addAttribute("langhouse", langhouse);
        model.addAttribute("years", years);
        return("wizardevs/list");
    }
    
    @PostMapping("/wizardevs/findByYearGreaterThan")
    public String findByYearGreaterThan(Model model,@RequestParam Integer year) {
        model.addAttribute("wizardevs", repository.findByYearGreaterThan(year));
        model.addAttribute("years", years);
        return("wizardevs/list");
    }
    
    @PostMapping("/wizardevs/search")
    public String search(Model model, @RequestParam String search) {
        model.addAttribute("wizardevs", repository.findByNameContainingIgnoreCase(search));
        model.addAttribute("search", search);
        return("wizardevs/list");
    }
    
}
