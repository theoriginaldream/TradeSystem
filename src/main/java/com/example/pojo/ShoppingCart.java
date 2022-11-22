package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
    private int id;
    private String userid;
    private int itemid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
}
