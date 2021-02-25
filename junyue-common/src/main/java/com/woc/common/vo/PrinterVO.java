package com.woc.common.vo;

import lombok.Data;
import lombok.Setter;

@Data
public class PrinterVO {

    private String printerNo;

    private String printerKey;

    private Integer printerBand;

    private Boolean status;

    private String errorMsg;
}
