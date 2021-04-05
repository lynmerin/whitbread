package com.whitbread.demo.model;

import lombok.Data;

@Data
public class AcceptedCreditCard {
    private String code;
    private String feeAmount;
    private String feeCurrency;
    private boolean paymentOnly;
    private String listOrder;
    private String name;
    private String schemeLogo;
}
