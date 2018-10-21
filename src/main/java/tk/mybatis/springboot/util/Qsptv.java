package tk.mybatis.springboot.util;

import com.mysql.jdbc.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.springboot.mapper.VideoMapper;
import tk.mybatis.springboot.model.Video;
import tk.mybatis.springboot.model.dto.JVideo;
import tk.mybatis.springboot.model.dto.JVideoClass;
import tk.mybatis.springboot.service.VideoService;
import tk.mybatis.springboot.threadUtil.ThreadData;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DELL-5490 on 2018/9/20.
 */
public class Qsptv {
    static ThreadPoolExecutor executor = CreateThreadPool.getThreadPool();//获取线程池
    private static VideoMapper videoMapper;

    public static void main(String[] args) {
//        addVideo(null);
//        JVideo video = new JVideo(null, "", "http://www.qsptv.com/play/42048-1-%d.html", "3");
//        getDetail(video);
//        addVideo(null);
//        WebDriver driver = ChromeDriverUtils.getChromeDriver();
//        Video video = new Video();
//        video.setDetailLink("http://www.qsptv.com/show-34423.html");
//        getDetail(video,driver);
//        driver.get("http://www.baidu.com");
//        System.out.println("finished");


    }

    public static void test(VideoMapper videoMapper){
        ArrayList<Video> list = new ArrayList();
        Video video  = new Video();
        video.setId(UUIDUtil.getUUID());
        String s = "1234567890";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 1000; i++) {
            sb.append(s);
        }
        video.setClassId("21");
        video.setDownloadLink(sb.toString());
        list.add(video);

        Video video1 = new Video();
        video1.setId(UUIDUtil.getUUID());
        video1.setClassId("21");
        video1.setDownloadLink("111");
        list.add(video1);
        executor.execute(new ThreadData(list, videoMapper));

    }

    public static void addVideo(VideoMapper videoMapper) {
//        WebDriver driver = ChromeDriverUtils.getChromeDriver();
        Qsptv.videoMapper = videoMapper;
        List<JVideoClass> list = new ArrayList<JVideoClass>();
//        list.add(new JVideoClass("http://www.qsptv.com/mdongzuo/?s=vod-type-id-25-p-%d.html", "21", "动作片"));
//        list.add(new JVideoClass("http://www.qsptv.com/mdongzuo/?s=vod-type-id-26-p-%d.html", "24", "喜剧片"));
//        list.add(new JVideoClass("http://www.qsptv.com/mdongzuo/?s=vod-type-id-27-p-%d.html", "25", "爱情片"));
//        list.add(new JVideoClass("http://www.qsptv.com/mdongzuo/?s=vod-type-id-28-p-%d.html", "22", "科幻片"));
//        list.add(new JVideoClass("http://www.qsptv.com/mdongzuo/?s=vod-type-id-29-p-%d.html", "23", "恐怖片"));
        list.add(new JVideoClass("http://www.qsptv.com/mdongzuo/?s=vod-type-id-30-p-%d.html", "28", "战争片"));
        list.add(new JVideoClass("http://www.qsptv.com/mdongzuo/?s=vod-type-id-31-p-%d.html", "26", "剧情片"));
        list.add(new JVideoClass("http://www.qsptv.com/mdongzuo/?s=vod-type-id-32-p-%d.html", "31", "纪录片"));
        list.add(new JVideoClass("http://www.qsptv.com/mdongzuo/?s=vod-type-id-33-p-%d.html", "30", "动画片"));
        for (JVideoClass jVideoClass : list) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    startLoad(jVideoClass, ChromeDriverUtils.getChromeDriver());
                }
            }).start();
        }
    }

    private static void startLoad(JVideoClass videoClass, WebDriver driver) {

        System.out.println("start====" + videoClass.name);
        List<Video> list = getList(videoClass, driver);
        if (list != null) {
            for (Video item : list) {
                Video movie = getDetail(item, driver);
                if (movie != null) {
                    movie.setIsDelete("0");
                    movie.setId(UUIDUtil.getUUID());
                }
            }
        }
        if (list.size() > 0) {
            //insert db  多线程执行插入数据
            executor.execute(new ThreadData(list, videoMapper));
            //判断是否有下一页数据
            if (list.size() > 0) {
                startLoad(videoClass, driver);
            }
            System.out.println("end====" + videoClass.name + "  movie size=" + list.size());
        }

    }

    private static List<Video> getList(JVideoClass jVideoClass, WebDriver driver) {
        driver.get(jVideoClass.getNextUrl());
        List<WebElement> data1 = driver.findElements(By.className("col-md-2"));
        List<Video> videoList = new ArrayList<>();
        for (WebElement item :
                data1) {
            WebElement picWebElement = item.findElement(By.className("video-pic"));
            WebElement scoreElement = item.findElement(By.className("score"));
            WebElement subtitleElement = item.findElement(By.className("subtitle"));
            Video video = new Video();
            video.setClassId(jVideoClass.getClassId());
            video.setCoverUrl(picWebElement.getAttribute("data-original"));
            video.setName(picWebElement.getAttribute("title"));
            video.setDetailLink(picWebElement.getAttribute("href"));
            video.setImdbGrade(scoreElement.getText());
            video.setLeadingRole(subtitleElement.getText());
            videoList.add(video);
        }
        return videoList;
    }


    private static Video getDetail(Video video, WebDriver driver) {
        try {
            driver.get(video.getDetailLink());
            List<WebElement> playlist = driver.findElements(By.className("playlist"));
            List<WebElement> detailContent = driver.findElements(By.className("details-content"));
            List<WebElement> videoAttr = driver.findElements(By.className("col-md-6"));
            if (videoAttr.size() > 0) {
                for (WebElement item :
                        videoAttr) {
                    String year = item.getText();
                    if (year.contains("年代：")) {
                        year = year.replace("年代：", "");
                        video.setYears(year);
                    }
                }
            }
            if (detailContent.size() > 0) {
                video.setDescription(detailContent.get(0).getText());
            }
            if (playlist.size() > 0 && playlist.size()<10) {
                List<WebElement> playlistA = playlist.get(0).findElements(By.tagName("a"));
                List<String> playLinks = new ArrayList<>();
                for (WebElement item :
                        playlistA) {
                    playLinks.add(item.getAttribute("href"));
                }
                StringBuffer downloadLink = new StringBuffer();
                for (String url :
                        playLinks) {
                    driver.get(url);
                    List<WebElement> realPlayUrlWebElement = driver.findElements(By.className("zanpiancms-play-iframe"));
                    for (WebElement e :
                            realPlayUrlWebElement) {
                        String link = e.getAttribute("src");
                        if (link.length() > 50) {
                            downloadLink.append(link).append(",");
                        }
                    }
                }
                video.setDownloadLink(downloadLink.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return video;
    }


}
