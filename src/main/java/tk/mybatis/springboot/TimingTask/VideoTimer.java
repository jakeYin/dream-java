package tk.mybatis.springboot.TimingTask;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by DELL-5490 on 2018/9/22.
 */
@Configuration          //证明这个类是一个配置文件
@EnableScheduling       //打开quartz定时器总开关
public class VideoTimer {

   // @Scheduled(cron = "0/2 * * * * *")
    public void timer() {
        //获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
