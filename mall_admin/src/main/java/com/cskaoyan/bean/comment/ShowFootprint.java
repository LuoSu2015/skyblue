package com.cskaoyan.bean.comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowFootprint {
    private Integer id;

    private String name;

    private String brief;

    private String picUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    private Integer goodsId;

    private BigDecimal retailPrice;
}