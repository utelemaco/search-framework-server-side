package com.owse.searchFramework

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Query

class IdFilter extends Filter {

	String type = 'Id'
	Long value

	@JsonIgnore
	boolean isActive() {
		return value
	}

	String buildCriteria(String hqlField) {
		return " ${hqlField} = :${id} "
	}

	void setParameterInQuery(Query query) {
		query.setParameter(id, value)
	}
}
