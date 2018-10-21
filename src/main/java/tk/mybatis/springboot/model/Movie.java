package tk.mybatis.springboot.model;

public class Movie extends BaseEntity{

    private String name;

    private String years;

    private String country;
    //最新/国内/欧美
    private String type;
    //动作/剧情/喜剧
    private String category;

    private String imdbGrade;

    private String doubanGrade;

    private String fileSize;

    private String duration;

    private String director;

    private String leadingRole;

    private String description;

    private String coverUrl;

    private String downloadLink;

    private String showTime;

    private String publishTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImdbGrade() {
        return imdbGrade;
    }

    public void setImdbGrade(String imdbGrade) {
        this.imdbGrade = imdbGrade;
    }

    public String getDoubanGrade() {
        return doubanGrade;
    }

    public void setDoubanGrade(String doubanGrade) {
        this.doubanGrade = doubanGrade;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLeadingRole() {
        return leadingRole;
    }

    public void setLeadingRole(String leadingRole) {
        this.leadingRole = leadingRole;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

}
