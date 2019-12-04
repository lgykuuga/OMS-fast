package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易订单快照 oms_trade_standard
 *
 * @author lgy
 * @date 2019-10-15
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_trade_standard")
public class StandardOrderData extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 平台交易单号 */
    private String tid;

    /** 交易修改时间 */
    private Date modified;

    /** 标准订单数据报文 */
    private String standard;

}
