package tk.mybatis.springboot.model.dto;

/**
 * Created by DELL-5490 on 2018/9/20.
 */
public class JVideoClass {

    public String baseUrl = "https://www.dytt789.com/Dongzuodianying/chart/%d.html";
    public String classId = "100";
    public String name = "动作片";
    public int pageNum = 1;

    public JVideoClass(String baseUrl, String classId, String name) {
        this.baseUrl = baseUrl;
        this.classId = classId;
        this.name = name;
    }

    public String getNextUrl() {
        String s = String.format(baseUrl, pageNum++);
        return s;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}