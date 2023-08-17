package com.yidiansishiyi.searchcenter.datasource;

import com.yidiansishiyi.searchcenter.model.enums.SearchTypeEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *数据源注册表
 *
 * @author sanqi
 */
@Component
public class DataSourceRegistry {

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    private Map<String,DataSource<T>> typeDataSourceMap;

    /**
     * 初始化注册表
     */
    @PostConstruct
    public void doInit(){
        typeDataSourceMap = new HashMap(){{
            put(SearchTypeEnum.POST.getValue(),postDataSource);
            put(SearchTypeEnum.PICTURE.getValue(),pictureDataSource);
            put(SearchTypeEnum.USER.getValue(),userDataSource);
        }};
    }

    /**
     * 根据 type 获取对应数据源
     * @param type 前端传入类型
     * @return 对应数据源实例
     */
    public DataSource getDataSourceByType(String type) {
        return typeDataSourceMap == null ? null : typeDataSourceMap.get(type);
    }

}
