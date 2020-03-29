package com.timecash.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: TODO
 * @Author:tanghongwei@jumaps.com
 * @Date:2019-12-05
 * @Copyright: 2019 juma.com All Rights Reserved
 */
@Data
public class ApplyInfo implements Serializable {

    private Long id;

    private String usrName;

    private String phoneNum;

    private String email;

    private String addr;

    private String remark;

    private Date createTime;
}
