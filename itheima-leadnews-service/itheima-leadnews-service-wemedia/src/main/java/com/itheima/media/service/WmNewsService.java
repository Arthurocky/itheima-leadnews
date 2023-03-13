package com.itheima.media.service;

import com.itheima.common.pojo.PageInfo;
import com.itheima.common.pojo.PageRequestDto;
import com.itheima.media.dto.WmNewsDto;
import com.itheima.media.pojo.WmNews;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 自媒体图文内容信息表 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-12-22
 */
public interface WmNewsService extends IService<WmNews> {

    /**
     * 分页查询
     * @param pageRequestDto
     * @return
     */
    PageInfo<WmNews> findByPageDto(PageRequestDto<WmNewsDto> pageRequestDto);

}
