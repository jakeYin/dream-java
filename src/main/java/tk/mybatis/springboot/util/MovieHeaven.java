package tk.mybatis.springboot.util;

import org.json.JSONArray;
import org.json.JSONObject;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DELL-5490 on 2018/9/20.
 */
public class MovieHeaven {

    @Autowired
    private VideoService videoService;

    static ThreadPoolExecutor executor = CreateThreadPool.getThreadPool();//获取线程池

    private static final String base_url = "https://www.dy2018.com";

    public static void addVideo(VideoMapper videoMapper){
        List<JVideoClass> list = new ArrayList<JVideoClass>();
        list.add(new JVideoClass("https://www.dy2018.com/2/","1","动作片"));
        list.add(new JVideoClass("https://www.dy2018.com/0/","2","剧情片"));
        list.add(new JVideoClass("https://www.dy2018.com/3/","3","爱情片"));
        list.add(new JVideoClass("https://www.dy2018.com/1/","4","喜剧片"));
        list.add(new JVideoClass("https://www.dy2018.com/15/","5","犯罪片"));
        list.add(new JVideoClass("https://www.dy2018.com/14/","6","战争片"));
        list.add(new JVideoClass("https://www.dy2018.com/7/","7","惊悚片"));
        list.add(new JVideoClass("https://www.dy2018.com/5/","8","动画片"));
        list.add(new JVideoClass("https://www.dy2018.com/8/","9","恐怖片"));
        list.add(new JVideoClass("https://www.dy2018.com/2/","1","动作片"));
        //循环标题
        for (JVideoClass jVideoClass:list){
            startLoad(jVideoClass,videoMapper,1,1,jVideoClass.getBaseUrl());
        }
    }
    private static void startLoad(JVideoClass videoClass,VideoMapper videoMapper,Integer orderBy ,Integer nextUrl,String baseUrl) {
        System.out.println("start===="+videoClass.name);
        List<Video> movieList = new ArrayList<Video>();
            List<JVideo> list = getList(videoClass);
            if (list!=null){
                for (JVideo jvideo : list) {
                    Video movie = getDetail(jvideo);
                    if (movie!=null){
                        movie.setIsDelete("0");
                        movie.setId(UUIDUtil.getUUID());
                        movie.setOrderBy(orderBy.toString());
                        movie.setDescription("".equals(movie.getDescription())?null:movie.getDescription());
                        movieList.add(movie);
                        orderBy ++;
                    }
                }
            }
        if (movieList.size()>0){
            //insert db  多线程执行插入数据
            executor.execute(new ThreadData(movieList,videoMapper));
            //判断是否有下一页数据
            if (list.size()>0){
                nextUrl ++;
                startLoad(new JVideoClass(baseUrl+"index_"+nextUrl+".html",videoClass.getClassId(),videoClass.getName()),videoMapper,orderBy,nextUrl,baseUrl);
            }
            System.out.println("end===="+videoClass.name+"  movie size="+movieList.size());
        }

    }

    private static List<JVideo> getList(JVideoClass jVideoClass) {
        try {
            Document document = Jsoup.connect(jVideoClass.baseUrl).get();
            System.out.println(document.title());
            Elements list= document.select("a.ulink");
            List<JVideo> jVideoList = new ArrayList<>();
            for (Element element: list) {
                String href = element.attr("href");
                if(href.endsWith(".html") ){
                    String text = element.text();
                    jVideoList.add(new JVideo(jVideoClass.baseUrl,text,href,jVideoClass.classId));
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
            String url = base_url+jvideo.detailUrl;
            Document document = Jsoup.connect(url).get();
            Video movie = new Video();
            movie.setClassId(jvideo.classId);
            movie.setDetailLink(url);
            String imgUrl = document.select("#Zoom p img").attr("src");
            String updateTime = document.select("span.updateTime").text();
            if(updateTime.length() >0 ){
                updateTime = updateTime.replace("发布时间：","");
                movie.setPublishime(updateTime);
            }
            if(imgUrl.length() == 0){
                imgUrl = document.select("#Zoom div img").attr("src");
            }

            movie.setCoverUrl(imgUrl);
            String content = document.toString();
            content = content.replaceAll("\n","");
            List<String> list = JPatternUtils.getSubUtil(content,"<p>◎(.*?)</p>");
            List<String> list3 = JPatternUtils.getSubUtil(content,"<div>           ◎(.*?)          </div>");
            List<String> list2 = JPatternUtils.getSubUtil(content,"<p>　　(.*?)</p>");
            List<String> list4 = JPatternUtils.getSubUtil(content,"<div>(.*?)</div>");
            List<String> list5 = JPatternUtils.getSubUtil(content,"<br>(.*?)<br>");
            if(list.size() == 0){
                if(list3.size() > 0){
                    list = list3;
                } else if(list5.size()>0){
                    list = list5;
                    for (int i = 0; i < list.size(); i++) {
                        list.set(i,list.get(i).replace("◎",""));
                    }
                }
            }
            StringBuffer leadingRole = new StringBuffer();
            StringBuffer desc = new StringBuffer();
            if(list2.size() > 0){
                for (int i = 0; i < list2.size(); i++) {
                    String s = list2.get(i);
                    if(s.startsWith("　　　　")){
                        s = s.replaceAll("　　　　","");
                        leadingRole.append(s).append(" ");
                    } else {
                        desc.append(s).append(" ");
                    }
                }

            }
            if(list4.size()>0 && desc.length() == 0) {
                for (int i = 0; i < list4.size(); i++) {
                    String s = list4.get(i);
                    if(s.startsWith("           ◎主　　演　")){
                        s = s.replace("           ◎主　　演　","           　　　　　　");
                    }
                    if(s.startsWith("           　　　　　　")){
                        s = s.trim();
                        s = s.replace("　　　　　　","");
                        leadingRole.append(s).append(" ");
                    }
                    if(s.startsWith("           　　")){
                        s = s.trim();
                        desc.append(s).append(" ");
                    }
                }
            }
            if(list5.size()>0 && desc.length() == 0){
                for (int i = 0; i < list5.size(); i++) {
                    String s = list5.get(i);
                    if(s.startsWith("导　　演　")){
                        s = s.replace("导　　演　","　　　　　　");
                    }
                    if(s.startsWith("　　　　　　")){
                        s = s.trim();
                        s = s.replace("　　　　　　","");
                        leadingRole.append(s).append(" ");
                    }
                    if(s.startsWith("　　")){
                        s = s.trim();
                        desc.append(s).append(" ");
                    }
                }
            }
            String descStr = desc.toString().replaceAll("&middot;",".");
            descStr = descStr.replaceAll("&amp;","&");
            descStr = descStr.replaceAll("&nbsp;"," ");
            movie.setDescription(descStr);
            String leadingRoleStr = leadingRole.toString().replaceAll("&middot;",".");
            movie.setLeadingRole(leadingRoleStr);

            if (jvideo.getTitle()!=null){
                movie.setName(jvideo.getTitle());
            }
            for (int i = 0; i < list.size(); i++) {
                String text = list.get(i);
                if (text.startsWith(Filter1.country)){
                    movie.setCountry(text.replace(Filter1.country,""));
                } else if(text.startsWith(Filter1.doubanGrade)){
                    movie.setDoubanGrade(text.replace(Filter1.doubanGrade,""));
                    text = text.replace(Filter1.doubanGrade,"");
                    text = text.substring(0,3);
                    movie.setDoubanGrade(text);
                }else if(text.startsWith(Filter1.imdbGrade)){
                    text = text.replace(Filter1.imdbGrade,"");
                    text = text.substring(0,3);
                    movie.setImdbGrade(text);
                }else if(text.startsWith(Filter1.showTime)){
                    movie.setShowTime(text.replace(Filter1.showTime,""));
                }else if(text.startsWith(Filter1.fileSize)){
                    movie.setFileSize(text.replace(Filter1.fileSize,""));
                }else if(text.startsWith(Filter1.duration)){
                    movie.setDuration(text.replace(Filter1.duration,""));
                }else if(text.startsWith(Filter1.director)){
                    text = text.replaceAll("&middot;",".");
                    movie.setDirector(text.replace(Filter1.director,""));
                }else if(text.startsWith(Filter1.year)){
                    movie.setYears(text.replace(Filter1.year,""));
                }else if(text.startsWith(Filter1.typeDesc)){
                    movie.setTypeDesc(text.replace(Filter1.typeDesc,""));
                }
            }

            Elements elements = document.select("a");
            for (int i = 0; i < elements.size(); i++) {
                Element e = elements.get(i);
                String s = e.text();
                if(s.startsWith("ftp://")||s.startsWith("magnet:")){
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

    public static void main(String[]args){
        JVideo video = new JVideo("","","/html/gndy/jddy/20090901/21320.html","1");
        getDetail(video);
    }
}
