package com.fulu.game.core.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.fulu.game.common.enums.TechAuthStatusEnum;
import com.fulu.game.core.dao.ICommonDao;
import com.fulu.game.core.dao.UserNightInfoDao;
import com.fulu.game.core.entity.*;
import com.fulu.game.core.entity.vo.ProductShowCaseVO;
import com.fulu.game.core.entity.vo.SalesModeVO;
import com.fulu.game.core.entity.vo.UserInfoVO;
import com.fulu.game.core.entity.vo.UserNightInfoVO;
import com.fulu.game.core.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserNightInfoServiceImpl extends AbsCommonService<UserNightInfo, Integer> implements UserNightInfoService {

    @Autowired
    private UserNightInfoDao userNightInfoDao;
    @Qualifier("userTechAuthServiceImpl")
    @Autowired
    private UserTechAuthService userTechAuthService;
    @Autowired
    private SalesModeService salesModeService;
    @Autowired
    private AdminService adminService;
    @Qualifier(value = "userInfoAuthServiceImpl")
    @Autowired
    private UserInfoAuthServiceImpl userInfoAuthService;
    @Autowired
    private ProductService productService;

    @Override
    public ICommonDao<UserNightInfo, Integer> getDao() {
        return userNightInfoDao;
    }

    @Override
    public PageInfo<UserNightInfo> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "info.sort ASC");
        UserNightInfoVO vo = new UserNightInfoVO();
        vo.setDelFlag(Boolean.FALSE);
        List<UserNightInfo> infoList = userNightInfoDao.list(vo);
        return new PageInfo<>(infoList);
    }

    @Override
    public void remove(Integer id) {
        UserNightInfo info = new UserNightInfo();
        info.setId(id);
        info.setUpdateTime(DateUtil.date());
        info.setDelFlag(Boolean.TRUE);
        userNightInfoDao.update(info);
    }

    @Override
    public UserNightInfoVO getNightConfig(Integer userId) {
        //获取此用户所有已通过的认证技能
        List<UserTechAuth> techAuthList = userTechAuthService.findByStatusAndUserId(userId,
                TechAuthStatusEnum.NORMAL.getType());

        List<SalesMode> salesModes = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(techAuthList)) {
            for (UserTechAuth meta : techAuthList) {
                Integer cId = meta.getCategoryId();
                salesModes = salesModeService.findByCategory(cId);
            }
        }

        UserNightInfoVO vo = new UserNightInfoVO();
        vo.setUserId(userId);
        vo.setDelFlag(Boolean.FALSE);
        List<UserNightInfo> infoList = userNightInfoDao.findByParameter(vo);
        UserNightInfo info = new UserNightInfo();
        if (CollectionUtils.isNotEmpty(infoList)) {
            info = infoList.get(0);
        }
        UserNightInfoVO resultVo = new UserNightInfoVO();
        BeanUtil.copyProperties(info, resultVo);
        resultVo.setAllUserTechs(techAuthList);
        resultVo.setAllSalesModes(salesModes);
        return resultVo;
    }

    @Override
    public UserNightInfo setNightConfig(Integer userId, Integer sort, Integer categoryId, Integer salesModeId) {
        Admin admin = adminService.getCurrentUser();

        SalesModeVO vo = new SalesModeVO();
        vo.setId(salesModeId);
        vo.setDelFlag(0);
        List<SalesMode> salesModeList = salesModeService.findByParameter(vo);
        SalesMode salesMode = new SalesMode();
        if (CollectionUtils.isNotEmpty(salesModeList)) {
            salesMode = salesModeList.get(0);
        }

        UserNightInfo info = new UserNightInfo();
        info.setUserId(userId);
        info.setCategoryId(categoryId);
        info.setSalesModeId(salesMode.getId());
        info.setType(salesMode.getType());
        info.setName(salesMode.getName());
        info.setSort(sort);
        info.setAdminId(admin.getId());
        info.setAdminName(admin.getName());
        info.setUpdateTime(DateUtil.date());
        info.setCreateTime(DateUtil.date());
        info.setDelFlag(Boolean.FALSE);

        UserNightInfoVO infoVO = new UserNightInfoVO();
        infoVO.setUserId(userId);
        infoVO.setDelFlag(Boolean.FALSE);
        List<UserNightInfo> infoList = userNightInfoDao.findByParameter(infoVO);
        if (CollectionUtils.isNotEmpty(infoList)) {
            userNightInfoDao.updateByUserId(info);
        } else {
            userNightInfoDao.create(info);
        }
        return info;
    }

    @Override
    public PageInfo<ProductShowCaseVO> findNightUserByPage(Integer gender, Integer pageNum, Integer pageSize) {
        String orderBy = "t1.sort ASC, t2.create_time DESC";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        UserNightInfoVO vo = new UserNightInfoVO();
        vo.setGender(gender);
        List<ProductShowCaseVO> caseVOList = userNightInfoDao.findNightUser(vo);

        if (CollectionUtils.isNotEmpty(caseVOList)) {
            for (ProductShowCaseVO showCaseVO : caseVOList) {
                UserInfoVO userInfoVO = userInfoAuthService.findUserCardByUserId(showCaseVO.getUserId(), false, false, true, false);
                showCaseVO.setNickName(userInfoVO.getNickName());
                showCaseVO.setGender(userInfoVO.getGender());
                showCaseVO.setMainPhoto(userInfoVO.getMainPhotoUrl());
                showCaseVO.setCity(userInfoVO.getCity());
                showCaseVO.setPersonTags(userInfoVO.getTags());
                UserTechInfo userTechInfo = userTechAuthService.findDanInfo(showCaseVO.getTechAuthId());
                if (userTechInfo != null) {
                    showCaseVO.setDan(userTechInfo.getValue());
                }
                showCaseVO.setOnLine(productService.isProductStartOrderReceivingStatus(showCaseVO.getId()));
            }
        }
        return new PageInfo<>(caseVOList);
    }
}
