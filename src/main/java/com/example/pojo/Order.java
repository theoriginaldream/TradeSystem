package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int orderid;
    private int itemid;
    private String buyer;
    private String seller;
    private String status;
    private Date datetime;
}
