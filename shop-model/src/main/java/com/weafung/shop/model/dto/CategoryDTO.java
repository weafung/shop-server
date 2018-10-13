package com.weafung.shop.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author weifeng
 */
@Data
public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long categoryId;

    private Long parentId;

    private String title;

    private String image;

    private List<CategoryDTO> children;
}
