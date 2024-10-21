package com.cdss4pcp.rulemodificationservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DocsController {


    @RequestMapping(path = "/", method = {RequestMethod.GET})
    public String home() {
        return "home";
    }
}
