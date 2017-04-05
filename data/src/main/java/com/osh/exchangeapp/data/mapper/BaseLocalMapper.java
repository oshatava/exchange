package com.osh.exchangeapp.data.mapper;

import java.util.List;

/**
 * Created by oleg on 2/7/2017.
 */

public abstract class BaseLocalMapper<Entity, EntityLocal> {

    abstract public Entity fromLocal(EntityLocal u);
    abstract public EntityLocal toLocal(Entity u);

    public List<Entity> fromLocal(List<EntityLocal> ul){
        if(ul==null)
            return null;
        return CollectionMapper.map(ul, this::fromLocal);
    }

    public List<EntityLocal> toLocal(List<Entity> entities) {
        if(entities==null)
            return null;
        return CollectionMapper.map(entities, this::toLocal);
    }
}
