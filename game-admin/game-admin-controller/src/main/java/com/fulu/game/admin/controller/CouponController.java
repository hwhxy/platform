package com.fulu.game.admin.controller;

import com.fulu.game.common.Result;
import com.fulu.game.core.entity.Coupon;
import com.fulu.game.core.entity.CouponGroup;
import com.fulu.game.core.service.CouponGrantService;
import com.fulu.game.core.service.CouponGroupService;
import com.fulu.game.core.service.CouponService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController extends BaseController{

    @Autowired
    private CouponGroupService couponGroupService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponGrantService couponGrantService;

    /**
     * 批量生产优惠券
     * @param couponGroup
     * @return
     */
    @PostMapping(value = "generate")
    public Result generate(@Valid CouponGroup couponGroup){
        couponGroupService.create(couponGroup);
        return Result.success().msg("批量生产优惠券成功!");
    }


    /**
     * 优惠券列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping(value = "list")
    public Result list(Integer pageNum,
                       Integer pageSize){
        PageInfo<CouponGroup> pageInfo = couponGroupService.list(pageNum,pageSize,null);
        return Result.success().data(pageInfo);
    }


    /**
     * 优惠券详情
     * @param id
     * @return
     */
    @PostMapping(value = "details")
    public Result details(@RequestParam(required = true)Integer id,
                          @RequestParam(required = true)Integer pageNum,
                          @RequestParam(required = true)Integer pageSize){
        PageInfo<Coupon> pageInfo =  couponService.listByGroup(id,pageNum,pageSize,null);
        return Result.success().data(pageInfo);
    }


    /**
     * 优惠券发放
     * @param redeemCode
     * @param mobiles
     * @param remark
     * @return
     */
    @PostMapping(value = "grant")
    public Result couponGrant(@RequestParam(required = true)String redeemCode,
                              @RequestParam(required = true)String mobiles,
                              @RequestParam(required = true)String remark){
        if(StringUtils.isBlank(mobiles)){
            return Result.error().msg("手机号不能为空");
        }
        List<String> mobileList = Arrays.asList(mobiles.split(","));
        couponGrantService.create(redeemCode,mobileList,remark);
        return Result.success().msg("优惠券发放完成，发放失败用户请查看明显!");
    }


}