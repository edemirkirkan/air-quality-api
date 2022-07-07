package com.edemirkirkan.airqualityapi.gen.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {
    @RequestMapping("/index")
    public String home() {
        return "index";
    }
}