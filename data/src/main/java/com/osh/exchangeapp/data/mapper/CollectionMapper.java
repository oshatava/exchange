package com.osh.exchangeapp.data.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleg on 2/7/2017.
 */

public class CollectionMapper {
    public static <ClassTo, ClassFrom> List<ClassTo> map(Iterable<ClassFrom> in, Mapper<ClassFrom, ClassTo> mapper) {
        List<ClassTo> ret = new ArrayList<ClassTo>();
        if (mapper != null && in!=null) {
            for (ClassFrom from : in) {
                ret.add(mapper.map(from));
            }
        }
        return ret;
    }

    public static <ClassTo, ClassFrom> List<ClassTo> map(List<ClassTo> out,
                                                         Iterable<ClassFrom> in,
                                                         Mapper<ClassFrom, ClassTo> mapper) {
        if (mapper != null && in != null) {
            for (ClassFrom from : in) {
                out.add(mapper.map(from));
            }
        }
        return out;
    }

    public interface Mapper<ClassFrom, ClassTo> {
        ClassTo map(ClassFrom from);
    }
}
