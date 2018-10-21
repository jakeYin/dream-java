/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package tk.mybatis.springboot.model;

import javax.persistence.Column;

/**
 * @author liuzh_3nofxnp
 * @since 2016-01-22 22:16
 */
@javax.persistence.Table(name = "T_VIDEO")
public class Video extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    /**
     * 封面url
     */
    @Column(name = "COVER_URL")
    private String coverUrl;
    /**
     * 描述
     */
    @Column(name = "DESCRIPTION")
    private String description;
    /**
     * 导演
     */
    @Column(name = "DIRECTOR")
    private String director;
    /**
     * 豆瓣分数
     */
    @Column(name = "DOUBAN_GRADE")
    private String doubanGrade;
    /**
     * 下载地址
     */
    @Column(name = "DOWNLOAD_LINK")
    private String downloadLink;
    /**
     * 持续时间
     */
    @Column(name = "DURATION")
    private String duration;
    /**
     * 文件大小
     */
    @Column(name = "FILE_SIZE")
    private String fileSize;

    @Column(name = "IMDB_GRADE")
    private String imdbGrade;

    /**
     * 领先角色
     */
    @Column(name = "LEADING_ROLE")
    private String leadingRole;

    /**
     * 发布时间
     */
    @Column(name = "PUBLISH_TIME")
    private String publishime;

    /**
     * 展示时间
     */
    @Column(name = "SHOW_TIME")
    private String showTime;

    /**
     * 国家
     */
    @Column(name = "COUNTRY")
    private String country;

    /**
     * 类型
     */
    @Column(name = "TYPE")
    private String type;

    @Column(name = "CLASS_ID")
    private String classId;

    @Column(name = "IS_DELETE")
    private String isDelete;

    @Column(name = "DETAIL_LINK")
    private String detailLink;

    /**
     * 年
     */
    @Column(name = "YEARS")
    private String years;

    @Column(name = "TYPE_DESC")
    private String typeDesc;

    @Column(name = "ORDER_BY")
    private  String orderBy;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getDetailLink() {
        return detailLink;
    }

    public void setDetailLink(String detailLink) {
        this.detailLink = detailLink;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDoubanGrade() {
        return doubanGrade;
    }

    public void setDoubanGrade(String doubanGrade) {
        this.doubanGrade = doubanGrade;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getImdbGrade() {
        return imdbGrade;
    }

    public void setImdbGrade(String imdbGrade) {
        this.imdbGrade = imdbGrade;
    }

    public String getLeadingRole() {
        return leadingRole;
    }

    public void setLeadingRole(String leadingRole) {
        this.leadingRole = leadingRole;
    }

    public String getPublishime() {
        return publishime;
    }

    public void setPublishime(String publishime) {
        this.publishime = publishime;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }
}
