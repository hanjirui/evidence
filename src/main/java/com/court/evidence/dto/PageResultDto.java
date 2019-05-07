package com.court.evidence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResultDto<T> {
    private PageDto page;
    private List<T> result;

    public PageResultDto(int page, int pageSize, int total, List<T> result){
        this.page = PageDto.builder().page(page).pageSize(pageSize).total(total).build();
        this.result = result;
    }


}
