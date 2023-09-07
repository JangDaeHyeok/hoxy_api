package com.jdh.hoxy_api.common.annotations.groups;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, ValidationGroups.NotNullGroup.class, ValidationGroups.EnumGroup.class, })
public interface ValidationSequence {
}
