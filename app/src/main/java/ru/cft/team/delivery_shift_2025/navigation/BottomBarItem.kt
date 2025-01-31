package ru.cft.team.delivery_shift_2025.navigation

import ru.cft.team.calculation.navigation.CalculationRoute
import ru.cft.team.delivery_shift_2025.R
import ru.cft.team.history.navigation.HistoryRoute
import ru.cft.team.network.navigation.Route
import ru.cft.team.profile.navigation.ProfileRoute

enum class BottomBarItem(
    val icon: Int,
    val title: String,
    val route: Route
) {
    Calculation(
        icon = R.drawable.calc,
        title = "Расчёт",
        route = CalculationRoute
    ),
    History(
        icon = R.drawable.hist,
        title = "История",
        route = HistoryRoute
    ),
    Profile(
        icon = R.drawable.prof,
        title = "Профиль",
        route = ProfileRoute
    )
}