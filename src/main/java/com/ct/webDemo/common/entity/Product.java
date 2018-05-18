package com.ct.webDemo.common.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Product {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品小标题
     */
    @Column(name = "small_title")
    private String smallTitle;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品一级分类ID
     */
    @Column(name = "category_first")
    private Integer categoryFirst;

    /**
     * 商品二级分类ID
     */
    @Column(name = "category_second")
    private Integer categorySecond;

    /**
     * 协议价格
     */
    private BigDecimal price;

    /**
     * 商品编号，系统自动生成
     */
    private String num;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 商品重量
     */
    private Integer weight;

    /**
     * 商品入库时间
     */
    @Column(name = "stock_time")
    private Date stockTime;

    /**
     * 上下架状态 0：下架；1：上架
     */
    private Short status;

    /**
     * 商品销量
     */
    private Integer sales;

    /**
     * 评论总数
     */
    private Integer comments;

    private String picture1;

    private String picture2;

    private String picture3;

    private String picture4;

    /**
     * 主图
     */
    private String picture;

    /**
     * 热卖
     */
    @Column(name = "hot_sell")
    private Boolean hotSell;

    /**
     * 0：默认；1：促销商品
     */
    @Column(name = "is_sale")
    private Boolean isSale;

    /**
     * 企业ID
     */
    @Column(name = "business_id")
    private Integer businessId;

    /**
     * 品牌id
     */
    @Column(name = "brand_id")
    private Integer brandId;

    /**
     * 区县
     */
    private Integer area;

    /**
     * 市
     */
    private Integer city;

    /**
     * 零售价格
     */
    private BigDecimal price2;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品标题
     *
     * @return title - 商品标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置商品标题
     *
     * @param title 商品标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取商品小标题
     *
     * @return small_title - 商品小标题
     */
    public String getSmallTitle() {
        return smallTitle;
    }

    /**
     * 设置商品小标题
     *
     * @param smallTitle 商品小标题
     */
    public void setSmallTitle(String smallTitle) {
        this.smallTitle = smallTitle;
    }

    /**
     * 获取商品名称
     *
     * @return name - 商品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品名称
     *
     * @param name 商品名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取商品一级分类ID
     *
     * @return category_first - 商品一级分类ID
     */
    public Integer getCategoryFirst() {
        return categoryFirst;
    }

    /**
     * 设置商品一级分类ID
     *
     * @param categoryFirst 商品一级分类ID
     */
    public void setCategoryFirst(Integer categoryFirst) {
        this.categoryFirst = categoryFirst;
    }

    /**
     * 获取商品二级分类ID
     *
     * @return category_second - 商品二级分类ID
     */
    public Integer getCategorySecond() {
        return categorySecond;
    }

    /**
     * 设置商品二级分类ID
     *
     * @param categorySecond 商品二级分类ID
     */
    public void setCategorySecond(Integer categorySecond) {
        this.categorySecond = categorySecond;
    }

    /**
     * 获取协议价格
     *
     * @return price - 协议价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置协议价格
     *
     * @param price 协议价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取商品编号，系统自动生成
     *
     * @return num - 商品编号，系统自动生成
     */
    public String getNum() {
        return num;
    }

    /**
     * 设置商品编号，系统自动生成
     *
     * @param num 商品编号，系统自动生成
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * 获取商品库存
     *
     * @return stock - 商品库存
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置商品库存
     *
     * @param stock 商品库存
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取商品重量
     *
     * @return weight - 商品重量
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * 设置商品重量
     *
     * @param weight 商品重量
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * 获取商品入库时间
     *
     * @return stock_time - 商品入库时间
     */
    public Date getStockTime() {
        return stockTime;
    }

    /**
     * 设置商品入库时间
     *
     * @param stockTime 商品入库时间
     */
    public void setStockTime(Date stockTime) {
        this.stockTime = stockTime;
    }

    /**
     * 获取上下架状态 0：下架；1：上架
     *
     * @return status - 上下架状态 0：下架；1：上架
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 设置上下架状态 0：下架；1：上架
     *
     * @param status 上下架状态 0：下架；1：上架
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * 获取商品销量
     *
     * @return sales - 商品销量
     */
    public Integer getSales() {
        return sales;
    }

    /**
     * 设置商品销量
     *
     * @param sales 商品销量
     */
    public void setSales(Integer sales) {
        this.sales = sales;
    }

    /**
     * 获取评论总数
     *
     * @return comments - 评论总数
     */
    public Integer getComments() {
        return comments;
    }

    /**
     * 设置评论总数
     *
     * @param comments 评论总数
     */
    public void setComments(Integer comments) {
        this.comments = comments;
    }

    /**
     * @return picture1
     */
    public String getPicture1() {
        return picture1;
    }

    /**
     * @param picture1
     */
    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    /**
     * @return picture2
     */
    public String getPicture2() {
        return picture2;
    }

    /**
     * @param picture2
     */
    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    /**
     * @return picture3
     */
    public String getPicture3() {
        return picture3;
    }

    /**
     * @param picture3
     */
    public void setPicture3(String picture3) {
        this.picture3 = picture3;
    }

    /**
     * @return picture4
     */
    public String getPicture4() {
        return picture4;
    }

    /**
     * @param picture4
     */
    public void setPicture4(String picture4) {
        this.picture4 = picture4;
    }

    /**
     * 获取主图
     *
     * @return picture - 主图
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 设置主图
     *
     * @param picture 主图
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * 获取热卖
     *
     * @return hot_sell - 热卖
     */
    public Boolean getHotSell() {
        return hotSell;
    }

    /**
     * 设置热卖
     *
     * @param hotSell 热卖
     */
    public void setHotSell(Boolean hotSell) {
        this.hotSell = hotSell;
    }

    /**
     * 获取0：默认；1：促销商品
     *
     * @return is_sale - 0：默认；1：促销商品
     */
    public Boolean getIsSale() {
        return isSale;
    }

    /**
     * 设置0：默认；1：促销商品
     *
     * @param isSale 0：默认；1：促销商品
     */
    public void setIsSale(Boolean isSale) {
        this.isSale = isSale;
    }

    /**
     * 获取企业ID
     *
     * @return business_id - 企业ID
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * 设置企业ID
     *
     * @param businessId 企业ID
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * 获取品牌id
     *
     * @return brand_id - 品牌id
     */
    public Integer getBrandId() {
        return brandId;
    }

    /**
     * 设置品牌id
     *
     * @param brandId 品牌id
     */
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    /**
     * 获取区县
     *
     * @return area - 区县
     */
    public Integer getArea() {
        return area;
    }

    /**
     * 设置区县
     *
     * @param area 区县
     */
    public void setArea(Integer area) {
        this.area = area;
    }

    /**
     * 获取市
     *
     * @return city - 市
     */
    public Integer getCity() {
        return city;
    }

    /**
     * 设置市
     *
     * @param city 市
     */
    public void setCity(Integer city) {
        this.city = city;
    }

    /**
     * 获取零售价格
     *
     * @return price2 - 零售价格
     */
    public BigDecimal getPrice2() {
        return price2;
    }

    /**
     * 设置零售价格
     *
     * @param price2 零售价格
     */
    public void setPrice2(BigDecimal price2) {
        this.price2 = price2;
    }
}