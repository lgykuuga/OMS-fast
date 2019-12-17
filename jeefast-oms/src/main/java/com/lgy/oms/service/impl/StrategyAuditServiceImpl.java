package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.StrategyAudit;
import com.lgy.oms.domain.StrategyAuditShop;
import com.lgy.oms.mapper.StrategyAuditMapper;
import com.lgy.oms.mapper.StrategyAuditShopMapper;
import com.lgy.oms.service.IStrategyAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 审单策略 服务层实现
 *
 * @author lgy
 * @date 2019-12-17
 */
@Service
public class StrategyAuditServiceImpl extends ServiceImpl<StrategyAuditMapper, StrategyAudit> implements IStrategyAuditService {

    @Autowired
    protected StrategyAuditShopMapper shopMapper;

    @Override
    public List<StrategyAuditShop> getStrategyShop(String gco) {
        return shopMapper.getStrategyShop(gco);
    }

    @Override
    public boolean changeAuto(Long id, String auto) {
        return shopMapper.changeAuto(id, auto);
    }

    @Override
    public Integer deleteShopByGco(String gco) {
        if (StringUtils.isNotEmpty(gco)) {
            QueryWrapper wrapper = new QueryWrapper<>();
            wrapper.eq("gco", gco);
            return shopMapper.delete(wrapper);
        }
        return 0;
    }

    @Override
    public Integer deleteShopById(List<String> ids) {
        return shopMapper.deleteBatchIds(ids);
    }

    @Override
    public List<StrategyAuditShop> addLoadShop(String shopCode, String shopName, String gco, boolean enforce) {
        List<StrategyAuditShop> list;
        if (enforce) {
            //获取不在该策略的店铺
            list = shopMapper.getNotJoThisStrategyShop(shopCode, shopName, gco);
        } else {
            //获取未加入策略的店铺
            list = shopMapper.getNotJoThisStrategyShop(shopCode, shopName, null);
        }
        return list;
    }

    @Override
    public Integer saveStrategyShop(List<StrategyAuditShop> strategyShopList, boolean enforce) {
        //保存数量
        int count = 0;

        if (enforce) {
            for (StrategyAuditShop strategyShop : strategyShopList) {
                //删除选中店铺在其它策略的关系
                QueryWrapper queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("shop", strategyShop.getShop());
                shopMapper.delete(queryWrapper);

                //保存
                count += shopMapper.insert(strategyShop);
            }
        } else {
            for (StrategyAuditShop strategyShop : strategyShopList) {
                //保存
                count += shopMapper.insert(strategyShop);
            }
        }

        return count;
    }


    @Override
    public StrategyAudit getStrategyByShop(String shop) {
        return shopMapper.getStrategyByShop(shop);

    }
}