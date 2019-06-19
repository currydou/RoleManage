package com.example.administrator.rolemanage.base.basecontractT;

public class BaseRepository implements IDataSource {
    /*protected IRepositoryManager mRepositoryManager;//用于管理网络请求层,以及数据缓存层

    public BaseRepository(IRepositoryManager repositoryManager) {
        this.mRepositoryManager = repositoryManager;
    }
*/
    @Override
    public void onDestroy() {
        //mRepositoryManager = null;
    }
}