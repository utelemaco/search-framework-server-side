package com.owse.searchFramework;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Query;

@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  include = JsonTypeInfo.As.PROPERTY,
		  property = "type")
		@JsonSubTypes({
            @Type(value = StringFilter.class, name = "String"),
            @Type(value = ListFilter.class, name = "List"),
            @Type(value = EntityListFilter.class, name = "EntityList"),
            @Type(value = EnumFilter.class, name = "Enum"),
            @Type(value = EnumListFilter.class, name = "EnumList"),
            @Type(value = EntityFilter.class, name = "Entity"),
            @Type(value = DatetimeFilter.class, name = "Datetime"),
            @Type(value = BooleanFilter.class, name = "Boolean"),
            @Type(value = IntegerFilter.class, name = "Integer"),
            @Type(value = LongFilter.class, name = "Long"),
            @Type(value = IdFilter.class, name = "Id")
		})
public abstract class Filter {

	private String id;
	private String operator;
	private String type;

	abstract boolean isActive();

	abstract String buildCriteria(String searchField);

	abstract void setParameterInQuery(Query query);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
