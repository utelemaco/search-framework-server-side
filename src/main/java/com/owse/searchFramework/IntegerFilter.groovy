package com.owse.searchFramework

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Query

class IntegerFilter extends Filter {

    String type = 'Integer'
    String value

    @JsonIgnore
    boolean isActive() {
        String valueAsString = (String) value
        return valueAsString?.trim()
    }

    String buildCriteria(String hqlField) {
        if (operator == 'more') {
            return " ${hqlField} > :${id}"
        }

        if (operator == 'moreOrEquals') {
            return " ${hqlField} >= :${id}"
        }

        if (operator == 'less') {
            return " ${hqlField} < :${id}"
        }

        if (operator == 'lessOrEquals') {
            return " ${hqlField} <= :${id}"
        }

        return " ${hqlField} = :${id}"
    }

    void setParameterInQuery(Query query) {
        query.setParameter(id, (value as Integer))
    }
}
