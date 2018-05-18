package com.ct.webDemo.common.entity;

import java.util.Date;
import javax.persistence.*;

public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    /**
     * 是否推荐
0：不推荐
1：推荐
     */
    private String recommend;

    /**
     * 是否举报
0：没有举报
1：被举报
     */
    private String report;

    /**
     * 主图文件地址
     */
    @Column(name = "major_pic")
    private String majorPic;

    /**
     * 所有图片地址
     */
    private String pics;

    /**
     * 简要描述
     */
    @Column(name = "major_desc")
    private String majorDesc;

    /**
     * 关注量
     */
    private Integer likes;

    /**
     * 分享量
     */
    private Integer shares;

    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 状态
0：待审核
1：审核通过
2：审核不通过
3：举报成功
     */
    private String status;

    /**
     * 分类集合
     */
    @Column(name = "tag_id")
    private String tagId;

    /**
     * 自定义标签
     */
    @Column(name = "my_tag")
    private String myTag;

    /**
     * 活动ID
     */
    @Column(name = "activity_id")
    private Integer activityId;

    /**
     * 审核不通过原因
     */
    private String reason;

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
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取是否推荐
0：不推荐
1：推荐
     *
     * @return recommend - 是否推荐
0：不推荐
1：推荐
     */
    public String getRecommend() {
        return recommend;
    }

    /**
     * 设置是否推荐
0：不推荐
1：推荐
     *
     * @param recommend 是否推荐
0：不推荐
1：推荐
     */
    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    /**
     * 获取是否举报
0：没有举报
1：被举报
     *
     * @return report - 是否举报
0：没有举报
1：被举报
     */
    public String getReport() {
        return report;
    }

    /**
     * 设置是否举报
0：没有举报
1：被举报
     *
     * @param report 是否举报
0：没有举报
1：被举报
     */
    public void setReport(String report) {
        this.report = report;
    }

    /**
     * 获取主图文件地址
     *
     * @return major_pic - 主图文件地址
     */
    public String getMajorPic() {
        return majorPic;
    }

    /**
     * 设置主图文件地址
     *
     * @param majorPic 主图文件地址
     */
    public void setMajorPic(String majorPic) {
        this.majorPic = majorPic;
    }

    /**
     * 获取所有图片地址
     *
     * @return pics - 所有图片地址
     */
    public String getPics() {
        return pics;
    }

    /**
     * 设置所有图片地址
     *
     * @param pics 所有图片地址
     */
    public void setPics(String pics) {
        this.pics = pics;
    }

    /**
     * 获取简要描述
     *
     * @return major_desc - 简要描述
     */
    public String getMajorDesc() {
        return majorDesc;
    }

    /**
     * 设置简要描述
     *
     * @param majorDesc 简要描述
     */
    public void setMajorDesc(String majorDesc) {
        this.majorDesc = majorDesc;
    }

    /**
     * 获取关注量
     *
     * @return likes - 关注量
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     * 设置关注量
     *
     * @param likes 关注量
     */
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     * 获取分享量
     *
     * @return shares - 分享量
     */
    public Integer getShares() {
        return shares;
    }

    /**
     * 设置分享量
     *
     * @param shares 分享量
     */
    public void setShares(Integer shares) {
        this.shares = shares;
    }

    /**
     * 获取发布时间
     *
     * @return publish_time - 发布时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置发布时间
     *
     * @param publishTime 发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 获取状态
0：待审核
1：审核通过
2：审核不通过
3：举报成功
     *
     * @return status - 状态
0：待审核
1：审核通过
2：审核不通过
3：举报成功
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
0：待审核
1：审核通过
2：审核不通过
3：举报成功
     *
     * @param status 状态
0：待审核
1：审核通过
2：审核不通过
3：举报成功
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取分类集合
     *
     * @return tag_id - 分类集合
     */
    public String getTagId() {
        return tagId;
    }

    /**
     * 设置分类集合
     *
     * @param tagId 分类集合
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    /**
     * 获取自定义标签
     *
     * @return my_tag - 自定义标签
     */
    public String getMyTag() {
        return myTag;
    }

    /**
     * 设置自定义标签
     *
     * @param myTag 自定义标签
     */
    public void setMyTag(String myTag) {
        this.myTag = myTag;
    }

    /**
     * 获取活动ID
     *
     * @return activity_id - 活动ID
     */
    public Integer getActivityId() {
        return activityId;
    }

    /**
     * 设置活动ID
     *
     * @param activityId 活动ID
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     * 获取审核不通过原因
     *
     * @return reason - 审核不通过原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置审核不通过原因
     *
     * @param reason 审核不通过原因
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
}