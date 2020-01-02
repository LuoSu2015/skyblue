package com.cskaoyan.bean.systemManagement;

import lombok.Data;

import java.util.List;

@Data
public class SystemPermissionForFront {
    String id;

    String label;

    List<SystemPermissionForFront> children;
}
