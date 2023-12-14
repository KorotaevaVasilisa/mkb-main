package ru.vsls.mkb.presentation.tree

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.vsls.mkb.presentation.getAnnotatedString

@Composable
inline fun DepthStyle(
    depth: Int,
    modifier: Modifier = Modifier,
    horPad: Dp = 16.dp,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .padding(start = horPad * depth)
    ) {
        content()
    }
}

@Composable
fun LeafItem(
    name: String,
    mkbCode: String,
    confirmedSearchString: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.heightIn(min = 50.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        tonalElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Column {
                Text(
                    text = mkbCode,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = getAnnotatedString(name, confirmedSearchString),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Preview
@Composable
fun LeafItemPreview() {
    LeafItem(mkbCode = "M41.9", name = "Сколиоз неуточненный", confirmedSearchString =  "оз")
}

@Composable
fun ContainerItem(
    name: String,
    mkbCode: String,
    isExpanded: Boolean,
    confirmedSearchString: String,
    onExpand: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onExpand,
        modifier = modifier.heightIn(min = 50.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        tonalElevation = if (isExpanded) 4.dp else 8.dp,
    ) {
        Row(
            Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(weight = 0.9f)) {
                Text(
                    text = mkbCode,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = getAnnotatedString(name, confirmedSearchString),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Icon(
                if (!isExpanded) Icons.Default.KeyboardArrowRight
                else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}
