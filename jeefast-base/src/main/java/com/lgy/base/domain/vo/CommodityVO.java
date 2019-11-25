package com.lgy.base.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品档案VO对象
 *
 * @author lgy
 * @date 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommodityVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 商品编码
     */
    private String gco;

    /**
     * 商品名称
     */
    private String gna;

    /**
     * 组合商品
     */
    private String combo;

    /**
     * 缩略图url
     */
    private String imgUrl;

    /**
     * 货主编码
     */
    private String owner;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

}
