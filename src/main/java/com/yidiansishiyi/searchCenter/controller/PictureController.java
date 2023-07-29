package com.yidiansishiyi.searchCenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidiansishiyi.searchCenter.common.BaseResponse;
import com.yidiansishiyi.searchCenter.common.ErrorCode;
import com.yidiansishiyi.searchCenter.common.ResultUtils;
import com.yidiansishiyi.searchCenter.exception.ThrowUtils;
import com.yidiansishiyi.searchCenter.model.dto.post.PostQueryRequest;
import com.yidiansishiyi.searchCenter.model.entity.Picture;
import com.yidiansishiyi.searchCenter.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片接口接口
 *
 * @author sanqi
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;


    /**
     * 分页获取列表（封装类）
     *
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
        public BaseResponse<Page<Picture>> listPictureVOByPage(@RequestBody PostQueryRequest pictureQueryRequest,
                                                               HttpServletRequest request) {
        {
            long current = pictureQueryRequest.getCurrent();
            long size = pictureQueryRequest.getPageSize();
            // 限制爬虫
            ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
            String searchText = pictureQueryRequest.getSearchText();
            Page<Picture> picturePage = pictureService.searchPicturre(searchText, current, size);

            return ResultUtils.success(picturePage);
        }
    }
}
