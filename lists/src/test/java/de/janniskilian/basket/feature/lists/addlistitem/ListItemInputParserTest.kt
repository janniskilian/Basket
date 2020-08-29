package de.janniskilian.basket.feature.lists.addlistitem

import org.junit.Test
import kotlin.test.assertEquals

class ListItemInputParserTest {

    private val parser = ListItemInputParser()

    @Test
    fun parseInputWithNameOnly() {
        checkResult("Apples", "Apples")
        checkResult("Apple Pie", "Apple Pie")
        checkResult("Apple Pie   ", "Apple Pie")
        checkResult("Apple Pie, ", "Apple Pie,")
        checkResult("Apple, Pie", "Apple, Pie")
        checkResult("Apple, Pie ", "Apple, Pie")
        checkResult("Apple, Pie, ", "Apple, Pie,")
        checkResult(" ", "")
    }

    @Test
    fun parseInputWithNameAndQuantity() {
        checkResult("Bananas 2", "Bananas", "2")
        checkResult("Bananas, 2", "Bananas", "2")
        checkResult("Banana Bread 2", "Banana Bread", "2")
        checkResult("Banana Bread,  2", "Banana Bread", "2")
        checkResult("Banana, Bread 2", "Banana, Bread", "2")
        checkResult("Banana, Bread, 2", "Banana, Bread", "2")
        checkResult("Gruyère 2", "Gruyère", "2")
    }

    @Test
    fun parseInputWithNameAndQuantityAndUnit() {
        checkResult("Clementines 2kg", "Clementines", "2", "kg")
        checkResult("Clementines 2 kg", "Clementines", "2", "kg")
        checkResult("Clementines, 2kg", "Clementines", "2", "kg")
        checkResult("Clementines, 2  kg", "Clementines", "2", "kg")
        checkResult("Banana Bread  2kg", "Banana Bread", "2", "kg")
        checkResult("Banana Bread  2  kg", "Banana Bread", "2", "kg")
        checkResult("Banana Bread, 2kg", "Banana Bread", "2", "kg")
        checkResult("Banana Bread, 2 kg", "Banana Bread", "2", "kg")
        checkResult("Gruyère 200g", "Gruyère", "200", "g")
    }

    private fun checkResult(
        input: String,
        name: String,
        quantity: String? = null,
        unit: String? = null
    ) {
        val result = parser.parse(input)
        assertEquals(name, result.name)
        assertEquals(quantity, result.quantity)
        assertEquals(unit, result.unit)
    }
}
