package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private int orderid;
    private int itemid;
    private String itemname;
    private String price;
    private String buyer;
    private String seller;
    private String status;
    private ItemPicture itempicture;
    private Date datetime;
}
