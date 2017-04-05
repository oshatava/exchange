package com.osh.exchangeapp.data.repository;

import com.osh.exchangeapp.data.local.HasLocalRepository;
import com.osh.exchangeapp.data.local.LocalRepository;
import com.osh.exchangeapp.data.remote.HasRemoteRepository;
import com.osh.exchangeapp.data.remote.RemoteRepository;

/**
 * Created by oleg on 2/6/2017.
 */

public class BaseRepositoryOnlyLocal<LocalRepositoryClass extends LocalRepository> implements HasLocalRepository<LocalRepositoryClass>{


    private LocalRepositoryClass localRepository;


    public BaseRepositoryOnlyLocal(LocalRepositoryClass localRepository) {
        this.localRepository = localRepository;
    }

    @Override
    public LocalRepositoryClass getLocalRepository() {
        return localRepository;
    }


}
