package com.fulu.game.core.service.impl;

import com.fulu.game.common.Constant;
import com.fulu.game.common.utils.GenIdUtil;
import com.fulu.game.core.dao.ChannelDao;
import com.fulu.game.core.dao.ICommonDao;
import com.fulu.game.core.entity.Admin;
import com.fulu.game.core.entity.Channel;
import com.fulu.game.core.entity.vo.ChannelVO;
import com.fulu.game.core.service.AdminService;
import com.fulu.game.core.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ChannelServiceImpl extends AbsCommonService<Channel, Integer> implements ChannelService {

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private AdminService adminService;

    @Override
    public ICommonDao<Channel, Integer> getDao() {
        return channelDao;
    }

    @Override
    public List<Channel> findByParam(ChannelVO channelVO){
        return channelDao.findByParameter(channelVO);
    }

    @Override
    public Channel save(String name){
        Admin admin = adminService.getCurrentUser();
        int adminId = admin.getId();
        log.info("新增渠道商,操作人id={}",adminId);
        Channel channel = new Channel();
        channel.setName(name);
        channel.setAdminId(adminId);
        channel.setAdminName(admin.getName());
        channel.setBalance(Constant.DEFAULT_BALANCE);
        channel.setCreateTime(new Date());
        channel.setUpdateTime(new Date());
        String appid = GenIdUtil.getAppid();
        channel.setAppid(appid);
        String appkey = GenIdUtil.getAppkey();
        channel.setAppkey(appkey);
        channelDao.create(channel);
        log.info("新增渠道商成功,appid={},appkey={}",appid,appkey);
        return channel;
    }

}
