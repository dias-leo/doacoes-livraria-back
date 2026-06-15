package br.unilasalle.livraria_doacoes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

    /**
     * Redirect /swagger-ui to /swagger-ui/index.html for convenience
     */
    @GetMapping("/swagger-ui")
    public String redirectSwagger() {
        return "redirect:/swagger-ui/index.html";
    }

    /**
     * Redirect /docs to /swagger-ui/index.html for convenience
     */
    @GetMapping("/docs")
    public String redirectDocs() {
        return "redirect:/swagger-ui/index.html";
    }
}

