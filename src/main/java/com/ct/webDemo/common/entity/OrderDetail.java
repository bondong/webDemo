package com.ct.webDemo.common.entity;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "order_detail")
public class OrderDetail {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 商品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 商品价格
     */
    @Column(name = "product_price")
    private BigDecimal productPrice;

    /**
     * 商品购买数量
     */
    @Column(name = "product_count")
    private Integer productCount;

    /**
     * 商品图片
     */
    @Column(name = "product_image")
    private String productImage;

    /**
     * 是否评论0：没有评论；>0：评论id
     */
    @Column(name = "comment_id")
    private Integer commentId;

    /**
     * 备注信息
     */
    @Column(name = "back_desc")
    private String backDesc;

    /**
     * 订单状态；
0：正常状态；
1：申请退款；
2：拒绝退款；
3：同意退款；
4：退款完成；
     */
    private Short status;

    private BigDecimal average;

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
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取商品id
     *
     * @return product_id - 商品id
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 设置商品id
     *
     * @param productId 商品id
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 获取商品名称
     *
     * @return product_name - 商品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置商品名称
     *
     * @param productName 商品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取商品价格
     *
     * @return product_price - 商品价格
     */
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    /**
     * 设置商品价格
     *
     * @param productPrice 商品价格
     */
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * 获取商品购买数量
     *
     * @return product_count - 商品购买数量
     */
    public Integer getProductCount() {
        return productCount;
    }

    /**
     * 设置商品购买数量
     *
     * @param productCount 商品购买数量
     */
    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    /**
     * 获取商品图片
     *
     * @return product_image - 商品图片
     */
    public String getProductImage() {
        return productImage;
    }

    /**
     * 设置商品图片
     *
     * @param productImage 商品图片
     */
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    /**
     * 获取是否评论0：没有评论；>0：评论id
     *
     * @return comment_id - 是否评论0：没有评论；>0：评论id
     */
    public Integer getCommentId() {
        return commentId;
    }

    /**
     * 设置是否评论0：没有评论；>0：评论id
     *
     * @param commentId 是否评论0：没有评论；>0：评论id
     */
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    /**
     * 获取备注信息
     *
     * @return back_desc - 备注信息
     */
    public String getBackDesc() {
        return backDesc;
    }

    /**
     * 设置备注信息
     *
     * @param backDesc 备注信息
     */
    public void setBackDesc(String backDesc) {
        this.backDesc = backDesc;
    }

    /**
     * 获取订单状态；
0：正常状态；
1：申请退款；
2：拒绝退款；
3：同意退款；
4：退款完成；
     *
     * @return status - 订单状态；
0：正常状态；
1：申请退款；
2：拒绝退款；
3：同意退款；
4：退款完成；
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 设置订单状态；
0：正常状态；
1：申请退款；
2：拒绝退款；
3：同意退款；
4：退款完成；
     *
     * @param status 订单状态；
0：正常状态；
1：申请退款；
2：拒绝退款；
3：同意退款；
4：退款完成；
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * @return average
     */
    public BigDecimal getAverage() {
        return average;
    }

    /**
     * @param average
     */
    public void setAverage(BigDecimal average) {
        this.average = average;
    }
}