package tk.mybatis.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.springboot.model.VideoClass;
import tk.mybatis.springboot.service.TypeService;

import java.util.List;

/**
 * Created by DELL-5490 on 2018/9/18.
 */
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private TypeService typeService;

    @RequestMapping
    public List<VideoClass> getAll(VideoClass videoClass) {
        List<VideoClass> list = typeService.getClassAll(videoClass);
        return list;
    }
}
