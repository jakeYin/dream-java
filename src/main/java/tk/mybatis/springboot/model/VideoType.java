package tk.mybatis.springboot.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by DELL-5490 on 2018/9/18.
 */

@javax.persistence.Table(name = "T_VIDEO_TYPE")
public class VideoType extends BaseEntity{

    @Column(name = "NAME")
    private  String name;

    @Column(name = "IMG_URL")
    private  String imgUrl;

    @Column(name = "IS_DELETE")
    private String isDelete;

    @Column(name = "ORDER_BY")
    private  String orderBy;

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
