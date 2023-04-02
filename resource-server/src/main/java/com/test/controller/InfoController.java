package com.test.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InfoController {
    @RequestMapping("/info")
    @ResponseBody
    public Authentication GetInfo(){
        SecurityContext context= SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication());
        return context.getAuthentication();
    }
}
