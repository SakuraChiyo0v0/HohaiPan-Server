package com.hhu.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import static com.hhu.constant.MessageConstant.UNKNOWN_ENUM_ERROR;

@Getter
public enum CategoryCodeType {
    All(0, "all"),
    Video(1, "video"),
    Music(2, "music"),
    Image(3, "image"),
    Doc(4, "doc"),
    Others(5, "others");

    @EnumValue
    private final Integer typeCode;
    @JsonValue
    private final String typeName;

    CategoryCodeType(Integer categoryCode, String type) {
        this.typeCode = categoryCode;
        this.typeName = type;
    }

    public static String getTypeName(int code) {
        for(CategoryCodeType type: CategoryCodeType.values()){
            if(type.typeCode == code){
                return type.typeName;
            }
        }
        throw new IllegalArgumentException(UNKNOWN_ENUM_ERROR);
    }

    public static String getTypeCode(String typeName){
        for(CategoryCodeType type: CategoryCodeType.values()){
            if(type.typeName.equals(typeName)){
                return String.valueOf(type.typeCode);
            }
        }
        throw new IllegalArgumentException(UNKNOWN_ENUM_ERROR);
    }
}
