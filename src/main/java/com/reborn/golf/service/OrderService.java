package com.reborn.golf.service;

import com.reborn.golf.dto.shop.OrdersDto;
import com.reborn.golf.dto.common.PageRequestDto;
import com.reborn.golf.dto.common.PageResultDto;
import com.reborn.golf.entity.Orders;

public interface OrderService {

    PageResultDto<Orders, OrdersDto> getListWithUser(Integer memberIdx, PageRequestDto pageRequestDto);

    PageResultDto<Orders, OrdersDto> getList(PageRequestDto pageRequestDto);

    String order(Integer memberIdx, OrdersDto ordersDto);

//    Long orderFromCart(Integer memberIdx, CartListDto cartListDto);

    Long cancel(Integer memberIdx, Long orderIdx);

    default OrdersDto entityToDto(Orders orders){
        return OrdersDto.builder()
                .idx(orders.getIdx())
                .totalPrice(orders.getTotalPrice())
                .name(orders.getMember().getName())
                .email(orders.getMember().getEmail())
                .address(orders.getDelivery().getAddress())
                .orderState(orders.getOrderState().name())
                .deliveryStatus(orders.getDelivery().getDeliveryStatus().name())
                .orderProductList(orders.toOrderProductDto())
                .build();
    }

}