package com.jty.eduservice.controller;

import com.jty.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {
    //login
    @PostMapping("login")
    public R login() {
        return R.ok().data("token","admin");
    }
    //info
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","manager").data("name","admin").data("avatar","https://i.bmp.ovh/imgs/2022/04/04/663234ecff472a38.jpg");
    }
}
