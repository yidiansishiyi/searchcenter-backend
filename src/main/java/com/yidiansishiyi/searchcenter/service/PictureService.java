package com.yidiansishiyi.searchcenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidiansishiyi.searchcenter.model.entity.Picture;

/**
 * 图片服务
 *
 * @author sanqi
 */
public interface PictureService {

    Page<Picture> searchPicturre(String searchText, long pageNum, long pageSize);

}
