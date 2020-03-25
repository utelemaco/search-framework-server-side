package com.owse.searchFramework

import org.junit.Test


class StringFilterTest {

    @Test
    public void test() {
        StringFilter filter = new StringFilter(id: 'myFieldId')
        assert !filter.isActive()

        filter.value = 'aaa'
        assert filter.isActive()
        assert filter.buildCriteria('myField') == ' trim(lower(myField)) like :myFieldId'
    }
}
