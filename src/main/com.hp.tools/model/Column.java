package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Column {

    /**
     * 字段名称
     */
    private String columnName;
    /**
     * 字段类型
     */
    private String dataType;
    /**
     * 长度
     */
    private Integer dataLength;
    /**
     * 数据精度
     */
    private Integer dataPrecision;
    /**
     * 数据刻度
     */
    private Integer dataScale;
    /**
     * 是否为空
     */
    private String nullable;
    /**
     * 默认值
     */
    private String dataDefault;
    /**
     * 字段注释
     */
    private String columnComments;

}