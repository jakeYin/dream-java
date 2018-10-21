package tk.mybatis.springboot.threadUtil;

import tk.mybatis.springboot.mapper.VideoMapper;
import tk.mybatis.springboot.model.Video;

import java.util.List;

/**
 * Created by DELL-5490 on 2018/9/22.
 */
public class ThreadData implements Runnable{

    private List<Video> dataList;

    private VideoMapper videoMapper;


    public ThreadData(List<Video> dataList ,VideoMapper videoMapper) {
        this.dataList = dataList;
        this.videoMapper = videoMapper;
    }
    @Override
    public void run() {
        for (Video data:dataList){
            System.out.println(data.getClassId());
            //添加try...catch , 防止1条添加失败, 持续终止了
            try {
                videoMapper.insert(data);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
