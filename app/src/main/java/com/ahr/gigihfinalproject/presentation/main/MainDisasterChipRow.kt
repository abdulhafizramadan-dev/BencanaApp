package com.ahr.gigihfinalproject.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.ui.theme.GigihFinalProjectTheme

@Composable
fun RowMainDisasterChip(
    modifier: Modifier = Modifier,
    selected: DisasterType? = null,
    items: List<DisasterType> = emptyList(),
    onChipClicked: (DisasterType?) -> Unit = {}
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(items = items, key = { it.code }) { disaster ->
            MainDisasterChip(
                selected = selected?.code == disaster.code,
                disasterType = disaster,
                onClicked = onChipClicked
            )
        }
    }
}

@Composable
fun MainDisasterChip(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    disasterType: DisasterType = DisasterType(),
    onClicked: (DisasterType?) -> Unit = {},
) {
    val containerColor =
        if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    val contentColor =
        if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(containerColor)
            .clickable {
                val disaster = if (!selected) disasterType else null
                onClicked(disaster)
            }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = contentColor
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = disasterType.name,
            style = MaterialTheme.typography.labelLarge.copy(color = contentColor)
        )
    }
}


@Preview
@Composable
fun PreviewRowMainDisasterChip() {
    GigihFinalProjectTheme {
        Surface {
//            val items = (0..9).map { "Banjir" }
//            RowMainDisasterChip(items = items)
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewMainDisasterChip() {
    GigihFinalProjectTheme {
        Surface {
            Column {
                MainDisasterChip(
                    modifier = Modifier.padding(16.dp),
                    disasterType = DisasterType()
                )
                MainDisasterChip(
                    modifier = Modifier.padding(16.dp),
                    disasterType = DisasterType(),
                    selected = true
                )

            }
        }
    }
}
