package com.jty.eduservice.controller;

import com.jty.commonutils.R;
import com.jty.eduservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/eduservice/bili")
@CrossOrigin
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("getUserInfoByID/{mid}")
    @ResponseBody
    public R getUPInfoByID(@PathVariable String mid){
        String msg = clientService.getUPInfoByID(mid);
        return R.ok().data("json",msg);
    }

    @GetMapping("searchAll/{keyword}")
    @ResponseBody
    public R searchAll(@PathVariable String keyword){
        String msg = clientService.searchAll(keyword);
        return R.ok().data("json",msg);
    }

    @GetMapping("searchByType/{type}/{keyword}")
    @ResponseBody
    public R searchByType(@PathVariable String type, @PathVariable String keyword){
        String msg = clientService.searchByType(type,keyword);
        return R.ok().data("json",msg);
    }

    @GetMapping("getVideoSrc/{bvid}/{cid}")
    @ResponseBody
    public R getVideoSrc(@PathVariable String bvid, @PathVariable String cid){
            String msg=clientService.getVideoSrc(bvid,cid);
            return R.ok().data("json",msg);
    }

    @GetMapping("getVideoInfo/{bvid}")
    @ResponseBody
    public R getVideoInfo(@PathVariable String bvid){
        String msg=clientService.getVideoInfo(bvid);
        return R.ok().data("json",msg);
    }

    @GetMapping("getVideoListByUP/{mid}/{page}/{limit}")
    @ResponseBody
    public R getVideoListByUP(@PathVariable String mid,@PathVariable long page,@PathVariable long limit){
        String msg=clientService.getVideoListByUP(mid,page,limit);
        return R.ok().data("json",msg);
    }
}

//    @GetMapping("getVideoSrc")
//    @ResponseBody
//    public String getVideoSrc(@PathVariable String url){
//        File file = new File(url);
//        FileInputStream input = new FileInputStream(file);
//        int i = input.available();
//        byte[] bytes = new byte[i];
//        input.read(bytes);
//        response.setContentType("application/video");
//        OutputStream output = response.getOutputStream();
//        output.write(bytes);
//        output.flush();
//        output.close();
//        input.close();
//    }
