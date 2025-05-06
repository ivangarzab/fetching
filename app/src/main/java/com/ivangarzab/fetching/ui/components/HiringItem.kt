package com.ivangarzab.fetching.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ivangarzab.fetching.domain.model.Hiring
import com.ivangarzab.fetching.ui.theme.FetchingTheme

/**
 * The purpose of this Composable is to display a single hiring item in a list.
 */
@Composable
fun HiringItem(
    modifier: Modifier = Modifier,
    data: Hiring
) {
    val context = LocalContext.current
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = {
            Toast.makeText(context, "Coming Soon!", Toast.LENGTH_SHORT).show()
        }
    ) {
        Row(
            modifier = modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "${data.listId}",
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                modifier = Modifier.weight(3f),
                text = data.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "${data.id}",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview
@Composable
fun HiringItemPreview() {
    FetchingTheme {
        HiringItem(
            modifier = Modifier.background(color = Color.White),
            data = Hiring(id = 1,listId = 1, name = "Sample Item")
        )
    }
}