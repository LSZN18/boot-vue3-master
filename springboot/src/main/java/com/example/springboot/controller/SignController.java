package com.example.springboot.controller;

import com.example.springboot.common.annotation.AutoLog;
import cn.hutool.core.date.DateUtil;
import com.example.springboot.entity.User;
import com.example.springboot.utils.SessionUtils;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import java.net.URLEncoder;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import org.springframework.web.multipart.MultipartFile;
import com.example.springboot.service.ISignService;
import com.example.springboot.entity.Sign;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 李家和
 * @since 2023-03-27
 */
@RestController
@RequestMapping("/sign")
public class SignController {

    @Resource
    private ISignService signService;

    @AutoLog("新增签到")
    @PostMapping
    public Result save(@RequestBody Sign sign) {
//        User user = SessionUtils.getUser();
//        sign.setUser(user.getName());
//        sign.setUserid(user.getId());
//        sign.setDate(DateUtil.today());
        String now= DateUtil.now();
        if (sign.getId() == null) {

            sign.setTime(now);
            QueryWrapper<Sign> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user", sign.getUser());
            queryWrapper.likeRight("time", (now.substring(0,10)));//
            Sign one = signService.getOne(queryWrapper);
            if (one != null) {//打过卡了
                return Result.error("-1", "您今日已打过卡！");
            }

        }sign.setTime(now);
        signService.save(sign);
        return Result.success();
    }

    @AutoLog("编辑签到")
    @PutMapping
    public Result update(@RequestBody Sign sign) {
        signService.updateById(sign);
        return Result.success();
    }

    @AutoLog("删除签到")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        signService.removeById(id);
        return Result.success();
    }

    @AutoLog("批量删除签到")
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        signService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(signService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(signService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Sign> queryWrapper = new QueryWrapper<Sign>().orderByDesc("id");
        queryWrapper.like(!"".equals(name), "name", name);
        return Result.success(signService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Sign> list = signService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("Sign信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     *
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Sign> list = reader.readAll(Sign.class);

        signService.saveBatch(list);
        return Result.success();
    }

}
