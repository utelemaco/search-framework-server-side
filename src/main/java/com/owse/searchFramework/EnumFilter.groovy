package com.owse.searchFramework

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Query


class EnumFilter extends Filter {

    String type = 'Enum'
    String value
    Class enumType

    @JsonIgnore
    boolean isActive() {
        return value
    }

    @Override
    String buildCriteria(String hqlField) {
        return " ${hqlField} = :${id} "
    }

    @Override
    void setParameterInQuery(Query query) {
        if (enumType) {
            query.setParameter(id, enumType.valueOf(value))
            return
        }

        query.setParameter(id, value)
    }
}
