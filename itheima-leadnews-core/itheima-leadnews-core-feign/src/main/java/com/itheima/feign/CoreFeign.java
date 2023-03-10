package com.itheima.feign;

import com.itheima.common.pojo.PageInfo;
import com.itheima.common.pojo.PageRequestDto;
import com.itheima.common.pojo.Result;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;


/**
 * @author Arthurocky
 */
public interface CoreFeign<T> {
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable(name = "id") Serializable id) ;

    /**
     * 添加记录
     *
     * @param record
     * @return
     */
    @PostMapping
    public Result<T> save(@RequestBody T record) ;

    /**
     * 更新数据
     */
    @PutMapping
    public Result updateByPrimaryKey(@RequestBody T record) ;

    /**
     * 查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<T> findById(@PathVariable(name = "id") Serializable id) ;

    @GetMapping
    public Result<List<T>> findAll() ;

    /**
     * 根据等号条进行查询调用
     * @param record
     * @return
     */
    @PostMapping("/listCondition")
    public Result<List<T>> findByRecord(@RequestBody T record) ;


    /**
     * 通用条件分页查询
     *
     * @param pageRequestDto
     * @return
     */
    @PostMapping(value = "/search")
    public Result<PageInfo<T>> findByPage(@RequestBody PageRequestDto<T> pageRequestDto) ;
}
