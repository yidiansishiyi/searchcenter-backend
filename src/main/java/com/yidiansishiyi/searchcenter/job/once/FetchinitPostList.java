package com.yidiansishiyi.searchcenter.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yidiansishiyi.searchcenter.service.PostService;
import com.yidiansishiyi.searchcenter.model.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 全量同步帖子到 es
 * @author sanqi
 */
// 注释取消,每次启动后执行
//@Component
@Slf4j
public class FetchinitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Override
    public void run(String... args) {
        // 1. 获取数据
        String json = "{\n" +
                "  \"current\": 1,\n" +
                "  \"pageSize\": 8,\n" +
                "  \"sortField\": \"createTime\",\n" +
                "  \"sortOrder\": \"descend\",\n" +
                "  \"category\": \"文章\",\n" +
                "  \"reviewStatus\": 1\n" +
                "}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest
                .post(url)
                .body(json)
                .execute().body();
//        System.out.println(result2);
        // 2. json 转对象
        Map<String, Object> map = JSONUtil.toBean(result,Map.class);
        System.out.println(map);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        System.out.println(records);
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject tempRecord = (JSONObject) record;
            Post post = new Post();
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            JSONArray tags = (JSONArray) tempRecord.get("tags");
            List<String> tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));
            post.setUserId(1L);
            postList.add(post);
        }
//        System.out.println(postList);
        // 3. 数据入库
        boolean b = postService.saveBatch(postList);
        if (b) {
            log.info("帖子列表初始化成功");
        }else{
            log.info("帖子列表初始化失败");
        }
    }
}
