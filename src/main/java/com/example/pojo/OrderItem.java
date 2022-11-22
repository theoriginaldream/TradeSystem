package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date datetime;
}
