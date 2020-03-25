package com.owse.searchFramework

class IntegerFilterDef extends FilterDef {

    String type = 'Integer'
    Integer defaultValue
    String defaultOperator = 'equals'
    List<Operator> operators = []

    IntegerFilterDef() {
        operators << new Operator(name: 'equals')
        operators << new Operator(name: 'notEquals')
        operators << new Operator(name: 'moreOrEquals')
        operators << new Operator(name: 'more')
        operators << new Operator(name: 'lessOrEquals')
        operators << new Operator(name: 'less')
        operators << new Operator(name: 'between')
    }
}
