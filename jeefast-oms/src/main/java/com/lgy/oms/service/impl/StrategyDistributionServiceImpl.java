package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.*;
import com.lgy.oms.mapper.StrategyDistributionMapper;
import com.lgy.oms.mapper.StrategyDistributionShopMapper;
import com.lgy.oms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配货策略 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionServiceImpl extends ServiceImpl<StrategyDistributionMapper, StrategyDistribution> implements IStrategyDistributionService {

    @Autowired
    StrategyDistributionShopMapper shopMapper;

    @Autowired
    IStrategyDistributionPreService preService;

    @Autowired
    IStrategyDistributionSpecialService specialService;

    @Autowired
    IStrategyDistributionCategoryService categoryService;

    @Autowired
    IStrategyDistributionWarehouseSkuService warehouseSkuService;

    @Autowired
    IStrategyDistributionWarehouseRuleService warehouseRuleService;


    @Override
    public List<StrategyDistributionShop> getStrategyShop(String gco) {
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
    public List<StrategyDistributionShop> addLoadShop(String shopCode, String shopName, String gco, boolean enforce) {
        List<StrategyDistributionShop> list;
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
    public Integer saveStrategyShop(List<StrategyDistributionShop> strategyShopList, boolean enforce) {
        //保存数量
        int count = 0;

        if (enforce) {
            for (StrategyDistributionShop strategyShop : strategyShopList) {
                //删除选中店铺在其它策略的关系
                QueryWrapper queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("shop", strategyShop.getShop());
                shopMapper.delete(queryWrapper);

                //保存
                count += shopMapper.insert(strategyShop);
            }
        } else {
            for (StrategyDistributionShop strategyShop : strategyShopList) {
                //保存
                count += shopMapper.insert(strategyShop);
            }
        }

        return count;
    }


    @Override
    public StrategyDistribution getStrategyByShop(String shop) {
        return shopMapper.getStrategyByShop(shop);
    }

    @Override
    public StrategyDistribution getFullInfoStrategyByShop(String shop) {
        StrategyDistribution strategy = getStrategyByShop(shop);
        if (strategy != null) {
            //获取对应信息配置
            List<StrategyDistributionPre> pres = preService.getStrategyByGco(strategy.getGco());
            if (pres != null && !pres.isEmpty()) {
                strategy.setDistributionPreList(pres);
            }
            List<StrategyDistributionSpecial> specials = specialService.getStrategyByGco(strategy.getGco());
            if (specials != null && !specials.isEmpty()) {
                strategy.setDistributionSpecialList(specials);
            }
            List<StrategyDistributionCategory> categories = categoryService.getStrategyByGco(strategy.getGco());
            if (categories != null && !categories.isEmpty()) {
                strategy.setDistributionCategoryList(categories);
            }
            List<StrategyDistributionWarehouseSku> warehouseSku = warehouseSkuService.getStrategyByGco(strategy.getGco());
            if (warehouseSku != null && !warehouseSku.isEmpty()) {
                strategy.setDistributionWarehouseSkuList(warehouseSku);
            }
            List<StrategyDistributionWarehouseRule> warehouseRules = warehouseRuleService.getStrategyByGco(strategy.getGco());
            if (warehouseRules != null && !warehouseRules.isEmpty()) {
                strategy.setDistributionWarehouseRules(warehouseRules);
            }
            return strategy;
        }
        return null;
    }

}