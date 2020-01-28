package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.StrategyConvertShop;
import com.lgy.oms.domain.vo.StrategyConvertVO;
import com.lgy.oms.mapper.StrategyConvertShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.StrategyConvertMapper;
import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.service.IStrategyConvertService;

import java.util.List;

/**
 * 转单策略 服务层实现
 *
 * @author lgy
 * @date 2019-10-31
 */
@Service
public class StrategyConvertServiceImpl extends ServiceImpl<StrategyConvertMapper, StrategyConvert> implements IStrategyConvertService {

    @Autowired
    StrategyConvertShopMapper shopMapper;
    @Autowired
    StrategyConvertMapper convertMapper;

    @Override
    public List<StrategyConvertShop> getConvertShop(String gco) {
        return shopMapper.getConvertShop(gco);
    }

    @Override
    public boolean changeAuto(Long id, String auto) {
        return shopMapper.changeAuto(id, auto);
    }

    @Override
    public Integer deleteConvertShopByGco(String gco) {
        if (StringUtils.isNotEmpty(gco)) {
            QueryWrapper<StrategyConvertShop> wrapper = new QueryWrapper<>();
            wrapper.eq("gco", gco);
            return shopMapper.delete(wrapper);
        }
        return 0;
    }

    @Override
    public Integer deleteConvertShopById(List<String> ids) {
        return shopMapper.deleteBatchIds(ids);
    }

    @Override
    public List<StrategyConvertShop> addLoadShop(String shopCode, String shopName, String gco, boolean enforce) {
        List<StrategyConvertShop> list = null;
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
    public Integer saveStrategyConvertShop(List<StrategyConvertShop> strategyConvertShopList, boolean enforce) {
        //保存数量
        int count = 0;

        if (enforce) {
            for (StrategyConvertShop strategyConvertShop : strategyConvertShopList) {
                //删除选中店铺在其它策略的关系
                QueryWrapper<StrategyConvertShop> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("shop", strategyConvertShop.getShop());
                shopMapper.delete(queryWrapper);

                //保存
                count += shopMapper.insert(strategyConvertShop);
            }
        } else {
            for (StrategyConvertShop strategyConvertShop : strategyConvertShopList) {
                //保存
                count += shopMapper.insert(strategyConvertShop);
            }
        }

        return count;
    }


    @Override
    public StrategyConvert getStrategyByShop(String shop) {
        return shopMapper.getStrategyByShop(shop);

    }

    @Override
    public List<StrategyConvertVO> queryList(StrategyConvertVO strategyConvertVO) {
        return convertMapper.queryList(strategyConvertVO);
    }
}