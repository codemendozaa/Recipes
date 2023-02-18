package com.example.recipes.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipes.R
import com.example.recipes.ui.theme.MEDIUM_PADDING
import com.example.recipes.ui.theme.SMALL_PADDING
import com.example.recipes.ui.theme.Shapes

@Composable
fun SearchWidget(text: String, onTextChange: (String) -> Unit) {
    Surface(
        shape = Shapes.medium,
        modifier = Modifier.padding(horizontal = MEDIUM_PADDING, vertical = MEDIUM_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = onTextChange,
            leadingIcon = {
                Icon(
                    modifier = Modifier.padding(horizontal = SMALL_PADDING),
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_here_placeholder)
                )
            },
            placeholder = {
                Text(text = stringResource(id = R.string.search_here_placeholder))
            },
            maxLines = 1,
            singleLine = true
        )
    }
}

@Preview
@Composable
fun SearchWidgetPreview() {
    SearchWidget(text = "", onTextChange = {})
}
