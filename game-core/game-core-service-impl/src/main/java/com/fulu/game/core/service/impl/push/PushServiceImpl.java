package com.fulu.game.core.service.impl.push;

import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.fulu.game.common.Constant;
import com.fulu.game.common.enums.WechatTemplateIdEnum;
import com.fulu.game.common.utils.SMSUtil;
import com.fulu.game.core.entity.User;
import com.fulu.game.core.entity.WxMaTemplateMessageVO;
import com.fulu.game.core.entity.vo.WechatFormidVO;
import com.fulu.game.core.service.PushService;
import com.fulu.game.core.service.UserService;
import com.fulu.game.core.service.WechatFormidService;
import com.fulu.game.core.service.impl.RedisOpenServiceImpl;
import com.fulu.game.core.service.queue.PushMsgQueue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public abstract class PushServiceImpl implements PushService {


    @Autowired
    private PushMsgQueue pushMsgQueue;
    @Autowired
    private UserService userService;
    @Autowired
    private WechatFormidService wechatFormidService;
    @Autowired
    private RedisOpenServiceImpl redisOpenService;


    /**
     * 通用模板推送消息
     *
     * @param platform
     * @param userId
     * @param templateIdEnum
     * @param wechatPage
     * @param content
     * @param replaces
     */
    protected void pushWechatTemplateMsg(int platform, int userId, WechatTemplateIdEnum templateIdEnum, String wechatPage, String content, String... replaces) {
        if (replaces != null && replaces.length > 0) {
            content = StrUtil.format(content, replaces);
        }
        String date = DateUtil.format(new Date(), "yyyy年MM月dd日 HH:mm");
        List<WxMaTemplateMessage.Data> dataList;
        switch (templateIdEnum) {
            case PLAY_LEAVE_MSG:
                dataList = CollectionUtil.newArrayList(new WxMaTemplateMessage.Data("keyword1", content), new WxMaTemplateMessage.Data("keyword2", date));
                break;
            default:
                dataList = CollectionUtil.newArrayList(new WxMaTemplateMessage.Data("keyword1", content), new WxMaTemplateMessage.Data("keyword2", date));
        }

        addTemplateMsg2Queue(platform, null, Collections.singletonList(userId), wechatPage, templateIdEnum, dataList);
    }


    /**
     * 批量写入推送模板消息
     *
     * @param pushId
     * @param userIds
     * @param page
     * @param wechatTemplateEnum
     * @param dataList
     */
    public synchronized void addTemplateMsg2Queue(int platform,
                                                  Integer pushId,
                                                  List<Integer> userIds,
                                                  String page,
                                                  WechatTemplateIdEnum wechatTemplateEnum,
                                                  List<WxMaTemplateMessage.Data> dataList) {
        //删除表里面过期的formId
        long startTime = System.currentTimeMillis();
        Date date = DateUtil.offset(new Date(), DateField.HOUR, (-24 * 7) + 1);
        wechatFormidService.deleteByExpireTime(date);
        long endTime = System.currentTimeMillis();
        log.info("pushTask:{}执行wechatFormidService.deleteByExpireTime方法耗时:{}", pushId, endTime - startTime);
        for (int i = 0; ; i = +1000) {
            List<WechatFormidVO> wechatFormidVOS = null;
            try {
                long findStartTime = System.currentTimeMillis();
                List<User> users = userService.findByUserIds(userIds, Boolean.TRUE);
                wechatFormidVOS = wechatFormidService.findByUserIds(platform, userIds, i, 1000);
                long findEndTime = System.currentTimeMillis();
                log.info("pushTask:{}执行wechatFormidService.findByUserIds:{}", pushId, findEndTime - findStartTime);
                if (wechatFormidVOS.isEmpty()) {
                    break;
                }
                List<String> formIds = new ArrayList<>();
                Map<Integer, String> userFormIds = new HashMap<>();
                //发送微信模板消息
                for (WechatFormidVO wechatFormidVO : wechatFormidVOS) {
                    WxMaTemplateMessage wxMaTemplateMessage = new WxMaTemplateMessage();
                    wxMaTemplateMessage.setTemplateId(wechatTemplateEnum.getTemplateId());
                    wxMaTemplateMessage.setToUser(wechatFormidVO.getOpenId());
                    wxMaTemplateMessage.setPage(page);
                    wxMaTemplateMessage.setFormId(wechatFormidVO.getFormId());
                    wxMaTemplateMessage.setData(dataList);
                    pushMsgQueue.addTemplateMessage(new WxMaTemplateMessageVO(platform, pushId, wxMaTemplateMessage));
                    formIds.add(wechatFormidVO.getFormId());
                    userFormIds.put(wechatFormidVO.getUserId(), wechatFormidVO.getFormId());
                }
                //没有有效formId的user进行短信补发
                sendSMSIfFormIdInVaild(userFormIds, users, dataList);
                //删除已经发过的formId
                if (formIds.size() > 0) {
                    long delStartTime = System.currentTimeMillis();
                    wechatFormidService.deleteFormIds(formIds.toArray(new String[]{}));
                    long delEndTime = System.currentTimeMillis();
                    log.info("pushTask:{}执行wechatFormidService.deleteFormIds方法耗时:{}", pushId, delEndTime - delStartTime);
                }
            } catch (Exception e) {
                log.error("批量写入推送模板消息异常wechatFormidVOS:{}", wechatFormidVOS);
                log.error("批量写入推送模板消息异常", e);
            }

        }
    }

    /**
     * 如果formid无效，补发短信
     *
     * @param userFormId
     * @param userList
     * @param dataList
     */
    private void sendSMSIfFormIdInVaild(Map<Integer, String> userFormId, List<User> userList, List<WxMaTemplateMessage.Data> dataList) {
        String content = null;
        if (CollectionUtil.isEmpty(dataList)) {
            return;
        }
        for (WxMaTemplateMessage.Data data : dataList) {
            if ("keyword1".equals(data.getName())) {
                content = data.getValue();
            }
        }
        if (StringUtils.isEmpty(content) || CollectionUtil.isEmpty(userList)) {
            return;
        }
        for (User user : userList) {
            String formId = userFormId.get(user.getId());
            if (StringUtils.isEmpty(formId)) {
                log.error("user或者formId为null无法给用户推送消息user:{};content:{};formId:{}", user, content, formId);
                if (StringUtils.isNotEmpty(user.getMobile())) {
                    Boolean flag = SMSUtil.sendLeaveInform(user.getMobile(), content, Constant.WEIXN_JUMP_URL);
                    if (!flag) {
                        log.error("留言通知发送短信失败:user.getMobile:{};content:{};", user.getMobile(), content);
                    } else {
                        log.info("留言通知发送短信成功:user.getMobile:{};content:{};", user.getMobile(), content);
                    }
                }
            }
            return;
        }
    }

}