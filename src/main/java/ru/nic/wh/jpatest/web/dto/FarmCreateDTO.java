package ru.nic.wh.jpatest.web.dto;

import lombok.Data;
import ru.nic.wh.jpatest.domain.FarmIP;

import java.util.List;

@Data
public class FarmCreateDTO extends FarmDTO {

	private List<FarmIPCreateDTO> farmipList;
}
