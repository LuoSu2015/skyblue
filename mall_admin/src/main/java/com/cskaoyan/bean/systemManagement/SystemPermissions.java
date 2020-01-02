package com.cskaoyan.bean.systemManagement;


import lombok.Data;

import java.util.List;

@Data
public class SystemPermissions {
    Integer primaryId;

    String id;

    String label;

    Integer pid;

    String api;

    List<SystemPermissions> children;
}
