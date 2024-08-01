package com.liveCodeTodoList.liveCodeTodo.utils.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PaginationResponse<T> {
    private List<T> items;
    private Long totalItems;
//    private Integer size;
    private Integer totalPages;
    private Integer currentPage;

    public PaginationResponse(Page<T> page) {
        this.items = page.getContent();
        this.totalItems = page.getTotalElements();
//        this.size = page.getSize();
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
    }
}
