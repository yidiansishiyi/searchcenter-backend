package com.yidiansishiyi.searchcenter.esdao;

import com.yidiansishiyi.searchcenter.model.dto.post.PostEsDTO;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 帖子 ES 操作
 *
 * @author sanqi
 */
public interface PostEsDao extends ElasticsearchRepository<PostEsDTO, Long> {

    List<PostEsDTO> findByUserId(Long userId);
}