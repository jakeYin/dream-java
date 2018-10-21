package tk.mybatis.springboot.model.dto;

/**
 * Created by DELL-5490 on 2018/9/20.
 */
public class JVideo {

  public  String parentUrl;
  public String title;
  public  String detailUrl;
  public String classId;

    public JVideo(String parentUrl, String title, String detailUrl,String classId) {
        this.parentUrl = parentUrl;
        this.title = title;
        this.detailUrl = detailUrl;
        this.classId = classId;
    }

    public String getParentUrl() {
        return parentUrl;
    }

    public void setParentUrl(String parentUrl) {
        this.parentUrl = parentUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
