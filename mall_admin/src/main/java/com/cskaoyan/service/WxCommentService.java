package com.cskaoyan.service;

import com.cskaoyan.bean.Feedback;
import com.cskaoyan.bean.Region;
import com.cskaoyan.bean.User;
import com.cskaoyan.bean.comment.*;

import java.util.List;
import java.util.Map;

public interface WxCommentService {
       Map selectFootprint(Integer page, Integer size, User user);

       List<ShowAddress> selectAddress(User user);

       int insertAddress(User user, SaveAddress saveAddress);

       List<Region> selectRegion(Integer pid);

       int deleteAddress(Integer pid);

       AddressDetail selectAddressById(Integer id);

       int insertFeedback(Feedback feedback, User user);

}
