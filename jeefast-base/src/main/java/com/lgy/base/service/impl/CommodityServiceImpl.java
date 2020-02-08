package com.lgy.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.base.constant.BaseConstants;
import com.lgy.base.domain.Commodity;
import com.lgy.base.mapper.CommodityMapper;
import com.lgy.base.service.ICommodityService;
import com.lgy.common.constant.Constants;
import com.lgy.common.exception.BusinessException;
import com.lgy.common.utils.StringUtils;
import com.lgy.system.domain.vo.Config;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品档案 服务层实现
 *
 * @author lgy
 * @date 2019-10-09
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {

    /**
     * redis cache value
     */
    private final String CACHE_NAMES = "commodity";

    @Override
    public String importData(List<Commodity> commoditys, boolean updateSupport, String operName) {
        if (StringUtils.isNull(commoditys) || commoditys.size() == 0) {
            throw new BusinessException("导入商品档案数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Commodity commodity : commoditys) {
            try {
                //校验必填信息
                String checkInfo = checkInfo(commodity);
                if (StringUtils.isNotEmpty(checkInfo)) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、商品 " + commodity.getGco() + checkInfo);
                }
                //验证商品档案是否存在
                QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("gco", commodity.getGco());
                Commodity one = baseMapper.selectOne(queryWrapper);
                if (updateSupport) {
                    //支持更新
                    if (one != null) {
                        baseMapper.update(commodity, queryWrapper);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、商品 " + commodity.getGco() + " 更新成功");
                    } else {
                        this.save(commodity);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、商品 " + commodity.getGco() + " 导入成功");
                    }
                } else {
                    if (one != null) {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、商品 " + commodity.getGco() + " 已存在");
                    } else {
                        this.save(commodity);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、商品 " + commodity.getGco() + " 导入成功");
                    }
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、商品 " + commodity.getGco() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public Commodity getOne(String gco) {
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("gco", gco);
        queryWrapper.eq("status", Constants.VALID);
        return getOne(queryWrapper);
    }

    @Override
    public List<Config> selectCommodity() {
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", BaseConstants.NORMAL);
        List<Commodity> list = baseMapper.selectList(queryWrapper);

        List<Config> configs = new ArrayList<>(list.size());
        for (Commodity commodity : list) {
            configs.add(new Config(commodity.getGco(), commodity.getGna()));
        }
        return configs;
    }

    /**
     * 校验商品信息
     *
     * @param commodity
     * @return
     */
    private String checkInfo(Commodity commodity) {
        if (StringUtils.isEmpty(commodity.getGco())) {
            return "商品编码不能为空";
        }
        if (StringUtils.isEmpty(commodity.getGna())) {
            return "商品名称不能为空";
        }
        if (StringUtils.isEmpty(commodity.getOwner())) {
            return "商品货主不能为空";
        }
        return "";
    }

    @Override
    @CachePut(value = CACHE_NAMES, key = "#entity.gco")
    public Commodity add(Commodity entity) {
        boolean b = super.save(entity);
        if (b) {
            return entity;
        }
        return null;
    }

    @Override
    @CacheEvict(value = CACHE_NAMES, key = "#gco")
    public boolean delete(String gco) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gco", gco);
        return this.remove(queryWrapper);
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAMES, key = "#gco")
    public Commodity findOne(String gco) {
        return getOne(gco);
    }

    @Override
    @CachePut(value = CACHE_NAMES, key = "#entity.gco")
    public Commodity update(Commodity entity) {
        boolean b = super.updateById(entity);
        if (b) {
            return entity;
        }
        return null;
    }
}