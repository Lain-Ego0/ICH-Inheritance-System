package com.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test-db")
    public String testDb() {
        try {
            // 执行简单查询（替换成你数据库里的表，比如user表）
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Integer.class);
            return "数据库连接成功！用户表行数：" + count;
        } catch (Exception e) {
            return "数据库连接失败：" + e.getMessage();
        }
    }
}