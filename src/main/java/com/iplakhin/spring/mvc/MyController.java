    package com.iplakhin.spring.mvc;


    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.RequestMapping;

    @Controller
    public class MyController {
        @RequestMapping("/")
        public String showLoginView() {
            return "login";
        }


        @RequestMapping("/home")
        public String showHomeView() {
            return "home";
        }
    }
