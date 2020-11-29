package com.hades.eduservice.controller;

import com.hades.commonutils.R;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("eduservice/user")
@CrossOrigin
public class EduLoginController {


    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }
}
