package com.owse.searchFramework


import java.time.Instant

class DatetimeFilterDef extends FilterDef {

	String type = 'Datetime'
    String defaultOperator = 'today'
    Instant defaultDatetime1
    Instant defaultDatetime2
	List<Operator> operators = []

	public DatetimeFilterDef() {
		operators << new Operator(name: 'today')
		operators << new Operator(name: 'yesterday')
		operators << new Operator(name: 'last-2-days')
		operators << new Operator(name: 'last-10-days')
		operators << new Operator(name: 'last-15-days')
		operators << new Operator(name: 'this-week')
		operators << new Operator(name: 'last-week')
		operators << new Operator(name: 'this-month')
		operators << new Operator(name: 'last-month')
		operators << new Operator(name: 'before-date', showValue1:true)
		operators << new Operator(name: 'before-datetime', showValue1:true)
		operators << new Operator(name: 'after-date', showValue1:true)
		operators << new Operator(name: 'after-datetime', showValue1:true)
		operators << new Operator(name: 'between-date', showValue1:true, showValue2:true)
		operators << new Operator(name: 'between-datetime', showValue1:true, showValue2:true)
		operators << new Operator(name: 'exact-date', showValue1:true)
		operators << new Operator(name: 'exact-datetime', showValue1:true)
	}

}
