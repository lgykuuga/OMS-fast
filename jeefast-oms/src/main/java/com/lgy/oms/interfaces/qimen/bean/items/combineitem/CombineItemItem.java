package com.lgy.oms.interfaces.qimen.bean.items.combineitem;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 同步的商品信息
 *
 * @author LGy
 */
@XStreamAlias("item")
public class CombineItemItem {

    /**
     * 商品编码
     */
    private String itemCode;
    /**
     * 仓储系统商品编码, string (50) , 条件必填, 条件为商品同步接口, 出参itemId不为空
     */
    private String itemId;
    /**
     * 组合商品中的该商品个数
     */
    private Double quantity;
    /**
     * 货主
     */
    private String ownerCode;
    /**
     * 奇门仓储字段
     */
    private String sn;
    /**
     * 商品货号
     */
    private String goodsCode;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商品简称
     */
    private String shortName;
    /**
     * 英文名
     */
    private String englishName;
    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 条形码
     */
    private String barCode;
    /**
     * 商品属性 (如红色, XXL)
     */
    private String skuProperty;
    /**
     * 商品计量单位
     */
    private String stockUnit;
    /**
     * 长 (厘米)
     */
    private String length;
    /**
     * 宽 (厘米)
     */
    private String width;
    /**
     * 高 (厘米)
     */
    private String height;
    /**
     * 体积 (升)
     */
    private String volume;
    /**
     * 毛重 (千克)
     */
    private String grossWeight;
    /**
     * 净重 (千克)
     */
    private String netWeight;
    /**
     * 颜色,
     */
    private String color;
    /**
     * 尺寸
     */
    private String size;
    /**
     * 渠道中的商品标题
     */
    private String title;
    /**
     * 商品类别ID
     */
    private String categoryId;
    /**
     * 商品类别名称
     */
    private String categoryName;
    /**
     * 计价货类
     */
    private String pricingCategory;
    /**
     * 安全库存
     */
    private Long safetyStock;
    /**
     * 商品类型 (ZC=正常商品, FX=分销商品, ZH=组合商品, ZP=赠品, BC=包材, HC=耗材, FL=辅料, XN=虚拟品, FS=附属品, CC=残次品, OTHER=其它) ,
     * , 必填,  (只传英文编码)
     */
    private String itemType;
    /**
     * 吊牌价
     */
    private Double tagPrice;
    /**
     * 零售价
     */
    private Double retailPrice;
    /**
     * 成本价
     */
    private Double costPrice;
    /**
     * 采购价
     */
    private Double purchasePrice;
    /**
     * 季节编码
     */
    private String seasonCode;
    /**
     * 季节名称
     */
    private String seasonName;
    /**
     * 品牌代码
     */
    private String brandCode;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 是否需要串号管理, Y/N (默认为N)
     */
    private String isSNMgmt;
    /**
     * 生产日期, string (10) , YYYY-MM-DD
     */
    private String productDate;
    /**
     * 过期日期, string (10) , YYYY-MM-DD
     */
    private String expireDate;
    /**
     * 是否需要保质期管理, Y/N (默认为N)
     */
    private String isShelfLifeMgmt;
    /**
     * 保质期 (小时) , int
     */
    private String shelfLife;
    /**
     * 保质期禁收天数
     */
    private String rejectLifecycle;
    /**
     * 保质期禁售天数
     */
    private String lockupLifecycle;
    /**
     * 保质期临期预警天数
     */
    private String adventLifecycle;
    /**
     * 是否需要批次管理, Y/N (默认为N)
     */
    private String isBatchMgmt;
    /**
     * 批次代码
     */
    private String batchCode;
    /**
     * 批次备注,
     */
    private String batchRemark;
    /**
     * 包装代码
     */
    private String packCode;
    /**
     * 箱规
     */
    private String pcs;
    /**
     * 商品的原产地,
     */
    private String originAddress;
    /**
     * 批准文号
     */
    private String approvalNumber;
    /**
     * 是否易碎品, Y/N,  (默认为N)
     */
    private String isFragile;
    /**
     * 是否危险品, Y/N,  (默认为N)
     */
    private String isHazardous;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间 string (19) , YYYY-MM-DD HH:MM:SS
     */
    private String createTime;
    /**
     * 更新时间 string (19) , YYYY-MM-DD HH:MM:SS
     */
    private String updateTime;
    /**
     * 是否有效, Y/N (默认为Y)
     */
    private String isValid;
    /**
     * 是否sku, bool,  (默认为Y)
     */
    private String isSku;
    /**
     * 商品包装材料类型, string (200)
     */
    private String packageMaterial;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSkuProperty() {
        return skuProperty;
    }

    public void setSkuProperty(String skuProperty) {
        this.skuProperty = skuProperty;
    }

    public String getStockUnit() {
        return stockUnit;
    }

    public void setStockUnit(String stockUnit) {
        this.stockUnit = stockUnit;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPricingCategory() {
        return pricingCategory;
    }

    public void setPricingCategory(String pricingCategory) {
        this.pricingCategory = pricingCategory;
    }

    public Long getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(Long safetyStock) {
        this.safetyStock = safetyStock;
    }

    public String getItemType() {
        if (itemType == null) {
            return "ZC";
        }
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Double getTagPrice() {
        if (tagPrice == null) {
            return 0.0;
        }
        return tagPrice;
    }

    public void setTagPrice(Double tagPrice) {
        this.tagPrice = tagPrice;
    }

    public Double getRetailPrice() {
        if (retailPrice == null) {
            return 0.0;
        }
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Double getCostPrice() {
        if (costPrice == null) {
            return 0.0;
        }
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getPurchasePrice() {
        if (purchasePrice == null) {
            return 0.0;
        }
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getIsSNMgmt() {
        return isSNMgmt;
    }

    public void setIsSNMgmt(String isSNMgmt) {
        this.isSNMgmt = isSNMgmt;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getIsShelfLifeMgmt() {
        return isShelfLifeMgmt;
    }

    public void setIsShelfLifeMgmt(String isShelfLifeMgmt) {
        this.isShelfLifeMgmt = isShelfLifeMgmt;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public String getRejectLifecycle() {
        return rejectLifecycle;
    }

    public void setRejectLifecycle(String rejectLifecycle) {
        this.rejectLifecycle = rejectLifecycle;
    }

    public String getLockupLifecycle() {
        return lockupLifecycle;
    }

    public void setLockupLifecycle(String lockupLifecycle) {
        this.lockupLifecycle = lockupLifecycle;
    }

    public String getAdventLifecycle() {
        return adventLifecycle;
    }

    public void setAdventLifecycle(String adventLifecycle) {
        this.adventLifecycle = adventLifecycle;
    }

    public String getIsBatchMgmt() {
        return isBatchMgmt;
    }

    public void setIsBatchMgmt(String isBatchMgmt) {
        this.isBatchMgmt = isBatchMgmt;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getBatchRemark() {
        return batchRemark;
    }

    public void setBatchRemark(String batchRemark) {
        this.batchRemark = batchRemark;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public String getPcs() {
        return pcs;
    }

    public void setPcs(String pcs) {
        this.pcs = pcs;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getApprovalNumber() {
        return approvalNumber;
    }

    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    public String getIsFragile() {
        return isFragile;
    }

    public void setIsFragile(String isFragile) {
        this.isFragile = isFragile;
    }

    public String getIsHazardous() {
        return isHazardous;
    }

    public void setIsHazardous(String isHazardous) {
        this.isHazardous = isHazardous;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getIsSku() {
        return isSku;
    }

    public void setIsSku(String isSku) {
        this.isSku = isSku;
    }

    public String getPackageMaterial() {
        return packageMaterial;
    }

    public void setPackageMaterial(String packageMaterial) {
        this.packageMaterial = packageMaterial;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

}
