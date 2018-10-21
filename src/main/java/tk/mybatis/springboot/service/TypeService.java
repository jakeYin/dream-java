package tk.mybatis.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.springboot.mapper.ClassMapper;
import tk.mybatis.springboot.mapper.TypeMapper;
import tk.mybatis.springboot.model.VideoClass;
import tk.mybatis.springboot.model.VideoType;

import java.util.List;

/**
 * Created by DELL-5490 on 2018/9/18.
 */
@Service
public class TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private ClassMapper classMapper;

    public List<VideoType> getAll(VideoType type) {
        Example example = new Example(VideoType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete","0");
        example.setOrderByClause(" ORDER_BY desc ");
        return typeMapper.selectByExample(example);
    }

    public List<VideoClass> getClassAll(VideoClass videoClass) {
        Example example = new Example(VideoClass.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("typeId",videoClass.getTypeId());
        criteria.andEqualTo("isDelete","0");
        example.setOrderByClause(" ORDER_BY ASC ");
        return classMapper.selectByExample(example);
    }
}
