package com.owse.searchFramework

class PageableSearchRequest {

	/**
	 * Default values: first page and 20 registers per page...
	 */
	Integer pageNumber = 1
	Integer pageSize = 20

	List<Filter> filters = []

	def sortedBy = [:]

	List<Filter> getActiveFilters() {
		return filters.findAll { it.isActive() }
	}

}
