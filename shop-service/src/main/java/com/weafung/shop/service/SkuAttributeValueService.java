package com.weafung.shop.service;

import com.weafung.shop.model.dto.ResponseDTO;
import com.weafung.shop.model.dto.SkuAttributeValueDTO;

import java.util.List;

/**
 * @author weifeng
 */
public interface SkuAttributeValueService {
    /**
     * 根据属性值ID获取属性值
     * @param attributeValueId
     * @return
     */
    ResponseDTO<SkuAttributeValueDTO> getByAttributeValueId(Long attributeValueId);

    /**
     * 根据属性ID获取属性值
     * @param attributeNameId
     * @return
     */
    ResponseDTO<List<SkuAttributeValueDTO>> listByAttributeNameId(Long attributeNameId);

    ResponseDTO<Boolean> addSkuAttributeValue(Long attributeNameId, String value);

    ResponseDTO<List<SkuAttributeValueDTO>> listSkuAttributeValue();

    ResponseDTO<Boolean> deleteByAttributeValueId(Long attributeValueId);

    ResponseDTO<Boolean> updateByAttributeValueId(Long attributeValueId, String attributeValue);

}
