package tk.mybatis.springboot.model;

import javax.persistence.Column;

/**
 * Created by DELL-5490 on 2018/9/18.
 */
@javax.persistence.Table(name = "T_VIDEO_CLASS")
public class VideoClass extends BaseEntity{

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE_ID")
    private String typeId;

    @Column(name = "ORDER_BY")
    private String orderBy;

    @Column(name = "IS_DELETE")
    private String isDelete;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
