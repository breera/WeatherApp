package com.breera.feature_home.presentation.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.breera.feature_home.R
import com.breera.theme.theme.WeatherAppTheme
import ir.kaaveh.sdpcompose.sdp

/**
 * A search bar component for entering and searching city names.
 *
 * @param query The current text in the search bar.
 * @param onQueryChange Callback when the text changes.
 * @param onItemSearch Callback when the search action is triggered.
 * @param modifier Modifier for styling the search bar.
 *
 */

@Composable
fun CitySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onItemSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Provide custom text selection colors,
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = Color.Black,
            backgroundColor = Color.Black
        )
    ) {
        // OutlinedTextField, where the user inputs the city name
        OutlinedTextField(
            textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal),
            value = query,
            onValueChange = onQueryChange,
            modifier = modifier,
            shape = RoundedCornerShape(10.sdp),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onItemSearch()
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black,
                unfocusedContainerColor = MaterialTheme.colorScheme.onSurface,
                focusedContainerColor = MaterialTheme.colorScheme.onSurface,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_hint),
                    style = TextStyle(color = Color.Black),
                    color = Color.Black
                )
            },
            singleLine = true,
            trailingIcon = {
                // The search icon,
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon_description),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CitySearchBarPreview() {
    WeatherAppTheme {
        CitySearchBar(
            query = "",
            onQueryChange = { },
            onItemSearch = {},
            modifier = Modifier
        )
    }
}
