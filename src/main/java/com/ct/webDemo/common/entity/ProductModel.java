package com.ct.webDemo.common.entity;

import javax.persistence.*;

@Table(name = "product_model")
public class ProductModel {
    /**
     * 主键id
            
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 生产许可证
     */
    private String xukz;

    /**
     * 产品标准号
     */
    private String biaozh;

    /**
     * 厂名
     */
    private String changm;

    /**
     * 品牌
     */
    private String pinp;

    /**
     * 产品类别
     */
    private String leib;

    /**
     * 保质期
     */
    private String baozq;

    /**
     * 是否有机
     */
    private String youj;

    /**
     * 产地
     */
    private String changd;

    /**
     * 存储方法
     */
    private String cuncff;

    /**
     * 添加剂
     */
    private String tianjj;

    /**
     * 商品ID
     */
    @Column(name = "product_id")
    private Integer productId;

    private String lxfs;

    private String jinhl;

    /**
     * 商品详情
     */
    private String detail;

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
     * 获取生产许可证
     *
     * @return xukz - 生产许可证
     */
    public String getXukz() {
        return xukz;
    }

    /**
     * 设置生产许可证
     *
     * @param xukz 生产许可证
     */
    public void setXukz(String xukz) {
        this.xukz = xukz;
    }

    /**
     * 获取产品标准号
     *
     * @return biaozh - 产品标准号
     */
    public String getBiaozh() {
        return biaozh;
    }

    /**
     * 设置产品标准号
     *
     * @param biaozh 产品标准号
     */
    public void setBiaozh(String biaozh) {
        this.biaozh = biaozh;
    }

    /**
     * 获取厂名
     *
     * @return changm - 厂名
     */
    public String getChangm() {
        return changm;
    }

    /**
     * 设置厂名
     *
     * @param changm 厂名
     */
    public void setChangm(String changm) {
        this.changm = changm;
    }

    /**
     * 获取品牌
     *
     * @return pinp - 品牌
     */
    public String getPinp() {
        return pinp;
    }

    /**
     * 设置品牌
     *
     * @param pinp 品牌
     */
    public void setPinp(String pinp) {
        this.pinp = pinp;
    }

    /**
     * 获取产品类别
     *
     * @return leib - 产品类别
     */
    public String getLeib() {
        return leib;
    }

    /**
     * 设置产品类别
     *
     * @param leib 产品类别
     */
    public void setLeib(String leib) {
        this.leib = leib;
    }

    /**
     * 获取保质期
     *
     * @return baozq - 保质期
     */
    public String getBaozq() {
        return baozq;
    }

    /**
     * 设置保质期
     *
     * @param baozq 保质期
     */
    public void setBaozq(String baozq) {
        this.baozq = baozq;
    }

    /**
     * 获取是否有机
     *
     * @return youj - 是否有机
     */
    public String getYouj() {
        return youj;
    }

    /**
     * 设置是否有机
     *
     * @param youj 是否有机
     */
    public void setYouj(String youj) {
        this.youj = youj;
    }

    /**
     * 获取产地
     *
     * @return changd - 产地
     */
    public String getChangd() {
        return changd;
    }

    /**
     * 设置产地
     *
     * @param changd 产地
     */
    public void setChangd(String changd) {
        this.changd = changd;
    }

    /**
     * 获取存储方法
     *
     * @return cuncff - 存储方法
     */
    public String getCuncff() {
        return cuncff;
    }

    /**
     * 设置存储方法
     *
     * @param cuncff 存储方法
     */
    public void setCuncff(String cuncff) {
        this.cuncff = cuncff;
    }

    /**
     * 获取添加剂
     *
     * @return tianjj - 添加剂
     */
    public String getTianjj() {
        return tianjj;
    }

    /**
     * 设置添加剂
     *
     * @param tianjj 添加剂
     */
    public void setTianjj(String tianjj) {
        this.tianjj = tianjj;
    }

    /**
     * 获取商品ID
     *
     * @return product_id - 商品ID
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 设置商品ID
     *
     * @param productId 商品ID
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * @return lxfs
     */
    public String getLxfs() {
        return lxfs;
    }

    /**
     * @param lxfs
     */
    public void setLxfs(String lxfs) {
        this.lxfs = lxfs;
    }

    /**
     * @return jinhl
     */
    public String getJinhl() {
        return jinhl;
    }

    /**
     * @param jinhl
     */
    public void setJinhl(String jinhl) {
        this.jinhl = jinhl;
    }

    /**
     * 获取商品详情
     *
     * @return detail - 商品详情
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 设置商品详情
     *
     * @param detail 商品详情
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }
}