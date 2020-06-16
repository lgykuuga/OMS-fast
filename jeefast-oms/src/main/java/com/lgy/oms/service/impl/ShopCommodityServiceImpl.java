package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.base.domain.Shop;
import com.lgy.base.service.IShopService;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.DateUtils;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.oms.domain.ShopCommodity;
import com.lgy.oms.mapper.ShopCommodityMapper;
import com.lgy.oms.service.IShopCommodityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 铺货关系 服务层实现
 *
 * @author lgy
 * @date 2019-10-22
 */
@Service
public class ShopCommodityServiceImpl extends ServiceImpl<ShopCommodityMapper, ShopCommodity> implements IShopCommodityService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IShopService shopService;

    @Override
    public CommonResponse<String> importShopCommodity(List<ShopCommodity> shopCommodities,
                                                      boolean updateSupport, String operName) {
        if (StringUtils.isNull(shopCommodities)) {
            return new CommonResponse<String>().error(Constants.FAIL, "导入的数据为空。");
        }

        //错误信息
        StringBuilder failureMsg = new StringBuilder();

        //excel行,用于提示错误信息
        int i = 1;

        //检测是否存在相同数据
        Set<String> set = new HashSet<>();
        int setSize = 0;

        /**
         * 校验数据信息
         */
        for (ShopCommodity shopCommodity : shopCommodities) {

            if (StringUtils.isEmpty(shopCommodity.getShop())) {
                failureMsg.append("第").append(i).append("行店铺为空");
                continue;
            }

            //判断数据是否重复key
            String key = shopCommodity.getShop() + shopCommodity.getNumIid() + shopCommodity.getSkuId();

            QueryWrapper<Shop> queryShopWrapper = new QueryWrapper<>();
            queryShopWrapper.lambda().eq(Shop::getGco, shopCommodity.getShop());
            Shop shop = shopService.getOne(queryShopWrapper);
            if (StringUtils.isNull(shop)) {
                failureMsg.append("第").append(i).append("行店铺编码无效");
                continue;
            }

            //不更新已存在铺货关系,校验本条数据是否存在数据库
            if (!updateSupport) {
                QueryWrapper<ShopCommodity> queryWrapper = new QueryWrapper<>();
                //店铺编码
                queryWrapper.lambda().eq(ShopCommodity::getShop, shopCommodity.getShop());
                //商品数字ID
                if (StringUtils.isEmpty(shopCommodity.getNumIid())) {
                    queryWrapper.lambda().eq(ShopCommodity::getNumIid, shopCommodity.getNumIid());
                }
                //平台skuID
                if (StringUtils.isEmpty(shopCommodity.getSkuId())) {
                    queryWrapper.lambda().eq(ShopCommodity::getSkuId, shopCommodity.getSkuId());
                }
                List<ShopCommodity> list = this.list(queryWrapper);
                if (list != null && list.size() > 0) {
                    failureMsg.append("第").append(i).append("行铺货关系已存在系统,请勿重复导入");
                    continue;
                }

            }

            //设置店铺货主
            shopCommodity.setOwner(shop.getOwner());
            //操作人
            shopCommodity.setCreateBy(operName);
            //操作时间
            shopCommodity.setCreateTime(DateUtils.getNowDate());

            if (!ExcelUtil.checkIsSame(set, key, setSize)) {
                failureMsg.append("第").append(i).append("行同一店铺、商品数字ID、skuID在Excel存在多条");
                continue;
            } else {
                setSize = set.size();
            }

            i++;
        }

        if (StringUtils.isNotEmpty(failureMsg.toString())) {
            logger.error("铺货关系导入失败:[{}]", failureMsg.toString());
            return new CommonResponse<String>().error(Constants.FAIL, failureMsg.toString());
        }

        /**
         * 保存数据信息
         */
        boolean saveBatch = true;
        if (updateSupport) {
            for (ShopCommodity shopCommodity : shopCommodities) {
                QueryWrapper<ShopCommodity> queryWrapper = new QueryWrapper<>();
                //店铺编码
                queryWrapper.lambda().eq(ShopCommodity::getShop, shopCommodity.getShop());
                //商品数字ID
                if (StringUtils.isEmpty(shopCommodity.getNumIid())) {
                    queryWrapper.lambda().eq(ShopCommodity::getNumIid, shopCommodity.getNumIid());
                }
                //平台skuID
                if (StringUtils.isEmpty(shopCommodity.getSkuId())) {
                    queryWrapper.lambda().eq(ShopCommodity::getSkuId, shopCommodity.getSkuId());
                }
                saveBatch = this.saveOrUpdate(shopCommodity, queryWrapper);
            }
        } else {
            saveBatch = this.saveBatch(shopCommodities);
        }

        if (saveBatch) {
            logger.info("铺货关系导入成功");
            return new CommonResponse<String>().ok("铺货关系导入成功");
        }
        logger.error("铺货关系导入保存失败");
        return new CommonResponse<String>().error(Constants.FAIL, "铺货关系导入保存失败");
    }

    @Override
    public CommonResponse<ShopCommodity> getShopCommodityByOrder(String shop, String numIid, String skuId,
                                                                 String outerIid, String outerSkuId) {

        ShopCommodity shopCommodity;

        QueryWrapper<ShopCommodity> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .lambda()
                .eq(ShopCommodity::getShop, shop)
                .eq(ShopCommodity::getStatus, Constants.VALID);

        //用商家编码和商家sku匹配商品
        if (StringUtils.isNotEmpty(numIid)) {
            queryWrapper.lambda().eq(ShopCommodity::getNumIid, numIid);
        }
        if (StringUtils.isNotEmpty(skuId)) {
            queryWrapper.lambda().eq(ShopCommodity::getSkuId, skuId);
        }
        try {
            shopCommodity = this.getOne(queryWrapper);
        } catch (Exception e) {
            logger.error("存在多条相同有效的铺货关系");
            return new CommonResponse<ShopCommodity>().error(Constants.FAIL, "存在多条相同有效的铺货关系");
        }
        return new CommonResponse<ShopCommodity>().ok(shopCommodity);
    }
}