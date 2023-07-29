package com.yidiansishiyi.searchCenter.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.formula.functions.T;

/**
 * 搜索数据源必须实现的规范接口
 * @author sanqi
 * @param <T>
 */
public interface DataSource<T> {
    /**
     * 搜索方法
     * @param searchText 关键词
     * @param pageNum
     * @param pageSize
     * @return
     * @author sanqi
     */
    Page<T> doSearch(String searchText, long pageNum, long pageSize);
}
