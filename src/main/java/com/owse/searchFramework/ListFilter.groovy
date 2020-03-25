package com.owse.searchFramework

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Query

class ListFilter extends Filter {

	String type = 'List'
	List values

	@JsonIgnore
	boolean isActive() {
		return values
	}

	String buildCriteria(String hqlField) {
		return " ${hqlField} in (:${id}) "
	}

	void setParameterInQuery(Query query) {
		query.setParameter(id, values)
	}
}
