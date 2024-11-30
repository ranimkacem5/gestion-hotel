package com.example.hotelversion2.Web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
    @RequestMapping(path = "/contact", method = RequestMethod.GET)
    public String ContactPage(@RequestParam String param) {
        return "contact";
    }
}
