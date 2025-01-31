package ru.cft.team.delivery_shift_2025.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomBar(navController: NavController) {

    var selected by rememberSaveable { mutableIntStateOf(0) }

    val items = rememberSaveable { BottomBarItem.entries.toTypedArray() }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selected,
                onClick = {
                    navController.navigate(item.route)
                    selected = index
                },
                icon = {
                    Image(
                        modifier = Modifier.height(25.dp),
                        imageVector = ImageVector.vectorResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) })
        }
    }
}