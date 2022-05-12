package com.jty.eduservice.service.impl;

import com.jty.eduservice.service.ClientService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ClientServiceImpl implements ClientService {


    public String[][] header_ContentType;
    public String[][] header_ContentType_AllowOrigin;

    public ClientServiceImpl() {
        header_ContentType = new String[1][2];
        header_ContentType[0][0] = "Content-Type";
        header_ContentType[0][1] = "application/json; charset=utf-8";
        header_ContentType_AllowOrigin = new String[2][2];
        header_ContentType_AllowOrigin[0][0] = "Content-Type";
        header_ContentType_AllowOrigin[0][1] = "application/json; charset=utf-8";
        header_ContentType_AllowOrigin[1][0] = "Access-Control-Allow-Origin";
        header_ContentType_AllowOrigin[1][1] = "*";
    }

    @Override
    public String getUPInfoByID(String mid) {
        String url = "https://api.bilibili.com/x/space/acc/info?mid="+mid;
        return getString(url, header_ContentType);
    }

    @Override
    public String searchAll(String keyword) {
        String url = "http://api.bilibili.com/x/web-interface/search/all/v2?" +
        "__refresh__=true&from_spmid=333.337&platform=pc&highlight=1&single_column=0" +
        "&keyword="+ keyword +
        "&order_sort=0&user_type=0&dynamic_offset=0&preload=true&com2co=true";
        return getString(url, header_ContentType);
    }

    @Override
    public String searchByType(String type, String keyword) {
        String url = "http://api.bilibili.com/x/web-interface/search/type?" +
                "&keyword="+ keyword +"&search_type="+type;
        return getString(url, header_ContentType);
    }

    @Override
    public String getVideoSrc(String bvid,String cid) {
        //fnval=1:mp4,fnval=0(默认):flv,fnval=16:dash
        String url = "http://api.bilibili.com/x/player/playurl?"+
                "bvid="+bvid+"&cid="+cid+
                "&fnval=1";
        return getString(url, header_ContentType_AllowOrigin);
    }

    @Override
    public String getVideoInfo(String bvid) {
        String url = "http://api.bilibili.com/x/web-interface/view?"+
                "bvid="+bvid;
        return getString(url, header_ContentType);
    }

    @Override
    public String getVideoListByUP(String mid,long page,long limit) {
        String url ="https://api.bilibili.com/x/space/arc/search?"+
                "mid="+mid+
                "&order=pubdate&pn="+page+"&ps="+limit;
        return getString(url, header_ContentType);
    }

    public String getString(String url,String[][] header) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        for (String[] i : header) {
            request.setHeader(i[0],i[1]);
        }
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            System.out.println("===================================================");
            System.out.println("response.StatusCode="+response.getStatusLine().getStatusCode());
            System.out.println("===================================================");
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                return EntityUtils.toString(httpEntity, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
