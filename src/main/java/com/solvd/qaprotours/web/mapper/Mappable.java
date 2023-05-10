package com.solvd.qaprotours.web.mapper;

import java.util.List;

public interface Mappable<X, Y> {

    /**
     * Maps DTO to entity.
     *
     * @param y DTO object
     * @return entity object
     */
    X toEntity(Y y);

    /**
     * Maps a list of DTO to list of entity.
     *
     * @param y list of DTO object
     * @return list of entity object
     */
    List<X> toEntity(List<Y> y);

    /**
     * Maps entity to DTO.
     *
     * @param x entity object
     * @return DTO object
     */
    Y toDto(X x);

    /**
     * Maps a list of entity to DTO.
     *
     * @param x list of entity object
     * @return list of DTO object
     */
    List<Y> toDto(List<X> x);

}
