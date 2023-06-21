package com.ip.web_shop.model.dto;

import com.ip.web_shop.model.dto.request.AttributeRequest;
import lombok.Data;

import java.util.List;

@Data
public class OfferDTO {
    private Integer offerId;
    private String title;
    private String description;
    private Double price;
    private String location;
    private boolean isNew;
    private UserDTO user;
    private Integer quantity;
    private Boolean deleted;
    private List<OfferAttributeDTO> attributes;
    private List<CategoryDTO> categories;
    private List<PictureDTO> pictures;
    private List<QuestionDTO> questions;

    public boolean isSearchComplaint(List<AttributeRequest> criteriaList){
        //TODO pomocu equals i contains
        int count = 0;
        for(var attribute : attributes){
            if(count==criteriaList.size()){
                return true;
            }
            for(var criteria : criteriaList ) {
                if (attribute.isSearchComplaint(criteria.getAttributeId(), criteria.getValue())){
                    count++;
                    break;
                }
            }
        }
        return count == criteriaList.size();
    }

}
