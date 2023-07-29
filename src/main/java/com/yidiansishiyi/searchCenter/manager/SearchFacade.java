package com.yidiansishiyi.searchCenter.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidiansishiyi.searchCenter.common.ErrorCode;
import com.yidiansishiyi.searchCenter.datasource.*;
import com.yidiansishiyi.searchCenter.exception.ThrowUtils;
import com.yidiansishiyi.searchCenter.model.dto.post.PostQueryRequest;
import com.yidiansishiyi.searchCenter.model.enums.SearchTypeEnum;
import com.yidiansishiyi.searchCenter.model.vo.PostVO;
import com.yidiansishiyi.searchCenter.model.vo.SearchVO;
import com.yidiansishiyi.searchCenter.model.vo.UserVO;

import com.yidiansishiyi.searchCenter.exception.BusinessException;
import com.yidiansishiyi.searchCenter.model.dto.search.SearchRequest;
import com.yidiansishiyi.searchCenter.model.dto.user.UserQueryRequest;
import com.yidiansishiyi.searchCenter.model.entity.Picture;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * 搜索门面
 * @author sanqi
 */
@Slf4j
@Component
public class SearchFacade {

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private DataSourceRegistry dataSourceRegistry;

    public SearchVO searchAll(@RequestBody SearchRequest searchResult, HttpServletRequest request){
        String searchText = searchResult.getSearchText();
        String type = searchResult.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(searchTypeEnum == null, ErrorCode.PARAMS_ERROR);

        long pageSize = searchResult.getPageSize();
        long pageNum = searchResult.getCurrent();

        if (searchTypeEnum == null){
            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = pictureDataSource.doSearch(searchText, pageNum, pageSize);
                return picturePage;
            });

            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText, pageNum, pageSize);
                return userVOPage;
            });


            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText, pageNum, pageSize);
                return postVOPage;
            });


            CompletableFuture.allOf(userTask,postTask,pictureTask).join();

            try {
                Page<UserVO> userVOPage = userTask.get();
                Page<PostVO> postVOPage = postTask.get();
                Page<Picture> picturePage = pictureTask.get();

                SearchVO searchVO = new SearchVO();
                searchVO.setPictureList(picturePage.getRecords());
                searchVO.setUserList(userVOPage.getRecords());
                searchVO.setPostList(postVOPage.getRecords());
                return searchVO;

            } catch (Exception e) {
                log.error("查询异常", e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR,"查询异常");
            }
        } else {
            SearchVO searchVO = new SearchVO();
            DataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(type);
            Page<?> page = dataSource.doSearch(searchText, pageNum, pageSize);
            searchVO.setDataList(page.getRecords());

            return searchVO;
        }
    }

}
