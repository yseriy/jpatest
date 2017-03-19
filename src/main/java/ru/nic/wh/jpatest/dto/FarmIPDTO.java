package ru.nic.wh.jpatest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.nic.wh.jpatest.miscellaneous.validationgroups.NotNullClass;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FarmIPDTO {

    @NotNull(groups = NotNullClass.class)
    private String ip;

    @Size(min = 1)
    @NotNull(groups = NotNullClass.class)
    private String farmIpTypeName;

    @Valid
    @Size(min = 1)
    @NotNull(groups = NotNullClass.class)
    private List<BrandDTO> brandList;
}
