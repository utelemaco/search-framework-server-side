package com.owse.searchFramework

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Query

class EntityFilter extends Filter {

	String type = 'Entity'
	def value

	@JsonIgnore
	boolean isActive() {
		return value
	}

	String buildCriteria(String searchField) {
		return " ${searchField} = :${id} "
	}

	void setParameterInQuery(Query query) {
		query.setParameter(id, value.id as long)
	}
}
