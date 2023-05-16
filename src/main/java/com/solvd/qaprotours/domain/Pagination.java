package com.solvd.qaprotours.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pagination {

    private Integer currentPage;
    private Integer pageSize;

}
