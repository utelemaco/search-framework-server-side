package com.owse.searchFramework

class PageableSearchResult<T> {

	def pageNumber = 1
	def pageSize = 20
	def total
	List<T> list

	List<Filter> filters = []

	def sortedBy = [:]

}
