package com.yidiansishiyi.searchcenter.datasource;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidiansishiyi.searchcenter.common.ErrorCode;
import com.yidiansishiyi.searchcenter.exception.BusinessException;
import com.yidiansishiyi.searchcenter.model.entity.Picture;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 图片业务实现
 * 图片来源为必应
 *
 * @author sanqi
 */
@Service
public class PictureDataSource implements DataSource<Picture> {
    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize) {

        long current = (pageNum-1)*pageSize;
        String url = String.format("https://cn.bing.com/images/search?q=%s&first=%s",searchText,current);

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据获取异常");
        }
        Elements elements = doc.select(".iuscp.isv");
        ArrayList<Picture> pictures = new ArrayList<>();
        for (Element element : elements) {
            if (pictures.size() >= pageSize) {
                break;
            }
            String m = element.select(".iusc").get(0).attr("m");
            String title = element.select(".inflnk").get(0).attr("aria-label");
            Map<String, Object> map = JSONUtil.toBean(m,Map.class);
            String murl = (String) map.get("murl");
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
            pictures.add(picture);
        }
        Page<Picture> picturePage = new Page<Picture>(pageNum,pageSize);
        picturePage.setRecords(pictures);
        return picturePage;
    }
}
