package com.ct.webDemo.common.entity;

import javax.persistence.*;

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品分类名称
     */
    private String name;

    /**
     * 0：一级分类
   >0：二级分类，为一级分类主键ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 是否显示，逻辑删除。默认为1：显示；0：不显示
     */
    @Column(name = "is_show")
    private Boolean isShow;

    private String icon;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品分类名称
     *
     * @return name - 商品分类名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品分类名称
     *
     * @param name 商品分类名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取0：一级分类
   >0：二级分类，为一级分类主键ID
     *
     * @return parent_id - 0：一级分类
   >0：二级分类，为一级分类主键ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置0：一级分类
   >0：二级分类，为一级分类主键ID
     *
     * @param parentId 0：一级分类
   >0：二级分类，为一级分类主键ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取是否显示，逻辑删除。默认为1：显示；0：不显示
     *
     * @return is_show - 是否显示，逻辑删除。默认为1：显示；0：不显示
     */
    public Boolean getIsShow() {
        return isShow;
    }

    /**
     * 设置是否显示，逻辑删除。默认为1：显示；0：不显示
     *
     * @param isShow 是否显示，逻辑删除。默认为1：显示；0：不显示
     */
    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    /**
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
}