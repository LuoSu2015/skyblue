package com.cskaoyan.service.systemManagement;

import com.cskaoyan.bean.Storage;
import com.cskaoyan.bean.StorageExample;
import com.cskaoyan.mapper.StorageMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    StorageMapper storageMapper;

    @Override
    public List<Storage> queryStorages(int page, int limit, String sort, String order, String key, String name) {
        PageHelper.startPage(page,limit);
        StorageExample storageExample = new StorageExample();
        StorageExample.Criteria criteria = storageExample.createCriteria();
        if(key != null){
            criteria.andKeyLike("%" + key + "%");
        }
        if(name != null){
            criteria.andNameLike("%"+name+"%");
        }
        criteria.andDeletedEqualTo(false);
        storageExample.setOrderByClause(sort+" "+order);

        List<Storage> storageList = storageMapper.selectByExample(storageExample);
        return storageList;
    }

    @Override
    public int changeStorageById(Storage storage) {
        int i = storageMapper.updateByPrimaryKey(storage);
        return i;


    }

    @Override
    public int deleteStorage(Storage storage) {
        StorageExample storageExample = new StorageExample();
        StorageExample.Criteria criteria = storageExample.createCriteria();
        criteria.andIdEqualTo(storage.getId());
        storage.setDeleted(true);
        int update = storageMapper.updateByExampleSelective(storage, storageExample);
        return update;

    }
}
