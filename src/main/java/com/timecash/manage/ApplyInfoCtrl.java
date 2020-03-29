package com.timecash.manage;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.timecash.dao.DBUtil;
import com.timecash.vo.ApplyInfo;
import com.timecash.vo.Msg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author:tanghongwei@jumaps.com
 * @Date:2019-12-05
 * @Copyright: 2019 juma.com All Rights Reserved
 */
@WebServlet(urlPatterns = {"/applyInfo/add","/applyInfo/list"})
public class ApplyInfoCtrl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setHeader("cache-control", "no-cache");
        String path = req.getServletPath();
        String content = getContent(req.getInputStream());
        Msg msg = null;
        if ("/applyInfo/add".equalsIgnoreCase(path)){
            ApplyInfo applyInfo = JSONObject.toJavaObject(JSON.parseObject(content) , ApplyInfo.class);
            DBUtil.save(applyInfo);
            msg = Msg.ok();
        }else if("/applyInfo/list".equalsIgnoreCase(path)){
            String user = (String) req.getSession().getAttribute("USER");
            if (StringUtils.isEmpty(user)){
                msg = Msg.login();
            }else{
                String pageNum = req.getParameter("pageNum");
                try{
                    //Integer total =  DBUtil.getTotal();
                    List<ApplyInfo> infos = DBUtil.list(StringUtils.isEmpty(pageNum) ? 0 : Integer.valueOf(pageNum));
                    Map<String,Object> data = new HashMap<>();
                   // data.put("pages" , DBUtil.getPage(total));
                  //  data.put("total" , total);
                    data.put("data" ,infos);

                    msg = Msg.ok(data);
                }catch (Exception ex){
                    ex.printStackTrace();
                    msg = Msg.err();
                }
            }

        }else {
            msg = Msg.err("非法请求");
        }

        resp.getWriter().print(JSON.toJSONString(msg));
        resp.getWriter().flush();
        resp.getWriter().close();
    }

    private String getContent(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        //获取请求参数
        StringBuffer sb = new StringBuffer("");
        String temp=null;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        return sb.toString();
    }
}
