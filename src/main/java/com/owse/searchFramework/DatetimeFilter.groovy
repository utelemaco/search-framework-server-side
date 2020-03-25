package com.owse.searchFramework

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Query
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields

class DatetimeFilter extends Filter {

	String type = 'Datetime'
	Instant datetime1
	Instant datetime2

	@JsonIgnore
	boolean isActive() {
		if (operator == 'today' || operator == 'yesterday' || operator.startsWith('this') || operator.startsWith('last')) {
			return true
		}

		if (operator.startsWith('before') || operator.startsWith('after') || operator.startsWith('exact')) {
			return datetime1 != null
		}

		if (operator.startsWith('between')) {
			return datetime1 != null && datetime2 != null
		}

		return false
	}

	String buildCriteria(String searchField) {
		if (operator == 'today' || operator == 'yesterday') {
			return " trunc(${searchField}) = trunc(:${id}) "
		}

		if (operator.startsWith('this') || operator.startsWith('last')) {
			return " trunc(${searchField}) between trunc(:${id}) and trunc(:${id}2) "
		}

		if (operator == 'before-date') {
			return " trunc(${searchField}) <= trunc(:${id}) "
		}

		if (operator == 'before-datetime') {
			return " ${searchField} <= :${id} "
		}

		if (operator == 'after-date') {
			return " trunc(${searchField}) >= trunc(:${id}) "
		}

		if (operator == 'after-datetime') {
			return " ${searchField} >= :${id} "
		}

		if (operator == 'between-date') {
			return " trunc(${searchField}) between trunc(:${id}) and trunc(:${id}2) "
		}

		if (operator == 'between-datetime') {
			return " ${searchField} between :${id} and :${id}2 "
		}

		if (operator == 'exact-date') {
			return " trunc(${searchField}) = trunc(:${id}) "
		}

		if (operator == 'exact-datetime') {
			return " ${searchField} = :${id} "
		}
	}

	void setParameterInQuery(Query query) {
		prepareDatetimeFilters()
		query.setParameter(id, datetime1)

		if (operator.startsWith('this') || operator.startsWith('last') || operator.startsWith('between')) {
			query.setParameter("${id}2", datetime2)
		}
	}

	void prepareDatetimeFilters() {
		if (operator == 'today') {
			datetime1 = Instant.now()
			return
		}

		if (operator == 'yesterday') {
			datetime1 = Instant.now().minus(1,ChronoUnit.DAYS)
			return
		}

		if (operator == 'last-2-days') {
			datetime1 = Instant.now().minus(2,ChronoUnit.DAYS)
			datetime2 = Instant.now()
			return
		}

		if (operator == 'last-10-days') {
			datetime1 = Instant.now().minus(10,ChronoUnit.DAYS)
			datetime2 = Instant.now()
			return
		}

		if (operator == 'last-15-days') {
			datetime1 = Instant.now().minus(15,ChronoUnit.DAYS)
			datetime2 = Instant.now()
			return
		}

		if (operator == 'this-week') {
			datetime1 = LocalDateTime.now().with(WeekFields.of(Locale.US).dayOfWeek(), 1L).toInstant(ZoneOffset.UTC)
			datetime2 = Instant.now()
			return
		}

		if (operator == 'last-week') {
			datetime1 = LocalDateTime.now().minus(1, ChronoUnit.WEEKS).with(WeekFields.of(Locale.US).dayOfWeek(), 1L).toInstant(ZoneOffset.UTC)
			datetime2 = LocalDateTime.now().minus(1, ChronoUnit.WEEKS).with(WeekFields.of(Locale.US).dayOfWeek(), 7L).toInstant(ZoneOffset.UTC)
			return
		}

		if (operator == 'this-month') {
			datetime1 = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).toInstant(ZoneOffset.UTC)
			datetime2 = Instant.now()
			return
		}

		if (operator == 'last-month') {
			datetime1 = LocalDateTime.now().minus(1, ChronoUnit.MONTHS).with(TemporalAdjusters.firstDayOfMonth()).toInstant(ZoneOffset.UTC)
			datetime2 = LocalDateTime.now().minus(1, ChronoUnit.MONTHS).with(TemporalAdjusters.lastDayOfMonth()).toInstant(ZoneOffset.UTC)
			return
		}
	}

}
