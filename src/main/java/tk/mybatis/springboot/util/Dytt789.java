package tk.mybatis.springboot.util;

import com.mysql.jdbc.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

import static javax.management.Query.attr;

/**
 * Created by DELL-5490 on 2018/9/20.
 */
public class Dytt789 {

    @Autowired
    private VideoService videoService;
    private static VideoMapper videoMapper;
    static ThreadPoolExecutor executor = CreateThreadPool.getThreadPool();//获取线程池

    public static void main(String[]args){
        addVideo(null);
    }
    public static void addVideo(VideoMapper videoMapper){
        Dytt789.videoMapper = videoMapper;
        List<JVideoClass> list = new ArrayList<JVideoClass>();
        list.add(new JVideoClass("https://www.dytt789.com/Dongzuodianying/chart/%d.html","10","动作电影"));
        list.add(new JVideoClass("https://www.dytt789.com/Kehuandianying/chart/%d.html","11","科幻电影"));
        list.add(new JVideoClass("https://www.dytt789.com/Kongbudianying/chart/%d.html","12","恐怖电影"));
        list.add(new JVideoClass("https://www.dytt789.com/Xijudianying/chart/%d.html","13","喜剧电影"));
        list.add(new JVideoClass("https://www.dytt789.com/Aiqingdianying/chart/%d.html","14","爱情电影"));
        list.add(new JVideoClass("https://www.dytt789.com/Juqingdianying/chart/%d.html","15","剧情电影"));
        for (JVideoClass jVideoClass:list){
            startLoad(jVideoClass);
        }
    }
    private static void startLoad(JVideoClass videoClass) {
        System.out.println("start===="+videoClass.name);
        List<Video> movieList = new ArrayList<Video>();
            List<JVideo> list = getList(videoClass);
            if (list!=null){
                for (JVideo jvideo : list) {
                    Video movie = getDetail(jvideo);
                    if (movie!=null){
                        movie.setIsDelete("0");
                        movie.setId(UUIDUtil.getUUID());
                        movie.setDescription("".equals(movie.getDescription())?null:movie.getDescription());
                        movieList.add(movie);
                    }
                }
            }
        if (movieList.size()>0){
            //insert db  多线程执行插入数据
            executor.execute(new ThreadData(movieList,videoMapper));
            //判断是否有下一页数据
            if (list.size()>0){
                startLoad(videoClass);
            }
            System.out.println("end===="+videoClass.name+"  movie size="+movieList.size());
        }

    }

    private static List<JVideo> getList(JVideoClass jVideoClass) {
        try {
            Document document = Jsoup.connect(jVideoClass.getNextUrl()).validateTLSCertificates(false).get();
            System.out.println(document.title());
            Elements list= document.select(".box3_mid a");
            List<JVideo> jVideoList = new ArrayList<>();
            ArrayList<String> links = new ArrayList<>();
            for (Element element: list) {
                String href = element.attr("href");
                if(!href.endsWith(".html")&&!links.contains(href)){
                    links.add(href);
                    String title = element.attr("title");
                    jVideoList.add(new JVideo(jVideoClass.baseUrl,title,href,jVideoClass.classId));
                }
            }
            System.out.println("get detail links");
            System.out.println(jVideoList.size());
            return jVideoList;
        } catch (HttpStatusException e) {
            return null;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Video getDetail(JVideo jvideo) {
        try {
            System.out.println("start getDetail "+jvideo.title);
            String url = jvideo.detailUrl;
            Document document = Jsoup.connect(url).validateTLSCertificates(false).get();
            Video movie = new Video();
            movie.setClassId(jvideo.classId);
            movie.setDetailLink(url);
            String imgUrl = document.select(".haibao img").attr("src");
            String updateTime = document.select("h1 p").text();
            if(updateTime.length() >0 ){
                updateTime = updateTime.replace("发布时间：","");
                movie.setPublishime(updateTime);
            }
            movie.setCoverUrl(imgUrl);
            String  leadingRole = document.select(".zhuyan").text();
            leadingRole = leadingRole.replace("主　演 ： ","");
            movie.setLeadingRole(leadingRole);

            String content = document.select(".neirong").text();
            if (jvideo.getTitle()!=null){
                movie.setName(jvideo.getTitle());
            }
            if(content.contains("◎")){
                String director = JPatternUtils.getSub(content,"导演: (.*?)主演: ");
                String typeDesc = JPatternUtils.getSub(content,"◎类　　别　(.*?)◎语　　言　");
                String country = JPatternUtils.getSub(content,"◎产　　地　(.*?)◎类　　别　");
                String lanuage = JPatternUtils.getSub(content,"◎语　　言　(.*?)◎字　　幕　");
                int index = content.indexOf("◎简　　介");
                String desc = "";
                if(index>-1){
                    desc = content.substring(index+"◎简　　介".length());
                }
                movie.setDirector(director);
                movie.setTypeDesc(typeDesc);
                movie.setCountry(country);
                movie.setDescription(desc);
                if(StringUtils.isNullOrEmpty(director) && StringUtils.isNullOrEmpty(typeDesc) && StringUtils.isNullOrEmpty(country)){
                    movie.setDescription(content);
                }
                System.out.println("");
            } else {
                String director = JPatternUtils.getSub(content,"导演: (.*?)主演: ");
                String typeDesc = JPatternUtils.getSub(content,"类型: (.*?)制片国家/地区: ");
                String country = JPatternUtils.getSub(content,"制片国家/地区: (.*?)语言: ");
                String lanuage = JPatternUtils.getSub(content,"语言: (.*?)上映日期: ");
                int index = content.indexOf(" ・ ・ ・ ・ ・ ・ 　　");
                String desc = "";
                if(index>-1){
                    desc = content.substring(index+" ・ ・ ・ ・ ・ ・ 　　".length());
                }
                movie.setDirector(director);
                movie.setTypeDesc(typeDesc);
                movie.setCountry(country);
                movie.setDescription(desc);

                if(StringUtils.isNullOrEmpty(director) && StringUtils.isNullOrEmpty(typeDesc) && StringUtils.isNullOrEmpty(country)){
                    movie.setDescription(content);
                }

                System.out.println("");
            }


            Elements elements = document.select(".downurl a");
            for (int i = 0; i < elements.size(); i++) {
                Element e = elements.get(i);
                String s = e.attr("href");
                String text = e.text();
                if((s.startsWith("ftp://")||s.startsWith("thunder:"))&&!text.toLowerCase().contains("cd")){
                    movie.setDownloadLink(s);
                    break;
                }
            }
            System.out.println("end getDetail");
            return movie;
        } catch ( SocketTimeoutException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
