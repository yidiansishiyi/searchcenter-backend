package com.yidiansishiyi.searchcenter.model.vo;


import com.yidiansishiyi.searchcenter.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合搜索
 *
 * @author sanqi
 */
@Data
public class SearchVO implements Serializable {

    private List<UserVO> userList;

    private List<PostVO> postList;

    private List<?> dataList;
    private List<Picture> pictureList;
}
