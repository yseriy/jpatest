package ru.nic.wh.jpatest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.nic.wh.jpatest.miscellaneous.validationgroups.NotNullClass;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FarmDTO {

    @Size(min = 1)
    @NotNull(groups = NotNullClass.class)
    private String name;

    @Min(1)
    @NotNull(groups = NotNullClass.class)
    private Integer capacity;

    @Size(min = 1)
    @NotNull(groups = NotNullClass.class)
    private String locationName;

    @Size(min = 1)
    @NotNull(groups = NotNullClass.class)
    private String farmtypeName;

    @Valid
    @Size(min = 1)
    @NotNull(groups = NotNullClass.class)
    private List<FarmIPDTO> farmipList;
}
