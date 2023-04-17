package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import cn.hutool.core.annotation.Alias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.example.springboot.common.LDTConfig;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 
* </p>
*
* @author 李家和
* @since 2023-03-27
*/
@Getter
@Setter
@ApiModel(value = "Sign对象", description = "")
public class Sign implements Serializable {

private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 用户名称
    @ApiModelProperty("用户名称")
    @Alias("用户名称")
    private String user;

    // 打卡位置
    @ApiModelProperty("打卡位置")
    @Alias("打卡位置")
    private String location;

    // 打卡时间
    @ApiModelProperty("打卡时间")
    @Alias("打卡时间")
    private String time;

    // 备注
    @ApiModelProperty("备注")
    @Alias("备注")
    private String comment;
}