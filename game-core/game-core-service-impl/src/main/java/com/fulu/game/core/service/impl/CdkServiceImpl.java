package com.fulu.game.core.service.impl;

import com.fulu.game.core.dao.CdkDao;
import com.fulu.game.core.dao.ICommonDao;
import com.fulu.game.core.entity.Cdk;
import com.fulu.game.core.entity.vo.CdkVO;
import com.fulu.game.core.service.CdkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CdkServiceImpl extends AbsCommonService<Cdk, Integer> implements CdkService {

    @Autowired
    private CdkDao cdkDao;

    @Override
    public ICommonDao<Cdk, Integer> getDao() {
        return cdkDao;
    }


    @Override
    public Cdk findBySeries(String series) {
        CdkVO param = new CdkVO();
        param.setSeries(series);
        List<Cdk> cdkList = cdkDao.findByParameter(param);
        if(cdkList.isEmpty()){
            return null;
        }
        return cdkList.get(0);
    }
}
