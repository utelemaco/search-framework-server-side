package com.owse.searchFramework

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Query

class EntityListFilter extends Filter {

	String type = 'EntityList'
	List values

	@JsonIgnore
	boolean isActive() {
		return values
	}

	String buildCriteria(String hqlField) {
		return " ${hqlField} in (:${id}) "
	}

	void setParameterInQuery(Query query) {
		query.setParameter(id, values.collect{it.id as long})
	}
}
