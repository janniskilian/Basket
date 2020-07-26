package de.janniskilian.basket

import com.schibsted.spain.barista.rule.cleardata.ClearDatabaseRule
import com.schibsted.spain.barista.rule.cleardata.ClearFilesRule
import com.schibsted.spain.barista.rule.cleardata.ClearPreferencesRule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class UiTestRule(isSkipOnboarding: Boolean = true) : TestRule {

    private val activityTestRule = ActivityRule(isSkipOnboarding)
    private val clearPreferencesRule = ClearPreferencesRule()
    private val clearDatabaseRule = ClearDatabaseRule()
    private val clearFilesRule = ClearFilesRule()

    override fun apply(base: Statement, description: Description): Statement =
        RuleChain
            .outerRule(clearPreferencesRule)
            .around(clearDatabaseRule)
            .around(clearFilesRule)
            .around(activityTestRule)
            .apply(base, description)
}
