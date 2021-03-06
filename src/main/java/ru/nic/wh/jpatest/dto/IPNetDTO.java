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
public class IPNetDTO {

    @Size(min = 1)
    @NotNull(groups = NotNullClass.class)
    private String netAddress;

    @Size(min = 1)
    @NotNull(groups = NotNullClass.class)
    private String ipnetTypeName;

    private Boolean required;

    @Valid
    private List<BrandDTO> brandList;
}
