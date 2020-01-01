package com.cskaoyan.bean.comment;

import lombok.Data;


@Data
public class CommentReceive {
    private String mobile;
    private String feedType;
    private String content;
    private Boolean hasPicture;
    String[] picUrls;
}
