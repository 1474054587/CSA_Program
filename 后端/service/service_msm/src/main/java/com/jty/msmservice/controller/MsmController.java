package com.jty.msmservice.controller;

import com.jty.commonutils.R;
import com.jty.msmservice.service.MsmService;
import com.jty.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("sendMail/{mail}")
    public R sendMail(@PathVariable String mail) {
        //1 从redis获取验证码，如果获取到直接返回
        String code = stringRedisTemplate.opsForValue().get(mail);
        if(!StringUtils.isEmpty(code)) {
            return R.error().message("已经发送验证码，请查看邮箱。");
        }
        code = RandomUtil.getFourBitRandom();
        boolean isSend = msmService.sendMail(mail, code);
        if(isSend) {
            //发送成功，把发送成功验证码放到redis里面
            //设置有效时间
            stringRedisTemplate.opsForValue().set(mail,code,5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("验证码发送失败");
        }
    }

    //发送短信的方法
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        //1 从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        //2 如果redis获取 不到，进行阿里云发送
        //生成随机值，传递阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        //调用service发送短信的方法
        boolean isSend = msmService.send(param,phone);
        if(isSend) {
            //发送成功，把发送成功验证码放到redis里面
            //设置有效时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }
}
