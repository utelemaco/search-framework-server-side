package com.owse.searchFramework

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Query


class EnumListFilter extends Filter {

    String type = 'EnumList'
    List values
    Class enumType

    @JsonIgnore
    boolean isActive() {
        return values
    }

    @Override
    String buildCriteria(String hqlField) {
        return " ${hqlField} in (:${name}) "
    }

    @Override
    void setParameterInQuery(Query query) {
        if (enumType) {
            List valuesAsEnum = values.collect {
                return enumType.valueOf(it)
            }

            query.setParameter(name, valuesAsEnum)
            return
        }

        query.setParameter(name, values)
    }
}
