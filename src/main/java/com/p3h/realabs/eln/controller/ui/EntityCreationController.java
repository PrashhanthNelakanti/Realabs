package com.p3h.realabs.eln.controller.ui;

import com.p3h.realabs.eln.model.Experiment;
import com.p3h.realabs.eln.model.Sample;
import com.p3h.realabs.eln.model.Storage;
import com.p3h.realabs.eln.service.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class EntityCreationController {

    private static final Logger logger = LoggerFactory.getLogger(EntityCreationController.class);

    @Autowired
    EntityService service;

    @GetMapping({"/create"})
    public String home(Model model, Principal principal) {
        logger.info("User logged in: {}", principal.getName());
        model.addAttribute("username", principal.getName());
        return "createEntity";
    }

    /*@PostMapping(
            value    = "/experiments",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Experiment saveExperiment(@RequestBody Experiment e) {
        return service.saveExperiment(e);
    }*/

    @PostMapping(value="/experiments", produces="application/json")
    @ResponseBody
    public Experiment saveExperiment(@ModelAttribute Experiment e) {
        return service.saveExperiment(e);
    }

    @PostMapping(value="/samples", produces="application/json")
    @ResponseBody
    public Sample saveSample(@ModelAttribute Sample s) {
        return service.saveSample(s);
    }

    @PostMapping(value="/storages", produces="application/json")
    @ResponseBody
    public Storage saveStorage(@ModelAttribute Storage s) {
        return service.saveStorage(s);
    }
}
