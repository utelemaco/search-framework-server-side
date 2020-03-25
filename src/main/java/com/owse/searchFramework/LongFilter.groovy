package com.owse.searchFramework

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Query

class LongFilter extends Filter {

    String type = 'Long'
    String value
    String value2

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

        if (operator == 'notEquals') {
            return " ${hqlField} != :${id}"
        }

        if (operator == 'between') {
            return " (${hqlField} >= :${id} and ${hqlField} <= :${id}2)"
        }

        return " ${hqlField} = :${id}"
    }

    void setParameterInQuery(Query query) {
        query.setParameter(id, (value as Long))

        if (operator == 'between') {
            query.setParameter("${id}2", (value2 as Long))
        }
    }
}
