/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package tk.mybatis.springboot.service;

import com.github.pagehelper.PageHelper;
import com.mysql.jdbc.StringUtils;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.applet.Main;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.springboot.mapper.VideoMapper;
import tk.mybatis.springboot.model.Country;
import tk.mybatis.springboot.model.Video;
import tk.mybatis.springboot.model.dto.JVideoClass;
import tk.mybatis.springboot.util.Dytt789;
import tk.mybatis.springboot.util.MovieHeaven;
import tk.mybatis.springboot.util.Qsptv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuzh
 * @since 2015-12-19 11:09
 */
@Service
public class VideoService {

    @Autowired
    private VideoMapper videoMapper;

    public List<Video> getAll(Video video) {
        if (video.getPage() != null && video.getRows() != null) {
            PageHelper.startPage(video.getPage(), video.getRows());
        }
        Example example = new Example(Video.class);
        Example.Criteria criteria = example.createCriteria();
        if (video.getName() != null && video.getName().length() > 0) {
            criteria.andLike("name", "%" + video.getName() + "%");
        }
        if (video.getClassId() !=null && video.getClassId().length() > 0){
            criteria.andEqualTo("classId",video.getClassId());
        }
        criteria.andEqualTo("isDelete","0");
        criteria.andIsNotNull("downloadLink");
        example.setOrderByClause(" IS_TOP desc ,ORDER_BY desc ");
        return videoMapper.selectByExample(example);
    }

    public Video getById(Integer id) {
        return videoMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        videoMapper.deleteByPrimaryKey(id);
    }

    public void save() {
//         MovieHeaven.addVideo(videoMapper);
        Dytt789.addVideo(videoMapper);
    }

    public void saveQsptv() {
        Qsptv.addVideo(videoMapper);
    }

    public void test() {
        Qsptv.test(videoMapper);
    }
}
