package com.fulu.game.core.service;

import com.fulu.game.core.entity.MoneyDetails;
import com.fulu.game.core.entity.vo.MoneyDetailsVO;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author yanbiao
 * @date 2018-04-25 14:59:54
 */
@Transactional
public interface MoneyDetailsService extends ICommonService<MoneyDetails,Integer>{

    PageInfo<MoneyDetailsVO> listByAdmin(MoneyDetailsVO moneyDetailsVO, Integer pageSize, Integer pageNum);
    PageInfo<MoneyDetailsVO> listByUser(MoneyDetailsVO moneyDetailsVO, Integer pageSize, Integer pageNum);

    /**
     * 入款-管理员加零钱
     * @param moneyDetailsVO
     * @return
     */
    MoneyDetails save(MoneyDetailsVO moneyDetailsVO);

    /**
     * 入款-完成陪玩订单
     * @param moneyDetailsVO
     * @return
     */
    MoneyDetails orderSave(MoneyDetailsVO moneyDetailsVO);

    /**
     * 提款-生成提款申请单后调用该接口
     * @return
     */
    MoneyDetails drawSave(MoneyDetails moneyDetails);


}
