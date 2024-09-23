package com.example.interview.notes.presentance.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interview.notes.domain.model.BusModel
import com.example.interview.notes.utils.timeConvert
import com.example.interview.ui.theme.InterviewTheme

@Composable
fun BusComponent(
    busModel: BusModel,
    onCardClick : (String) -> Unit
) {

    Card(
        onClick = {
            busModel.id?.let { onCardClick(it.toString()) }
        },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp, pressedElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = busModel.busName,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )
            Text(
                text = "Passenger: ${busModel.noPassenger}",
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )

            Text(
                text = "One time: ${busModel.oneTimeAlarm}",
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )
            Text(
                text = "Repeat time: ${busModel.repeatTimeAlarm}",
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                modifier = Modifier
                    .height(6.dp)
            )
           Row (
               modifier=Modifier
                   .fillMaxWidth(),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.End
           ){

                   Icon(imageVector = Icons.Rounded.Timer,
                       contentDescription = null)

               Spacer(modifier = Modifier
                   .width(4.dp))
               Text(
                   text = timeConvert(busModel.time),
                   textAlign = TextAlign.Right,
                   fontWeight = FontWeight.ExtraBold,
                   fontFamily = FontFamily.SansSerif,
               )
           }

        }

    }

}

@Preview
@Composable
private fun AE() {
    BusComponent(busModel = BusModel(busName = "as", noPassenger = "12", time = System.currentTimeMillis())) {
        
    }
}