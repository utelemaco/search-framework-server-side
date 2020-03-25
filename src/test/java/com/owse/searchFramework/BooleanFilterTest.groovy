package com.owse.searchFramework

import org.junit.Test


class BooleanFilterTest {

    @Test
    public void test() {
        BooleanFilter filter = new BooleanFilter(id: 'myFieldId', value: true)
        assert filter.buildCriteria('myField') == ' myField = :myFieldId'
    }
}
