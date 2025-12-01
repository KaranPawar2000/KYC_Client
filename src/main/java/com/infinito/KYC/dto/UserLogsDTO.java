package com.infinito.KYC.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserLogsDTO {

    private Date date;

    private String userName;

    private String LogText;
}
