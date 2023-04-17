package com.example.springboot;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.Sign;
import com.example.springboot.service.ISignService;

import javax.annotation.Resource;

public class su {
    @Resource
    private ISignService signService;
    public static void main(String[] args) {
        String now= DateUtil.now();
        System.out.println(now.substring(0,10));//
    }
}
