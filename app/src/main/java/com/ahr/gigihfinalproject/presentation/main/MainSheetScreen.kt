package com.ahr.gigihfinalproject.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.presentation.component.LatestDisasterItem
import com.ahr.gigihfinalproject.presentation.component.LatestDisasterLoadingItem
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.valentinilk.shimmer.shimmer

@Composable
fun MainSheetScreen(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    onCloseIconClicked: () -> Unit = {},
    disasterGeometryState: DisasterGeometryState = DisasterGeometryState.Loading,
    latestDisasters: List<DisasterGeometry> = emptyList(),
    onDisasterItemClicked: (DisasterGeometry) -> Unit = {}
) {
    val headerPadding = if (isExpanded) 0.dp else 16.dp
    val contentModifier = if (latestDisasters.isNotEmpty()) {
        Modifier.fillMaxHeight()
    } else {
        Modifier.height(396.dp)
    }

    val isLoading = remember(key1 = disasterGeometryState) {
        disasterGeometryState == DisasterGeometryState.Loading
    }

    Column(
        modifier = contentModifier
            .animateContentSize(
                animationSpec = spring()
            )
            .fillMaxWidth()
            .padding(top = 16.dp)
            .then(modifier)
    ) {
        Divider(
            modifier = Modifier
                .clip(CircleShape)
                .height(4.dp)
                .width(48.dp)
                .align(Alignment.CenterHorizontally),
        )

        MainSheetHeader(
            headerPadding = headerPadding,
            isExpanded = isExpanded,
            onCloseIconClicked = onCloseIconClicked,
            loadingState = isLoading
        )
        MainSheetContent(
            disasterReports = latestDisasters,
            onDisasterItemClicked = onDisasterItemClicked,
            loadingState = isLoading
        )
    }
}

@Composable
fun MainSheetHeader(
    modifier: Modifier = Modifier,
    headerPadding: Dp = 0.dp,
    isExpanded: Boolean = false,
    onCloseIconClicked: () -> Unit = {},
    loadingState: Boolean = false
) {
    if (loadingState) {
        MainSheetHeaderLoading()
        return
    }
    MainSheetHeaderSuccess(
        modifier = modifier,
        headerPadding = headerPadding,
        isExpanded = isExpanded,
        onCloseIconClicked = onCloseIconClicked
    )
}

@Composable
fun MainSheetHeaderSuccess(
    modifier: Modifier = Modifier,
    headerPadding: Dp = 0.dp,
    isExpanded: Boolean = false,
    onCloseIconClicked: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(horizontal = headerPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(visible = isExpanded) {
            IconButton(onClick = onCloseIconClicked,
                Modifier
                    .padding(end = 8.dp)
                    .offset(y = 6.dp)) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.desc_close_bottom_sheet)
                )
            }
        }
        Text(
            text = stringResource(R.string.label_latest_disaster_information),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 20.sp,
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
}

@Composable
fun MainSheetHeaderLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 16.dp)
            .shimmer()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .height(22.dp)
            .fillMaxWidth()
    )
}

@Composable
fun MainSheetContent(
    modifier: Modifier = Modifier,
    disasterReports: List<DisasterGeometry> = emptyList(),
    onDisasterItemClicked: (DisasterGeometry) -> Unit = {},
    loadingState: Boolean = false
) {
    if (loadingState) {
        DisasterListLoading()
        return
    }
    if (disasterReports.isEmpty()) {
        DisasterListEmpty()
        return
    }
    DisasterListSuccess(
        disasterReports = disasterReports,
        onDisasterItemClicked = onDisasterItemClicked,
        modifier = modifier
    )
}

@Composable
fun DisasterListSuccess(
    modifier: Modifier = Modifier,
    disasterReports: List<DisasterGeometry> = emptyList(),
    onDisasterItemClicked: (DisasterGeometry) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 8.dp,
            top = 4.dp,
            end = 8.dp,
            bottom = 16.dp
        )
    ) {
        itemsIndexed(items = disasterReports, key = { index, _ -> index }) { _, item ->
            LatestDisasterItem(
                disaster = item,
                onDisasterItemClicked = onDisasterItemClicked
            )
        }
    }
}

@Composable
fun DisasterListLoading(
    modifier: Modifier = Modifier
) {
    repeat(5) {
        LatestDisasterLoadingItem(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun DisasterListEmpty(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .navigationBarsPadding()
            .padding(bottom = 8.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lottie_empty_disaster))
        Column(verticalArrangement = Arrangement.Center) {
            LottieAnimation(
                composition = composition,
                iterations = Int.MAX_VALUE,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxSize(.5f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tidak ada bencana yang ditemukan!",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
