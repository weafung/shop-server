package com.weafung.shop.web.controller;

import com.weafung.shop.common.constant.CodeEnum;
import com.weafung.shop.model.dto.GoodsDTO;
import com.weafung.shop.model.dto.ResponseDTO;
import com.weafung.shop.model.vo.ResponseVO;
import com.weafung.shop.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author weifeng
 */
@Controller
@RequestMapping("/api/goods")
@Slf4j
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/get")
    @ResponseBody
    public ResponseVO<GoodsDTO> get(@RequestParam("goodsId") Long goodsId) {
        if (goodsId == null) {
            return ResponseVO.build(CodeEnum.PARAM_EMPTY);
        }
        ResponseDTO<GoodsDTO> responseDTO = goodsService.getGoodsByGoodsId(goodsId);
        if (responseDTO != null && responseDTO.getData() != null) {
            return ResponseVO.build(responseDTO.getCode(), responseDTO.getData(), responseDTO.getMsg());
        }
        log.warn("execute goodsService#getGoodsByGoodsId failed.goodsIs:{}, responseDTO:{}",goodsId, responseDTO);
        return ResponseVO.build(CodeEnum.ERROR);
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseVO<Boolean> update(@RequestBody GoodsDTO goodsDTO) {
        if (goodsDTO == null) {
            return ResponseVO.build(CodeEnum.PARAM_EMPTY);
        }
        ResponseDTO<Boolean> responseDTO = goodsService.updateGoods(goodsDTO);
        if (responseDTO != null && responseDTO.getData() != null) {
            return ResponseVO.build(responseDTO.getCode(), responseDTO.getData(), responseDTO.getMsg());
        }
        log.warn("execute goodsService#updateGoods failed. goodsDTO:{}, responseDTO:{}", goodsDTO, responseDTO);
        return ResponseVO.build(CodeEnum.ERROR);
    }
}
