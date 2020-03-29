package com.timecash.dao;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.timecash.vo.ApplyInfo;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Description: TODO
 * @Author:tanghongwei@jumaps.com
 * @Date:2019-12-05
 * @Copyright: 2019 juma.com All Rights Reserved
 */
public class DBUtil {

    private static DataSource dataSource = null;
    private static final Integer PAGE_SIZE = 15;
    static {
        InputStream inputStream =  DBUtil.class.getResourceAsStream("/db.properties");
         Properties pp = new Properties();
        try {
            pp.load(inputStream);
            // 创建连接池，使用配置文件中的参数
            dataSource = DruidDataSourceFactory.createDataSource(pp);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(500);
        }
    }

    public static Integer save(ApplyInfo applyInfo) {

        // 执行SQL语句
        String sql = "INSERT INTO `bitwatch`.`t_apply_info` " +
                "(`user_name`, `phone_num`, `email`, `addr`, `remark`, `create_time`) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        // 从连接池中取出连接
        Connection conn = null;
        PreparedStatement pstmt = null;

        Integer result = -1;

        try{
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, applyInfo.getUsrName());
            pstmt.setString(2, applyInfo.getPhoneNum());
            pstmt.setString(3, applyInfo.getEmail());
            pstmt.setString(4, applyInfo.getAddr());
            pstmt.setString(5, applyInfo.getRemark());
            pstmt.setDate(6, new Date(System.currentTimeMillis()));
            result = pstmt.executeUpdate();

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            close(conn, pstmt);
        }

        return result;
    }
    public static List list(int pageNum) throws SQLException {

        int startIndex = (pageNum - 1) > 0 ? (pageNum-1)* PAGE_SIZE : 0;
        // 执行SQL语句
        String sql = "select *  from `timecash`.`t_apply_info`  order by id desc LIMIT  " + startIndex + "," + PAGE_SIZE;
        // 从连接池中取出连接
        Connection conn = null;
        PreparedStatement pstmt = null;
        List data  = new ArrayList();
        try{
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            while (rs.next()) {
                ApplyInfo applyInfo =  new ApplyInfo();
                data.add(applyInfo);
                applyInfo.setId(rs.getLong("id"));
                applyInfo.setUsrName(rs.getString("user_name"));
                applyInfo.setPhoneNum(rs.getString("phone_num"));
                applyInfo.setEmail(rs.getString("email"));
                applyInfo.setAddr(rs.getString("addr"));
                applyInfo.setRemark(rs.getString("remark"));
                applyInfo.setCreateTime(new java.util.Date(rs.getTimestamp("create_time").getTime()));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            close(conn, pstmt);
        }
        return data;
    }
    public static Integer getTotal() throws SQLException {

        // 执行SQL语句
        String sql = "select  count(id) As rows from `timecash`.`t_apply_info` ;";
        // 从连接池中取出连接
        Connection conn = null;
        PreparedStatement pstmt = null;
        Integer rows = 0;
        try{
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            while (rs.next()) {
                rows =  rs.getInt("rows");
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            close(conn, pstmt);
        }
        return rows;
    }

    public static Integer  getPage(Integer rows){
        return rows % PAGE_SIZE == 0 ? rows / PAGE_SIZE : (rows / PAGE_SIZE) + 1;
    }

    private static void close(Connection conn, PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
