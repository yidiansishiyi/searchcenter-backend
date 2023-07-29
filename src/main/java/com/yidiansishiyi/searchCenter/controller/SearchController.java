package com.yidiansishiyi.searchCenter.controller;

import com.yidiansishiyi.searchCenter.common.BaseResponse;
import com.yidiansishiyi.searchCenter.common.ResultUtils;
import com.yidiansishiyi.searchCenter.manager.SearchFacade;
import com.yidiansishiyi.searchCenter.model.dto.search.SearchRequest;
import com.yidiansishiyi.searchCenter.model.vo.SearchVO;
import com.yidiansishiyi.searchCenter.service.PictureService;
import com.yidiansishiyi.searchCenter.service.PostService;
import com.yidiansishiyi.searchCenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片接口接口
 * @author sanqi
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {
    @Resource
    private UserService userService;

    @Resource
    private PostService postService;

    @Resource
    private PictureService pictureService;

    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchResult, HttpServletRequest request){
        SearchVO searchVO = searchFacade.searchAll(searchResult, request);
        return ResultUtils.success(searchVO);
    }



}
