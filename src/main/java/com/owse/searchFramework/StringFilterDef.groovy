package com.owse.searchFramework

class StringFilterDef extends FilterDef {

	String type = 'String'
	String defaultValue
    String defaultOperator = 'contains'
	List<Operator> operators = []

	public StringFilterDef() {
		operators << new Operator(name: 'contains')
		operators << new Operator(name: 'startsWith')
		operators << new Operator(name: 'endsWith')
		operators << new Operator(name: 'exact')
	}

}
