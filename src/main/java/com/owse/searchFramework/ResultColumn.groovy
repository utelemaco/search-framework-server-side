package com.owse.searchFramework

class ResultColumn {

	String id
    String dtoField
	String type = 'String'
    String subType
	Boolean sortable = true
	Boolean visible = false

    String buildSortStatement(String sortField) {
        if (type == 'String') {
            return "trim(lower(entity.${sortField}))"
        }
        return "entity."+ sortField;
    }

}
