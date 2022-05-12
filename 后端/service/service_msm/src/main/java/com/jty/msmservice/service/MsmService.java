package com.jty.msmservice.service;

import java.util.Map;

public interface MsmService {
    //发送短信的方法
    boolean send(Map<String, Object> param, String phone);
    //发邮箱验证码
    boolean sendMail(String to, String code);
}
