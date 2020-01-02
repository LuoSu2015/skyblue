package com.cskaoyan.service.systemmanagement;

        import com.cskaoyan.bean.Storage;

        import java.util.List;

public interface StorageService {

    List<Storage> queryStorages(int page, int limit, String sort, String desc, String key, String name);

    int changeStorageById(Storage storage);

    int deleteStorage(Storage storage);
}
