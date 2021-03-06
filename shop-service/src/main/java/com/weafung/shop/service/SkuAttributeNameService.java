package com.weafung.shop.service;

import com.weafung.shop.model.dto.ResponseDTO;
import com.weafung.shop.model.dto.SkuAttributeNameDTO;

import java.util.List;

public interface SkuAttributeNameService {
    /**
     * 根据属性ID获取属性值
     * @param attributeNameId
     * @return
     */
    ResponseDTO<SkuAttributeNameDTO> getByAttributeNameId(Long attributeNameId);

    ResponseDTO<Boolean> addSkuAttributeName(String name);

    ResponseDTO<Boolean> deleteByAttributeNameId(Long attributeNameId);

    ResponseDTO<List<SkuAttributeNameDTO>> listSkuAttributeName();

    ResponseDTO<Boolean> updateByAttributeNameId(Long attributeNameId, String attributeName);

}
