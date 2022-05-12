package com.jty.eduservice.service;

public interface ClientService {
    String getUPInfoByID(String mid);
    String searchAll(String keyword);
    String searchByType(String type, String keyword);
    String getVideoSrc(String bvid, String cid);
    String getVideoInfo(String bvid);
    String getVideoListByUP(String mid,long page,long limit);
}
