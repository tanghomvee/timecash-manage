package com.timecash.manage;

import com.alibaba.fastjson.JSON;
import com.timecash.vo.Msg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: TODO
 * @Author:tanghongwei@jumaps.com
 * @Date:2019-12-05
 * @Copyright: 2019 juma.com All Rights Reserved
 */
@WebServlet(urlPatterns = {"/usr/login"})
public class UsrCtrl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        String pwd = req.getParameter("pwd"),usrName = req.getParameter("usrName");
        Msg msg = null;
        if (!"admin".equalsIgnoreCase(usrName) || ! "timecash".equals(pwd)){
            msg = Msg.err("账户密码错误");
       }else {
            msg = Msg.ok();
            req.getSession().setAttribute("USER" , usrName + "-" + pwd);
        }

        resp.getWriter().print(JSON.toJSONString(msg));
        resp.getWriter().flush();
        resp.getWriter().close();
    }

}
