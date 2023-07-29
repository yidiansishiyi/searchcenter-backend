package com.yidiansishiyi.searchCenter;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yidiansishiyi.searchCenter.model.entity.Picture;
import com.yidiansishiyi.searchCenter.model.entity.Post;
import com.yidiansishiyi.searchCenter.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CrawlerTest {

    @Resource
    private PostService postService;

    @Test
    void testFetchPicture() throws IOException {
        int current = 1;
        String url = "https://cn.bing.com/images/search?q=小黑子&first=" + current;

        Document doc = Jsoup.connect(url).get();
//        System.out.println(doc);
        Elements elements = doc.select(".iuscp.isv");
        ArrayList<Picture> pictures = new ArrayList<>();
        for (Element element : elements) {
            String m = element.select(".iusc").get(0).attr("m");
            String title = element.select(".inflnk").get(0).attr("aria-label");
            Map<String, Object> map = JSONUtil.toBean(m,Map.class);
            String murl = (String) map.get("murl");
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
            pictures.add(picture);
//            System.out.println(murl);
//            System.out.println(title);
        }
        System.out.println(pictures);
    }

    @Test
    void testFetchPassage(){
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
        System.out.println(postList);
        // 3. 数据入库
        boolean b = postService.saveBatch(postList);
        Assertions.assertTrue(b);
    }


}
