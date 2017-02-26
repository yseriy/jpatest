package ru.nic.wh.jpatest.miscellaneous.validationgroups;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, NotNullClass.class})
public interface NotNullGroup {
}
