package com.yidiansishiyi.searchCenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidiansishiyi.searchCenter.model.entity.Picture;

/**
 * 图片服务
 *
 * @author sanqi
 */
public interface PictureService {

    Page<Picture> searchPicturre(String searchText, long pageNum, long pageSize);

}
