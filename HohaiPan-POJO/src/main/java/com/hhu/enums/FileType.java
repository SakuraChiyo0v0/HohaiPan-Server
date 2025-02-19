package com.hhu.enums;

import lombok.Getter;

import static com.hhu.constant.MessageConstant.UNKNOWN_ENUM_ERROR;

/**
 * 文件类型枚举
 */
@Getter
public enum FileType {

    FOLDER(0, "文件夹"),
    FILE(1, "文件"),
    NON_EXISTENT(-1, "不存在的文件");

    private final int code;
    private final String type;

    FileType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public static String getType(int code) {
        for (FileType type : FileType.values()) {
            if (type.getCode() == code) {
                return type.type;
            }
        }
        throw new IllegalArgumentException(UNKNOWN_ENUM_ERROR);
    }
}
