package ru.nic.wh.jpatest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.nic.wh.jpatest.miscellaneous.validationgroups.NotNullClass;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandDTO {

    @Size(min = 1)
    @NotNull(groups = NotNullClass.class)
    private String name;
}
