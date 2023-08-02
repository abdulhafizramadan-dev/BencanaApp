package com.ahr.gigihfinalproject.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.domain.model.DisasterFilterTimePeriod
import com.ahr.gigihfinalproject.ui.theme.GigihFinalProjectTheme

@Composable
fun DisasterTimePeriodDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    icon: ImageVector,
    title: String,
    timePeriods: List<DisasterFilterTimePeriod> = emptyList(),
    onTimePeriodClicked: (DisasterFilterTimePeriod) -> Unit = {},
    onChooseButtonClicked: (DisasterFilterTimePeriod?) -> Unit = {},
) {
    Dialog(onDismissRequest = onDismissRequest) {
        val selectedDisasterFilterTimePeriod: DisasterFilterTimePeriod? = timePeriods.firstOrNull { it.selected }
        val isButtonChooseEnabled = selectedDisasterFilterTimePeriod != null
        val contentModifier = if (timePeriods.size >= 5) Modifier.fillMaxHeight(0.4f) else Modifier
        Column(
            modifier = modifier
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.background)
                .padding(all = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                imageVector = icon,
                contentDescription = stringResource(R.string.desc_filter_disaster_timeperiod),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(modifier = contentModifier) {
                items(items = timePeriods, key = { it.name }) { timePeriod ->
                    val background = if (timePeriod.selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background
                    Text(
                        text = timePeriod.name,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.large)
                            .background(background)
                            .clickable { onTimePeriodClicked(timePeriod) }
                            .padding(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = onDismissRequest) {
                    Text(text = stringResource(R.string.label_cancel))
                }
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(
                    onClick = { onChooseButtonClicked(selectedDisasterFilterTimePeriod) },
                    enabled = isButtonChooseEnabled
                ) {
                    Text(text = stringResource(R.string.label_choose))
                }

            }
        }
    }
}

@Preview
@Composable
fun PreviewDisasterTimePeriodDialog() {
    GigihFinalProjectTheme {
        Surface {
            val disasterFilterTimePeriods = listOf(
                DisasterFilterTimePeriod(
                    timeSecond = 100,
                    name = "Hari ini 1",
                    selected = false
                ),
                DisasterFilterTimePeriod(
                    timeSecond = 100,
                    name = "Hari ini 2",
                    selected = false
                ),
                DisasterFilterTimePeriod(
                    timeSecond = 100,
                    name = "Hari ini 3",
                    selected = false
                ),
                DisasterFilterTimePeriod(
                    timeSecond = 100,
                    name = "Hari ini 4",
                    selected = false
                ),
                DisasterFilterTimePeriod(
                    timeSecond = 100,
                    name = "Hari ini 5",
                    selected = false
                ),
                DisasterFilterTimePeriod(
                    timeSecond = 100,
                    name = "Hari ini 6",
                    selected = true
                ),
                DisasterFilterTimePeriod(
                    timeSecond = 100,
                    name = "Hari ini 7",
                    selected = false
                ),
                DisasterFilterTimePeriod(
                    timeSecond = 100,
                    name = "Hari ini 8",
                    selected = false
                ),
                DisasterFilterTimePeriod(
                    timeSecond = 100,
                    name = "Hari ini 9",
                    selected = false
                )
            )
            DisasterTimePeriodDialog(
                icon = Icons.Default.CalendarMonth,
                onDismissRequest = {},
                title = "Filter bencana berdasarkan periode waktu",
                timePeriods = disasterFilterTimePeriods
            )
        }
    }
}