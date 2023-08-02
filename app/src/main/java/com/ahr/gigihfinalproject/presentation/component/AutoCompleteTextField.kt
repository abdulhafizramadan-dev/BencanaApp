package com.ahr.gigihfinalproject.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.ui.theme.GigihFinalProjectTheme
import com.ahr.gigihfinalproject.util.emptyString

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun <T> AutoCompleteTextField(
    modifier: Modifier = Modifier,
    placeholder: String = emptyString(),
    value: String = emptyString(),
    onValueChanged: (String) -> Unit = {},
    onClearClicked: () -> Unit = {},
    onDoneClicked: () -> Unit = {},
    predictions: List<T> = emptyList(),
    focusRequester: FocusRequester,
    leadingIcon: @Composable () -> Unit = {},
    itemContent: @Composable (T) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val heightModifier = if (predictions.size >= 8) {
        Modifier.fillMaxHeight(.4f)
    } else {
        Modifier
    }
    LazyColumn(
        state = lazyListState,
        modifier = modifier
            .clip(RoundedCornerShape(size = 4.dp))
            .fillMaxWidth()
            .then(heightModifier)
    ) {
        stickyHeader {
            TextFieldAutoComplete(
                placeholder = placeholder,
                value = value,
                onValueChanged = onValueChanged,
                onClearClicked = onClearClicked,
                onDoneClicked = onDoneClicked,
                focusRequester = focusRequester,
                leadingIcon = leadingIcon
            )
        }
        if (predictions.isNotEmpty()) {
            items(items = predictions) {
                itemContent(it)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun TextFieldAutoComplete(
    modifier: Modifier = Modifier,
    placeholder: String = emptyString(),
    value: String = emptyString(),
    onValueChanged: (String) -> Unit = {},
    onClearClicked: () -> Unit = {},
    onDoneClicked: () -> Unit = {},
    leadingIcon: @Composable () -> Unit = {},
    focusRequester: FocusRequester
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        textStyle = MaterialTheme.typography.labelLarge,
        singleLine = true,
        maxLines = 1,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.labelLarge,
            )
        },
        trailingIcon = {
            AnimatedVisibility(visible = value.isNotEmpty(), enter = fadeIn(), exit = fadeOut()) {
                IconButton(onClick = onClearClicked) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = stringResource(R.string.desc_clear_text),
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Words
        ),
        keyboardActions = KeyboardActions(onDone = { onDoneClicked() }),
        leadingIcon = leadingIcon
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewTextField() {
    GigihFinalProjectTheme {
        Surface(modifier = Modifier.padding(all = 16.dp)) {
            var value by remember { mutableStateOf("") }
            val dummyNames = listOf("Abdul", "Hafiz", "Ramadan")
            val predictions = remember(key1 = value) {
                if (value.isNotEmpty()) {
                    dummyNames.filter { it.contains(value, true) }
                } else emptyList()
            }
            AutoCompleteTextField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Cari disini",
                value = value,
                onValueChanged = { value = it },
                onClearClicked = { value = "" },
                predictions = predictions,
                focusRequester = FocusRequester.Default
            ) { text ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = text)
                }
            }
        }
    }
}
