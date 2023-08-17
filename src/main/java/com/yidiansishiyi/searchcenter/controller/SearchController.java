package com.yidiansishiyi.searchcenter.controller;

import com.yidiansishiyi.searchcenter.common.BaseResponse;
import com.yidiansishiyi.searchcenter.common.ResultUtils;
import com.yidiansishiyi.searchcenter.manager.SearchFacade;
import com.yidiansishiyi.searchcenter.model.dto.search.SearchRequest;
import com.yidiansishiyi.searchcenter.model.vo.SearchVO;
import com.yidiansishiyi.searchcenter.service.PictureService;
import com.yidiansishiyi.searchcenter.service.PostService;
import com.yidiansishiyi.searchcenter.service.UserService;
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
