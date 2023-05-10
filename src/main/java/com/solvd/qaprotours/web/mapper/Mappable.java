package com.solvd.qaprotours.web.mapper;

import java.util.List;

public interface Mappable<E, D> {

    /**
     * Maps DTO to entity.
     *
     * @param d DTO object
     * @return entity object
     */
    E toEntity(D d);

    /**
     * Maps a list of DTO to list of entity.
     *
     * @param d list of DTO object
     * @return list of entity object
     */
    List<E> toEntity(List<D> d);

    /**
     * Maps entity to DTO.
     *
     * @param e entity object
     * @return DTO object
     */
    D toDto(E e);

    /**
     * Maps a list of entity to DTO.
     *
     * @param e list of entity object
     * @return list of DTO object
     */
    List<D> toDto(List<E> e);

}
