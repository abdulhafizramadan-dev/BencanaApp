package com.ahr.gigihfinalproject.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.Resource
import com.ahr.gigihfinalproject.presentation.component.LatestDisasterItem

@Composable
fun MainSheetContent(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    onCloseIconClicked: () -> Unit = {},
    latestDisasters: Resource<List<DisasterGeometry>> = Resource.Idling
) {
    val headerPadding = if (isExpanded) 0.dp else 16.dp
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Divider(
            modifier = Modifier
                .clip(CircleShape)
                .height(4.dp)
                .width(48.dp)
                .align(Alignment.CenterHorizontally),
        )
        Row(
            modifier = Modifier
                .padding(horizontal = headerPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(visible = isExpanded) {
                IconButton(onClick = onCloseIconClicked,
                    Modifier
                        .padding(end = 8.dp)
                        .offset(y = 4.dp)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.desc_close_bottom_sheet)
                    )
                }
            }
            Text(
                text = stringResource(R.string.label_latest_disaster_information),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )
        }
        AnimatedVisibility(visible = isExpanded) {
            Divider(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        when (latestDisasters) {
            Resource.Idling -> {}
            Resource.Loading -> {}
            is Resource.Error -> {}
            is Resource.Success -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(items = latestDisasters.data.map { it.disasterProperties }, key = { it.pkey }) {
                        LatestDisasterItem(
                            disasterProperties = it,
                        )
                    }
                }
            }
        }
    }
}