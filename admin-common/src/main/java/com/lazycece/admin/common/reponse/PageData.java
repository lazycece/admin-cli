package com.lazycece.admin.common.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lazycece
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageData<T> {
    /**
     * current page
     */
    private Integer page;
    /**
     * total records
     */
    private Long count;
    /**
     * current page data
     */
    private T data;
}
