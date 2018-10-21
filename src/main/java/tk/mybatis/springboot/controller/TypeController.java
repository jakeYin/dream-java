package tk.mybatis.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.springboot.model.VideoType;
import tk.mybatis.springboot.service.TypeService;

import java.util.List;

/**
 * Created by DELL-5490 on 2018/9/18.
 */
@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @RequestMapping
    public List<VideoType> getAll(VideoType type) {
        List<VideoType> list = typeService.getAll(type);
        return list;
    }


}
