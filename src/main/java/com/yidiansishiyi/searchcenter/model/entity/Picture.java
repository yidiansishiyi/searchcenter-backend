package com.yidiansishiyi.searchcenter.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 图片
 * @author sanqi
 */
@Data
public class Picture implements Serializable {
    private static final long serialVersionUID = 4473334845302675835L;
    private String title;

    private String url;
}
