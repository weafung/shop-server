package com.weafung.shop.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author weifengshih
 */
@Data
public class CheckOutDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long addressId;

    private Set<OrderItemDTO> orderItems;
}
