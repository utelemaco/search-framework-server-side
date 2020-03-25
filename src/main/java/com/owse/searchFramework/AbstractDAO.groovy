package com.owse.searchFramework


import org.slf4j.Logger

import javax.persistence.EntityManager
import javax.persistence.Query

abstract class AbstractDAO<D,E> {

    private EntityManager entityManager
    private Class entityClass

    Map<String, HQLField> hqlFields = [:]

	public AbstractDAO(EntityManager entityManager, Class entityClass) {
		super();
		this.entityManager = entityManager
        this.entityClass = entityClass
        configureHQLFields()
	}

    abstract String getName();

    abstract List<FilterDef> getFiltersDef()

    abstract List<ResultColumn> getResultColumns()

    abstract String getDefaultSortByField()

    abstract void configureHQLFields()

    abstract Boolean getCanAddFilters()

    abstract Logger getLog();

    SearchConfig getSearchConfig() {
        SearchConfig searchConfig = new SearchConfig()
        searchConfig.filtersDef = getFiltersDef()
        searchConfig.resultColumns = getResultColumns()
        searchConfig.defaultSortByField = getDefaultSortByField()
        searchConfig.canAddFilters = getCanAddFilters()
        return searchConfig
    }

    PageableSearchResult<D> search(PageableSearchRequest searchRequest) {
        getLog().debug("Searching by ", searchRequest)
		PageableSearchResult<?> searchResult = new PageableSearchResult()
		searchResult.pageSize = searchRequest.pageSize
		searchResult.pageNumber = searchRequest.pageNumber
		searchResult.filters = searchRequest.filters
		searchResult.sortedBy = searchRequest.sortedBy

        Query queryCount = entityManager.createQuery(buildCountHQL(searchRequest))
		queryCount = setParameters(queryCount, searchRequest)
		searchResult.total = queryCount.singleResult

        Query queryList = entityManager.createQuery(buildListHQL(searchRequest), entityClass)
			.setMaxResults(searchRequest.pageSize)
			.setFirstResult((searchRequest.pageNumber - 1) * searchRequest.pageSize)
		queryList = setParameters(queryList, searchRequest)
		searchResult.list = queryList.resultList
        getLog().debug("Searching done! Found: Total: ${searchResult.total}. In this page: ${searchResult.list.size()}")
		searchResult
	}


	public String buildCountHQL(PageableSearchRequest searchRequest) {
		String hql = "select count(entity) from ${entityClass.name} entity"
		searchRequest.activeFilters.each { filter ->
            hql = addCriteria(hql, filter.buildCriteria("entity.${hqlFields[filter.id].searchField}"))
		}
		hql
	}

    public String buildListHQL(PageableSearchRequest searchRequest) {
		String hql = "select entity from ${entityClass.name} entity"
		searchRequest.activeFilters.each { filter ->
            hql = addCriteria(hql, filter.buildCriteria("entity.${hqlFields[filter.id].searchField}"))
		}

		if (searchRequest.sortedBy.id) {
            ResultColumn resultColumn = getResultColumns().find { it.id == searchRequest.sortedBy.id }
			hql = "${hql} order by ${resultColumn.buildSortStatement(hqlFields[searchRequest.sortedBy.id].sortField)} "
			if (searchRequest.sortedBy.reverse) {
				hql = "${hql} desc"
			}
		}

		hql
	}

	def setParameters(Query query, PageableSearchRequest searchRequest){
		searchRequest.activeFilters.each { filter ->
			filter.setParameterInQuery(query)
		}

		return query
	}

	String addCriteria(String hql, String criteria) {
		if (hql.endsWith("from ${entityClass.name} entity")) {
			return "${hql} where ${criteria}"
		}

		return "${hql} and ${criteria}"
	}

}
