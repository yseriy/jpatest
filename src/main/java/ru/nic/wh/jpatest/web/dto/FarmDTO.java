package ru.nic.wh.jpatest.web.dto;

import lombok.Data;

@Data
public class FarmDTO {

	private String name;

	private Integer capacity;

	private String location;

	private String farmtypeName;
}
