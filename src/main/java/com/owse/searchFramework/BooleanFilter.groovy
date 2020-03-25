package com.owse.searchFramework

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Query

class BooleanFilter extends Filter {

	String type = 'Boolean'
	Boolean value
	String trueValue
    String falseValue

	@JsonIgnore
	boolean isActive() {
		return true
	}

	String buildCriteria(String searchField) {
		return " ${searchField} = :${id}"
	}

	void setParameterInQuery(Query query) {
		query.setParameter(id, value?trueValue:falseValue)
	}

}
