package com.ahr.gigihfinalproject.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.domain.model.DisasterProperties
import com.ahr.gigihfinalproject.ui.theme.GigihFinalProjectTheme
import com.ahr.gigihfinalproject.util.toDisasterTimeFormat

@Composable
fun LatestDisasterItem(
    modifier: Modifier = Modifier,
    disasterProperties: DisasterProperties,
) {
    Row(
        modifier = modifier
    ) {
        AsyncImage(
            model = disasterProperties.imageUrl,
            contentDescription = disasterProperties.text,
            modifier = Modifier
                .clip(RoundedCornerShape(size = 16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .width(100.dp)
                .height(72.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = disasterProperties.text,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = disasterProperties.status,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .clip(RoundedCornerShape(size = 8.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.format_disaster_source, disasterProperties.source),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(
                        R.string.format_disaster_type,
                        disasterProperties.disasterType
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = disasterProperties.createdAt.toDisasterTimeFormat(),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewLatestDisasterItem() {
    GigihFinalProjectTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LatestDisasterItem(
                disasterProperties = DisasterProperties(
                    "",
                    "earthquake",
                    "22",
                    "231",
                    "grasp",
                    "Trainer - Maulana Bahrul alam",
                    "confirmed"
                ),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
