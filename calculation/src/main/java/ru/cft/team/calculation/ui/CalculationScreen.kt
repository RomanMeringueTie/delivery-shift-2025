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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cft.team.calculation.R
import ru.cft.team.calculation.presentation.CalculationScreenAction
import ru.cft.team.calculation.presentation.CalculationScreenState
import ru.cft.team.calculation.presentation.CalculationViewModel
import ru.cft.team.calculation.presentation.PackageTypesState
import ru.cft.team.calculation.presentation.PointsState
import ru.cft.team.network.model.PackageType
import ru.cft.team.network.model.Point



@Composable
fun CalculationScreen(viewModel: CalculationViewModel = hiltViewModel(), onCalculate: () -> Unit) {

    val state by viewModel.state.collectAsState()

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

            when (state.pointsState) {
                is PointsState.Content -> {
                    PointContent(state = state, viewModel = viewModel)
                }

                is PointsState.Failure -> {
                    Text(text = (state.pointsState as PointsState.Failure).message)
                }

                PointsState.Loading -> {
                    CircularProgressIndicator()
                }
            }
            when (state.packageTypesState) {
                is PackageTypesState.Content -> {
                    PackageContent(state = state, viewModel = viewModel)
                }

                is PackageTypesState.Failure -> {
                    AlertDialog(
                        onDismissRequest = { },
                        confirmButton = { viewModel.onAction(CalculationScreenAction.RetryLoad) },
                        text = { Text((state.packageTypesState as PackageTypesState.Failure).message) })
                }

                PackageTypesState.Loading -> {
                    CircularProgressIndicator()
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 10.dp)
                    .height(60.dp), onClick = onCalculate, colors = ButtonColors(
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
fun PointContent(state: CalculationScreenState, viewModel: CalculationViewModel) {
    PointItemPicker(
        title = stringResource(R.string.send_city),
        pickedText = state.pickedSendCity.name,
        items = (state.pointsState as PointsState.Content).list,
        extended = state.sendCityExtended,
        onExtend = { viewModel.onAction(CalculationScreenAction.ChangeSendCityExtended) },
        onPick = { viewModel.onAction(CalculationScreenAction.SetPickedSendCity(it)) },
    )
    PointItemPicker(
        title = stringResource(R.string.receive_city),
        pickedText = state.pickedReceiveCity.name,
        items = state.pointsState.list,
        extended = state.receiveCityExtended,
        onExtend = { viewModel.onAction(CalculationScreenAction.ChangeReceiveCityExtended) },
        onPick = {
            viewModel.onAction(
                CalculationScreenAction.SetPickedReceiveCity(
                    it
                )
            )
        },
    )
}

@Composable
fun PackageContent(state: CalculationScreenState, viewModel: CalculationViewModel) {
    PackageItemPicker(
        title = stringResource(R.string.package_size),
        pickedText = state.pickedPackageType.name,
        items = (state.packageTypesState as PackageTypesState.Content).list,
        extended = state.packageTypeExtended,
        onExtend = { viewModel.onAction(CalculationScreenAction.ChangePackageTypeExtended) },
        onPick = {
            viewModel.onAction(
                CalculationScreenAction.SetPickedPackageType(
                    it
                )
            )
        },
    )
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
                text = stringResource(R.string.point_camera_on_qr),
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun PointItemPicker(
    title: String,
    pickedText: String,
    extended: Boolean,
    items: List<Point>,
    onPick: (Point) -> Unit,
    onExtend: () -> Unit
) {
    Text(text = title, fontSize = 14.sp, textAlign = TextAlign.Start)
    Card(colors = CardColors(
        contentColor = Color.Black,
        containerColor = Color.White,
        disabledContentColor = Color.Black,
        disabledContainerColor = Color.White
    ),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
            .clickable { onExtend() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Image(Icons.Default.Place, "pickerIcon")
            Text(
                modifier = Modifier.padding(start = 5.dp), text = pickedText, fontSize = 16.sp
            )
            Image(
                modifier = Modifier.fillMaxWidth(),
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "arrowDown",
                alignment = Alignment.CenterEnd
            )
        }
    }
    PointHints(items = items) {
        onPick(it)
    }
    PointDropdownList(
        items = items,
        extended = extended,
        onPick = { onPick(it) },
        onExpand = onExtend
    )
}

@Composable
fun PackageItemPicker(
    title: String,
    pickedText: String,
    extended: Boolean,
    items: List<PackageType>,
    onPick: (PackageType) -> Unit,
    onExtend: () -> Unit
) {
    Text(text = title, fontSize = 14.sp, textAlign = TextAlign.Start)
    Card(colors = CardColors(
        contentColor = Color.Black,
        containerColor = Color.White,
        disabledContentColor = Color.Black,
        disabledContainerColor = Color.White
    ),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
            .clickable { onExtend() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Image(Icons.Default.Email, "pickerIcon")
            Text(
                modifier = Modifier.padding(start = 5.dp), text = pickedText, fontSize = 16.sp
            )
            Image(
                modifier = Modifier.fillMaxWidth(),
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "arrowDown",
                alignment = Alignment.CenterEnd
            )
        }
    }
    PackageDropdownList(
        items = items,
        extended = extended,
        onPick = { onPick(it) },
        onExpand = onExtend
    )
}

@Composable
fun PointDropdownList(
    items: List<Point>,
    onPick: (Point) -> Unit,
    onExpand: () -> Unit,
    extended: Boolean,
) {
    DropdownMenu(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp),
        expanded = extended,
        onDismissRequest = onExpand
    ) {
        AnimatedVisibility(
            visible = extended, enter = fadeIn(), exit = fadeOut()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { onExpand() })
                    Text(
                        text = stringResource(id = R.string.where),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                items.forEach { item ->
                    PointDropdownItem(
                        point = item,
                        onClick = { onPick(it); onExpand() }
                    )
                }
            }
        }
    }
}

@Composable
fun PointDropdownItem(point: Point, onClick: (Point) -> Unit) {
    DropdownMenuItem(text = {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = point.name, fontSize = 16.sp)
            Image(
                modifier = Modifier.fillMaxWidth(),
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = "arrowRight",
                alignment = Alignment.CenterEnd
            )
        }
    }, onClick = { onClick(point) })
}

@Composable
fun PackageDropdownList(
    items: List<PackageType>,
    onPick: (PackageType) -> Unit,
    onExpand: () -> Unit,
    extended: Boolean,
) {
    DropdownMenu(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 10.dp),
        expanded = extended,
        onDismissRequest = onExpand
    ) {
        AnimatedVisibility(
            visible = extended, enter = fadeIn(), exit = fadeOut()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { onExpand() })
                    Text(
                        text = stringResource(id = R.string.package_size),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                items.forEach { item ->
                    PackageDropdownItem(
                        point = item,
                        onClick = { onPick(it); onExpand() }
                    )
                }
            }
        }
    }
}

@Composable
fun PackageDropdownItem(point: PackageType, onClick: (PackageType) -> Unit) {
    DropdownMenuItem(text = {
        Text(
            text = "${point.name}, ${point.length}x${point.width}x${point.height}",
            fontSize = 16.sp
        )
    }, onClick = { onClick(point) })
}

@Composable
fun PointHints(items: List<Point>, onCLick: (Point) -> Unit) {
    Row {
        for (i in 0 until 3) {
            Text(modifier = Modifier
                .clickable { onCLick(items[i]) }
                .padding(end = 5.dp),
                text = items[i].name,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
                color = Color.Gray)
        }
    }
}