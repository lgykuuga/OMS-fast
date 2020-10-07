package com.lgy.oms.interfaces.rds.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单推送库表 jdp_tb_trade
 *
 * @author lgy
 * @date 2019-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jdp_tb_trade")
public class JdpTbTrade implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long tid;

    private String status;

    private String type;

    private String sellerNick;

    private String buyerNick;

    private Date created;

    private Date modified;

    private String jdpHashcode;

    private String jdpResponse;

    private Date jdpCreated;

    private Date jdpModified;

}
