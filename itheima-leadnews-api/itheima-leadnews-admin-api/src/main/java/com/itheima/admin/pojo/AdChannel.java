package com.itheima.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Arthurocky
 */
@Data
@TableName("ad_channel")
@ApiModel(value="adChannel",description = "这个是频道对象")
public class AdChannel implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(notes = "这个是主键")
    private Integer id;

    /**
     * 频道名称
     */
    @TableField("name")
    @ApiModelProperty(notes = "这个是频道名称")
    private String name;

    /**
     * 频道描述
     */
    @TableField("description")
    @ApiModelProperty(notes = "这个是频道描述信息")
    private String description;

    /**
     * 是否默认频道
     */
    @TableField("is_default")
    private Boolean isDefault;

    @TableField("status")
    private Boolean status;

    /**
     * 默认排序
     */
    @TableField("ord")
    private Integer ord;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;

}