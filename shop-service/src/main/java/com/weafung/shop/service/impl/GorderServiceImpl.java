package com.weafung.shop.service.impl;

import com.weafung.shop.common.constant.CodeEnum;
import com.weafung.shop.dao.GorderMapper;
import com.weafung.shop.model.dto.*;
import com.weafung.shop.model.po.Gorder;
import com.weafung.shop.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author weifengshih
 */
@Service
@Slf4j
public class GorderServiceImpl implements GorderService {

    @Autowired
    private AddressService addressService;
    @Autowired
    private SnowFlakeService snowFlakeService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private GorderMapper gorderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<Boolean> checkOut(String accountId, Long addressId, Set<OrderItemDTO> orderItemDTOSet) {
        if (StringUtils.isBlank(accountId) || Objects.isNull(addressId) || CollectionUtils.isEmpty(orderItemDTOSet)) {
            return ResponseDTO.build(CodeEnum.PARAM_EMPTY, Boolean.FALSE);
        }
        AddressDTO addressDTO = addressService.getAddress(accountId, addressId).getData();
        if (addressDTO == null) {
            return ResponseDTO.build(CodeEnum.ADDRESS_NOT_FOUND, Boolean.FALSE);
        }
        Long gorderId = snowFlakeService.nextGorderId();
        Long currentTimeMillis = System.currentTimeMillis();
        if (gorderMapper.insert(gorderId, accountId, addressId, currentTimeMillis) <= 0) {
            log.error("create gorder failed. accountId: {}, addredssId: {}, orderItems: {}",
                    accountId, addressId, orderItemDTOSet);
            throw new RuntimeException("create gorder failed");
        }
        for (OrderItemDTO orderItemDTO : orderItemDTOSet) {
            ResponseDTO<Boolean> orderResponseDTO = orderService.createOrder(accountId, gorderId,
                    orderItemDTO.getSkuId(), orderItemDTO.getCount());
            if (orderResponseDTO == null) {
                log.warn("create order failed. responseDTO is null. orderItemDTO:{}", orderItemDTO);
                throw new RuntimeException("create order failed. responseDTO is null.");
            } else if (Boolean.FALSE.equals(orderResponseDTO.getData())) {
                log.warn("create order failed. orderItemDTO:{}, responseDTO: {}", orderItemDTO, orderResponseDTO);
                throw new RuntimeException(orderResponseDTO.getMsg());
            }
            ResponseDTO<Boolean> shoppingCartResponseDTO = shoppingCartService.deleteGoods(accountId, orderItemDTO.getSkuId());
            if (shoppingCartResponseDTO == null) {
                log.warn("remove from shopping cart failed. responseDTO is null.");
            } else if (Boolean.FALSE.equals(shoppingCartResponseDTO.getData())) {
                log.warn("create order failed. orderItemDTO:{}, responseDTO: {}", orderItemDTO, shoppingCartResponseDTO);
            }
        }
        return ResponseDTO.buildSuccess(Boolean.TRUE);
    }

    @Override
    public ResponseDTO<List<GorderDetailDTO>> listGorderDetail(String accountId, Long gorderId, Integer status) {
        List<Gorder> gorderList = gorderMapper.listGorderPageByGorderIdAndStatus(accountId, gorderId, status);
        List<GorderDetailDTO> list = gorderList.stream().map(gorder -> {
            GorderDetailDTO gorderDetailDTO = new GorderDetailDTO();

            GorderDTO gorderDTO = new GorderDTO();
            BeanUtils.copyProperties(gorder, gorderDTO);

            List<SorderDTO> sorderDTOList = orderService.listSorderByGorderId(gorderDTO.getGorderId());
            gorderDetailDTO.setSorderDTOList(sorderDTOList);

            long money = 0L;
            int count = 0;
            for (SorderDTO sorderDTO : sorderDTOList) {
                money += sorderDTO.getMoney();
                count += sorderDTO.getCount();
            }
            gorderDTO.setCount(count);
            gorderDTO.setMoney(money);
            gorderDetailDTO.setGorderDTO(gorderDTO);

            return gorderDetailDTO;
        }).collect(Collectors.toList());
        return ResponseDTO.buildSuccess(list);
    }
}
