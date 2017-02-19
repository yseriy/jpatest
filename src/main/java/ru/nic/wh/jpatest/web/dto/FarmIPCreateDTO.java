package ru.nic.wh.jpatest.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FarmIPCreateDTO extends FarmIPDTO {

	@Size(min = 1)
	@NotNull(groups = FarmIPCreate.class)
	private List<String> brandNameList;

	public FarmIPCreateDTO() {
		super();
	}
}
