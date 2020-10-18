package de.janniskilian.basket.feature.main

import de.janniskilian.basket.R

enum class MainPage {

    SHOPPING_LISTS {
        override val navId: Int
            get() = R.id.listsFragment

        override val titleRes: Int
            get() = R.string.navigation_shopping_lists

        override val iconRes: Int
            get() = R.drawable.ic_shopping_lists_24
    },

    GROUPS {
        override val navId: Int
            get() = R.id.groupsFragment

        override val titleRes: Int
            get() = R.string.navigation_groups

        override val iconRes: Int
            get() = R.drawable.ic_groups_24
    },

    ARTICLES {
        override val navId: Int
            get() = R.id.articlesFragment

        override val titleRes: Int
            get() = R.string.navigation_articles

        override val iconRes: Int
            get() = R.drawable.ic_articles_24
    },

    CATEGORIES {
        override val navId: Int
            get() = R.id.categoriesFragment

        override val titleRes: Int
            get() = R.string.navigation_categories

        override val iconRes: Int
            get() = R.drawable.ic_categories_24
    },

    SETTINGS {
        override val navId: Int
            get() = R.id.settingsFragment

        override val titleRes: Int
            get() = R.string.navigation_settings

        override val iconRes: Int
            get() = R.drawable.ic_settings_24
    },

    HELP_AND_FEEDBACK {
        override val navId: Int
            get() = R.id.helpAndFeedbackFragment

        override val titleRes: Int
            get() = R.string.navigation_help_and_feedback

        override val iconRes: Int
            get() = R.drawable.ic_help_24
    };

    abstract val navId: Int

    abstract val titleRes: Int

    abstract val iconRes: Int
}
