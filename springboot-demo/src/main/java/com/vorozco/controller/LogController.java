package com.vorozco.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {

    Logger logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping
    public String doLog() {
        logger.trace("Nivel granular");
        logger.debug("Nivel desarrollo");
        logger.info("Nivel info");
        logger.warn("Nivel advertencia");
        logger.error("Nivel error");
        return "Los logs han sido generados";
    }
}
