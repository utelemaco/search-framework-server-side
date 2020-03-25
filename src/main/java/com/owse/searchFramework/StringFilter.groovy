package com.owse.searchFramework

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Query

class StringFilter extends Filter {

	String type = 'String'
	String value

	@JsonIgnore
	boolean isActive() {
		String valueAsString = (String) value
		return valueAsString?.trim()
	}

	String buildCriteria(String searchField) {
        return " trim(lower(${searchField})) like :${id}"
	}

	void setParameterInQuery(Query query) {
		String v = value?.toLowerCase().trim()

		if (operator == 'exact') {
			query.setParameter(id, v)
			return
		}

		if (operator == 'startsWith') {
			query.setParameter(id, v + '%')
			return
		}

		if (operator == 'endsWith') {
			query.setParameter(id, '%' +  v)
			return
		}

		query.setParameter(id, '%' + v + '%')
	}
}
