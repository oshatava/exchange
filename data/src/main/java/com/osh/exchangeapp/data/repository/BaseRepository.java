package com.osh.exchangeapp.data.repository;

import com.osh.exchangeapp.data.local.HasLocalRepository;
import com.osh.exchangeapp.data.local.LocalRepository;
import com.osh.exchangeapp.data.remote.HasRemoteRepository;
import com.osh.exchangeapp.data.remote.RemoteRepository;

/**
 * Created by oleg on 2/6/2017.
 */

public class BaseRepository<LocalRepositoryClass extends LocalRepository, RemoteRepositoryClass extends RemoteRepository> implements
        HasLocalRepository<LocalRepositoryClass>,
        HasRemoteRepository<RemoteRepositoryClass> {


    private LocalRepositoryClass localRepository;
    private RemoteRepositoryClass remoteRepository;


    public BaseRepository(LocalRepositoryClass localRepository, RemoteRepositoryClass remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    @Override
    public LocalRepositoryClass getLocalRepository() {
        return localRepository;
    }

    @Override
    public RemoteRepositoryClass getRemoteRepository() {
        return remoteRepository;
    }




}
