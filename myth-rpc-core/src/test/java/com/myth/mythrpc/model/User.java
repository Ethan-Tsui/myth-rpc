package com.myth.mythrpc.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Ethan
 * @version 1.0
 */
@Data
public class User implements Serializable {
    private Integer age;
    private String address;
    private Long bankNo;
    private Integer sex;
    private Integer id;
    private String idCardNo;
    private String remark;
    private String username;
}
