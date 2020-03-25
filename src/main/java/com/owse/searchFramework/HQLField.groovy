package com.owse.searchFramework

class HQLField {

    String searchField
    String sortField

    HQLField(String searchField) {
        this.searchField = searchField
        this.sortField = searchField
    }

    HQLField(String searchField, String sortField) {
        this.searchField = searchField
        this.sortField = sortField
    }

    String getSortField() {
        return sortField ? sortField : searchField
    }
}
