package com.court.evidence.rest.response;

import com.court.evidence.dto.PageDto;
import com.court.evidence.dto.PageResultDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private PageDto page;
    private T data;

    public BaseResponse(Object result){
        if(result instanceof PageResultDto){
            PageResultDto pageResultDto = (PageResultDto)result;
            page = pageResultDto.getPage();
            data = (T) pageResultDto.getResult();
        } else if(result instanceof Collection){
            data = (T) result;
        } else {
            data = (T) result;
        }
    }
}
