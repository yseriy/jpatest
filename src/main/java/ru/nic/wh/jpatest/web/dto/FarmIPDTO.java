package ru.nic.wh.jpatest.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FarmIPDTO {

	@NotNull
	private String ip;

	@Size(min = 1)
	@NotNull(groups = FarmIPCreate.class)
	private String farmIpTypeName;
}
