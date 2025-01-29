package ru.cft.team.calculation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.cft.team.calculation.R

@Composable
fun CalculationScreen(onCalculate: () -> Unit) {
    // Позже через viewmodel будем получать
    val cityList = listOf("Москва", "Санкт-Петербург", "Новосибирск", "Томск")
    val packageSizeList = listOf("Конверт", "Тележка", "Короб")
    val sendCityExtended = remember { mutableStateOf(false) }
    val receiveCityExtended = remember { mutableStateOf(false) }
    val packageSizeExtended = remember { mutableStateOf(false) }
    val sendCityText = remember { mutableStateOf(cityList.first()) }
    val receiveCityText = remember { mutableStateOf(cityList.first()) }
    val packageSizeText = remember { mutableStateOf(packageSizeList.first()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(
                    id = R.color.secondary
                )
            )
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 15.dp),
            text = stringResource(R.string.calc_screen_header),
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = stringResource(R.string.calc_screen_subtitle),
            fontSize = 16.sp,
            color = Color.Gray
        )
        QRCard()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(color = Color.White)
                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp),
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.calc_delivery),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
            ItemPicker(
                title = stringResource(R.string.send_city),
                pickedText = sendCityText,
                items = cityList,
                extended = sendCityExtended,
                dropdownHeader = stringResource(id = R.string.where),
                imageVector = Icons.Default.LocationOn
            )
            ItemPicker(
                title = stringResource(R.string.receive_city),
                pickedText = receiveCityText,
                items = cityList,
                extended = receiveCityExtended,
                dropdownHeader = stringResource(id = R.string.where),
                imageVector = Icons.Default.Place
            )
            ItemPicker(
                title = stringResource(R.string.package_size),
                pickedText = packageSizeText,
                items = packageSizeList,
                extended = packageSizeExtended,
                dropdownHeader = stringResource(R.string.package_size),
                imageVector = Icons.Default.Email
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 10.dp)
                    .height(60.dp),
                onClick = onCalculate,
                colors = ButtonColors(
                    contentColor = Color.White,
                    containerColor = colorResource(id = R.color.brand),
                    disabledContentColor = Color.White,
                    disabledContainerColor = colorResource(id = R.color.brand)
                )
            ) {
                Text(text = stringResource(R.string.calculate), fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun QRCard() {
    Box(
        modifier = Modifier
            .padding(top = 20.dp, bottom = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.5f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    modifier = Modifier.size(60.dp),
                    painter = painterResource(id = R.drawable.box),
                    contentDescription = "box"
                )
                Image(
                    modifier = Modifier.size(56.dp),
                    painter = painterResource(id = R.drawable.qr),
                    contentDescription = "qr"
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                text = "Наведите камеру телефона на QR‑код",
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ItemPicker(
    title: String,
    pickedText: MutableState<String>,
    items: List<String>,
    extended: MutableState<Boolean>,
    dropdownHeader: String,
    imageVector: ImageVector
) {
    Text(text = title, fontSize = 14.sp, textAlign = TextAlign.Start)
    Card(
        colors = CardColors(
            contentColor = Color.Black,
            containerColor = Color.White,
            disabledContentColor = Color.Black,
            disabledContainerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
            .clickable { extended.value = !extended.value }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Image(imageVector, "pickerIcon")
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = pickedText.value,
                fontSize = 16.sp
            )
            Image(
                modifier = Modifier.fillMaxWidth(),
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "arrowDown",
                alignment = Alignment.CenterEnd
            )
        }
    }
    Hints(items = items, pickedText = pickedText)
    DropdownList(
        items = items,
        pickedText = pickedText,
        extended = extended,
        dropdownHeader = dropdownHeader
    )
}

@Composable
fun DropdownList(
    items: List<String>,
    pickedText: MutableState<String>,
    extended: MutableState<Boolean>,
    dropdownHeader: String
) {
    DropdownMenu(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp),
        expanded = extended.value,
        onDismissRequest = { extended.value = !extended.value }) {
        AnimatedVisibility(
            visible = extended.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { extended.value = !extended.value }
                    )
                    Text(
                        text = dropdownHeader,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                items.forEach {
                    DropdownItem(text = it, pickedText = pickedText, extended = extended)
                }
            }
        }
    }
}

@Composable
fun DropdownItem(text: String, pickedText: MutableState<String>, extended: MutableState<Boolean>) {
    DropdownMenuItem(
        text = {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = text, fontSize = 16.sp)
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "arrowRight",
                    alignment = Alignment.CenterEnd
                )
            }
        },
        onClick = {
            pickedText.value = text
            extended.value = !extended.value
        })
}

@Composable
fun Hints(items: List<String>, pickedText: MutableState<String>) {
    Row {
        for (i in 0 until 3) {
            Text(
                modifier = Modifier
                    .clickable { pickedText.value = items[i] }
                    .padding(end = 5.dp),
                text = items[i],
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
                color = Color.Gray
            )
        }
    }
}