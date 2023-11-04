package ru.vsls.mkb.presentation.standard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.vsls.mkb.R
import ru.vsls.mkb.presentation.components.SearchView
import ru.vsls.mkb.presentation.navigation.Screen

@Composable
fun ClassificationDiseasesScreen(
    navController: NavController,
    searcher: Boolean,
) {
    val viewModel: DiseasesViewModel = hiltViewModel()
    val diseasesState by viewModel.diseasesStandState.collectAsState()
    Column {
        if (searcher)
            SearchView(
                diseasesState.searchContent,
                viewModel::searchDiseases,
                viewModel::changeContent
            )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(diseasesState.diseases) { item ->

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    val modifier = Modifier.fillMaxWidth()
                    ItemList(
                        item.disease.mkbName,
                        item.disease.mkbCode,
                        item.children.isNotEmpty(),
                        onClick = {
                            if (item.children.isNotEmpty())
                                navController.navigate(Screen.ClassificationScreen.route + "/${item.disease.id}")
                        },
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Preview()
@Composable()
fun ShowScreen() {
    ItemList(
        "Мочекаменная болезнь",
        "N20-N23",
        true,
        {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemList(
    mkbName: String,
    mkbCode: String,
    hasChildren: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.heightIn(min = 50.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(weight = 0.9f)) {
                Text(
                    text = mkbCode,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(text = mkbName, color = MaterialTheme.colorScheme.onSecondaryContainer)
            }
            if (hasChildren) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(id = R.string.onwards),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

        }
    }
}
