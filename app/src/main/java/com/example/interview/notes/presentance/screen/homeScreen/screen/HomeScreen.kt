package com.example.interview.notes.presentance.screen.homeScreen.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interview.notes.data.mapper.toModel
import com.example.interview.notes.presentance.component.BusComponent
import com.example.interview.notes.presentance.screen.homeScreen.stateEventt.HomeEvent
import com.example.interview.notes.presentance.screen.homeScreen.stateEventt.HomeState
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeState: HomeState,
    homeEvent: (HomeEvent) -> Unit
) {

    var alertDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val clock = rememberUseCaseState()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "BusTime",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null
                        )
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                alertDialog = !alertDialog
                homeEvent(HomeEvent.FetchScreen(null))

            }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null
                )
            }
        }
    ) { paddingValue ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(homeState.busList) { busEntity ->
                BusComponent(
                    busModel = busEntity.toModel(),
                    onCardClick = {
                        alertDialog = true
                        homeEvent(HomeEvent.FetchScreen(it))
                    }
                )
            }
        }
    }



    ClockDialog(
        state = clock,
        selection = ClockSelection.HoursMinutes { hours, minus ->
            val calender = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY,hours)
                set(Calendar.MINUTE,minus)
            }
            homeEvent(HomeEvent.Alarm(calender.timeInMillis))
        }
    )

    if (alertDialog) {
        AddDialog(
            onAlertDialog = {
                alertDialog = it
            },
            clock = clock,
            homeState = homeState,
            homeEvent = homeEvent
        )
    }

}

@Composable
fun AddDialog(
    onAlertDialog: (Boolean) -> Unit,
    clock: UseCaseState,
    homeState: HomeState,
    homeEvent: (HomeEvent) -> Unit
) {

    AlertDialog(
        onDismissRequest = {
            onAlertDialog(false)
        },
        confirmButton = {
            if (homeState.busEntity != null) {
                TextButton(onClick = {
                    homeEvent(HomeEvent.SaveBtn)
                    onAlertDialog(false)
                }) {
                    Text(text = "Update")
                }
            } else {
                TextButton(onClick = {
                    homeEvent(HomeEvent.SaveBtn)
                    onAlertDialog(false)
                }) {
                    Text(text = "Save")
                }
            }
        },
        dismissButton = {
            if (homeState.busEntity != null) {
                OutlinedButton(onClick = {
                    homeEvent(HomeEvent.DeleteBtn)
                    onAlertDialog(false)
                }) {
                    Text(text = "Delete")
                }
            } else {
                OutlinedButton(onClick = { onAlertDialog(false) }) {
                    Text(text = "Cancel")
                }
            }
        },
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 3.dp,
        title = {
            if (homeState.busEntity != null) {
                Text(
                    text = "Update or Delete",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                Text(
                    text = "Insert",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                OutlinedTextField(
                    value = homeState.name,
                    onValueChange = {
                        homeEvent(HomeEvent.BusName(it))
                    },
                    placeholder = {
                        Text(text = "Bus Name")
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                )
                OutlinedTextField(
                    value = homeState.passenger,
                    onValueChange = {
                        homeEvent(HomeEvent.Passenger(it))
                    },
                    placeholder = {
                        Text(text = "Passenger")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                )


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Set Alarm",
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 18.sp
                        )

                        IconButton(onClick = {
                            clock.show()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Alarm,
                                contentDescription = null
                            )
                        }

                    }



                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "One Time Alarm",
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 18.sp
                        )

                        Switch(checked = homeState.oneTime,
                            onCheckedChange = {
                                homeEvent(HomeEvent.OneTimeAlarm(it))
                            })

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Repeat Time Alarm",
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 18.sp
                        )

                        Switch(checked = homeState.repeatTime,
                            onCheckedChange = {
                                homeEvent(HomeEvent.RepeatTimeAlarm(it))
                            })

                    }
                }


            }
        }
    )
}