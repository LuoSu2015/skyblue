package com.cskaoyan.service;

import com.cskaoyan.bean.Storage;
import com.cskaoyan.mapper.GoodsMapper;
import com.cskaoyan.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxStorageServiceImpl implements WxStorageService{
    @Autowired
    StorageMapper storageMapper;
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public Storage addWxPic(Storage storage) {
        int i = 0;
        storageMapper.addWxStorage(storage);
        Integer id = storage.getId();
        Storage temp = storageMapper.getStorageById(id);
        storage.setAddTime(temp.getAddTime());
        storage.setUpdateTime(temp.getUpdateTime());
        storage.setDeleted(temp.getDeleted());
        return storage;
    }
}
