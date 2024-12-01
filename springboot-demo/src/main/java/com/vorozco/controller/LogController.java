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
        logger.trace("Trace level");
        logger.debug("Debug level");
        logger.info("Info level");
        logger.warn("Warn level");
        logger.error("Error level");
        return "Logs have been generated";
    }
}
