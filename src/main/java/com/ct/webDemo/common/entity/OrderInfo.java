package com.ct.webDemo.common.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "order_info")
public class OrderInfo {
    /**
     * 主键id
            
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单编号
     */
    @Column(name = "order_num")
    private String orderNum;

    /**
     * 订单状态
1：等待买家付款（刚刚下单）；
2：等待卖家发货（已付款）；
3：等待买家确认收货（已发货）；
4：买家确认收货；
5：订单完成；
6：关闭订单；
7：取消订单；

     */
    @Column(name = "order_status")
    private Short orderStatus;

    /**
     * 支付方式
     */
    @Column(name = "order_paymethod")
    private String orderPaymethod;

    /**
     * 实际支付金额
     */
    @Column(name = "order_paycost")
    private BigDecimal orderPaycost;

    /**
     * 物流费用
     */
    @Column(name = "delivery_cost")
    private BigDecimal deliveryCost;

    /**
     * 商品总费用
     */
    @Column(name = "product_cost")
    private BigDecimal productCost;

    /**
     * 积分抵扣
     */
    @Column(name = "integration_cost")
    private BigDecimal integrationCost;

    /**
     * 退款总金额
     */
    @Column(name = "refund_cost")
    private BigDecimal refundCost;

    /**
     * 订单原来总金额
     */
    @Column(name = "total_cost")
    private BigDecimal totalCost;

    /**
     * 收货人姓名
     */
    @Column(name = "buyer_realname")
    private String buyerRealname;

    /**
     * 收货人联系方式
     */
    @Column(name = "buyer_contact")
    private String buyerContact;

    /**
     * 收货人邮编
     */
    @Column(name = "buyer_post")
    private String buyerPost;

    /**
     * 收货人地址
     */
    @Column(name = "buyer_addr")
    private String buyerAddr;

    /**
     * 购买人帐号
     */
    @Column(name = "buyer_account")
    private String buyerAccount;

    /**
     * 送积分
     */
    @Column(name = "integration_gain")
    private Integer integrationGain;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 完成时间
     */
    @Column(name = "finished_time")
    private Date finishedTime;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 物流方式ID
     */
    @Column(name = "delivery_id")
    private Integer deliveryId;

    /**
     * 物流单号
     */
    @Column(name = "delivery_num")
    private String deliveryNum;

    /**
     * 支付时提交到支付宝返回的支付宝业务编号
     */
    @Column(name = "aplipay_num")
    private String aplipayNum;

    /**
     * 发货时间
     */
    @Column(name = "delivery_out_time")
    private Date deliveryOutTime;

    @Column(name = "back_up")
    private String backUp;

    /**
     * 企业ID
     */
    @Column(name = "business_id")
    private Integer businessId;

    /**
     * 是否评论
     */
    private String comments;

    @Column(name = "delivery_pic")
    private String deliveryPic;

    /**
     * 银行打款单号
     */
    @Column(name = "bank_receipt")
    private String bankReceipt;

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
     * 获取订单编号
     *
     * @return order_num - 订单编号
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     * 设置订单编号
     *
     * @param orderNum 订单编号
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取订单状态
1：等待买家付款（刚刚下单）；
2：等待卖家发货（已付款）；
3：等待买家确认收货（已发货）；
4：买家确认收货；
5：订单完成；
6：关闭订单；
7：取消订单；

     *
     * @return order_status - 订单状态
1：等待买家付款（刚刚下单）；
2：等待卖家发货（已付款）；
3：等待买家确认收货（已发货）；
4：买家确认收货；
5：订单完成；
6：关闭订单；
7：取消订单；

     */
    public Short getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置订单状态
1：等待买家付款（刚刚下单）；
2：等待卖家发货（已付款）；
3：等待买家确认收货（已发货）；
4：买家确认收货；
5：订单完成；
6：关闭订单；
7：取消订单；

     *
     * @param orderStatus 订单状态
1：等待买家付款（刚刚下单）；
2：等待卖家发货（已付款）；
3：等待买家确认收货（已发货）；
4：买家确认收货；
5：订单完成；
6：关闭订单；
7：取消订单；

     */
    public void setOrderStatus(Short orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取支付方式
     *
     * @return order_paymethod - 支付方式
     */
    public String getOrderPaymethod() {
        return orderPaymethod;
    }

    /**
     * 设置支付方式
     *
     * @param orderPaymethod 支付方式
     */
    public void setOrderPaymethod(String orderPaymethod) {
        this.orderPaymethod = orderPaymethod;
    }

    /**
     * 获取实际支付金额
     *
     * @return order_paycost - 实际支付金额
     */
    public BigDecimal getOrderPaycost() {
        return orderPaycost;
    }

    /**
     * 设置实际支付金额
     *
     * @param orderPaycost 实际支付金额
     */
    public void setOrderPaycost(BigDecimal orderPaycost) {
        this.orderPaycost = orderPaycost;
    }

    /**
     * 获取物流费用
     *
     * @return delivery_cost - 物流费用
     */
    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    /**
     * 设置物流费用
     *
     * @param deliveryCost 物流费用
     */
    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    /**
     * 获取商品总费用
     *
     * @return product_cost - 商品总费用
     */
    public BigDecimal getProductCost() {
        return productCost;
    }

    /**
     * 设置商品总费用
     *
     * @param productCost 商品总费用
     */
    public void setProductCost(BigDecimal productCost) {
        this.productCost = productCost;
    }

    /**
     * 获取积分抵扣
     *
     * @return integration_cost - 积分抵扣
     */
    public BigDecimal getIntegrationCost() {
        return integrationCost;
    }

    /**
     * 设置积分抵扣
     *
     * @param integrationCost 积分抵扣
     */
    public void setIntegrationCost(BigDecimal integrationCost) {
        this.integrationCost = integrationCost;
    }

    /**
     * 获取退款总金额
     *
     * @return refund_cost - 退款总金额
     */
    public BigDecimal getRefundCost() {
        return refundCost;
    }

    /**
     * 设置退款总金额
     *
     * @param refundCost 退款总金额
     */
    public void setRefundCost(BigDecimal refundCost) {
        this.refundCost = refundCost;
    }

    /**
     * 获取订单原来总金额
     *
     * @return total_cost - 订单原来总金额
     */
    public BigDecimal getTotalCost() {
        return totalCost;
    }

    /**
     * 设置订单原来总金额
     *
     * @param totalCost 订单原来总金额
     */
    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * 获取收货人姓名
     *
     * @return buyer_realname - 收货人姓名
     */
    public String getBuyerRealname() {
        return buyerRealname;
    }

    /**
     * 设置收货人姓名
     *
     * @param buyerRealname 收货人姓名
     */
    public void setBuyerRealname(String buyerRealname) {
        this.buyerRealname = buyerRealname;
    }

    /**
     * 获取收货人联系方式
     *
     * @return buyer_contact - 收货人联系方式
     */
    public String getBuyerContact() {
        return buyerContact;
    }

    /**
     * 设置收货人联系方式
     *
     * @param buyerContact 收货人联系方式
     */
    public void setBuyerContact(String buyerContact) {
        this.buyerContact = buyerContact;
    }

    /**
     * 获取收货人邮编
     *
     * @return buyer_post - 收货人邮编
     */
    public String getBuyerPost() {
        return buyerPost;
    }

    /**
     * 设置收货人邮编
     *
     * @param buyerPost 收货人邮编
     */
    public void setBuyerPost(String buyerPost) {
        this.buyerPost = buyerPost;
    }

    /**
     * 获取收货人地址
     *
     * @return buyer_addr - 收货人地址
     */
    public String getBuyerAddr() {
        return buyerAddr;
    }

    /**
     * 设置收货人地址
     *
     * @param buyerAddr 收货人地址
     */
    public void setBuyerAddr(String buyerAddr) {
        this.buyerAddr = buyerAddr;
    }

    /**
     * 获取购买人帐号
     *
     * @return buyer_account - 购买人帐号
     */
    public String getBuyerAccount() {
        return buyerAccount;
    }

    /**
     * 设置购买人帐号
     *
     * @param buyerAccount 购买人帐号
     */
    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    /**
     * 获取送积分
     *
     * @return integration_gain - 送积分
     */
    public Integer getIntegrationGain() {
        return integrationGain;
    }

    /**
     * 设置送积分
     *
     * @param integrationGain 送积分
     */
    public void setIntegrationGain(Integer integrationGain) {
        this.integrationGain = integrationGain;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取完成时间
     *
     * @return finished_time - 完成时间
     */
    public Date getFinishedTime() {
        return finishedTime;
    }

    /**
     * 设置完成时间
     *
     * @param finishedTime 完成时间
     */
    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    /**
     * 获取支付时间
     *
     * @return pay_time - 支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取物流方式ID
     *
     * @return delivery_id - 物流方式ID
     */
    public Integer getDeliveryId() {
        return deliveryId;
    }

    /**
     * 设置物流方式ID
     *
     * @param deliveryId 物流方式ID
     */
    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    /**
     * 获取物流单号
     *
     * @return delivery_num - 物流单号
     */
    public String getDeliveryNum() {
        return deliveryNum;
    }

    /**
     * 设置物流单号
     *
     * @param deliveryNum 物流单号
     */
    public void setDeliveryNum(String deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    /**
     * 获取支付时提交到支付宝返回的支付宝业务编号
     *
     * @return aplipay_num - 支付时提交到支付宝返回的支付宝业务编号
     */
    public String getAplipayNum() {
        return aplipayNum;
    }

    /**
     * 设置支付时提交到支付宝返回的支付宝业务编号
     *
     * @param aplipayNum 支付时提交到支付宝返回的支付宝业务编号
     */
    public void setAplipayNum(String aplipayNum) {
        this.aplipayNum = aplipayNum;
    }

    /**
     * 获取发货时间
     *
     * @return delivery_out_time - 发货时间
     */
    public Date getDeliveryOutTime() {
        return deliveryOutTime;
    }

    /**
     * 设置发货时间
     *
     * @param deliveryOutTime 发货时间
     */
    public void setDeliveryOutTime(Date deliveryOutTime) {
        this.deliveryOutTime = deliveryOutTime;
    }

    /**
     * @return back_up
     */
    public String getBackUp() {
        return backUp;
    }

    /**
     * @param backUp
     */
    public void setBackUp(String backUp) {
        this.backUp = backUp;
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
     * 获取是否评论
     *
     * @return comments - 是否评论
     */
    public String getComments() {
        return comments;
    }

    /**
     * 设置是否评论
     *
     * @param comments 是否评论
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return delivery_pic
     */
    public String getDeliveryPic() {
        return deliveryPic;
    }

    /**
     * @param deliveryPic
     */
    public void setDeliveryPic(String deliveryPic) {
        this.deliveryPic = deliveryPic;
    }

    /**
     * 获取银行打款单号
     *
     * @return bank_receipt - 银行打款单号
     */
    public String getBankReceipt() {
        return bankReceipt;
    }

    /**
     * 设置银行打款单号
     *
     * @param bankReceipt 银行打款单号
     */
    public void setBankReceipt(String bankReceipt) {
        this.bankReceipt = bankReceipt;
    }
}