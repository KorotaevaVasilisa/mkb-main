package ru.vsls.mkb.presentation.tree

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import ru.vsls.mkb.presentation.components.SearchView

@Composable
fun TreeScreen(
    searcher:Boolean
) {
    val viewModel: TreeViewModel = hiltViewModel()
    val diseasesTree by viewModel.diseasesTreeState.collectAsState()
    Column() {
        if (searcher)
            SearchView({})
        ClassificationTreeView(diseasesTree.tree, viewModel::loadChildren)
    }
}
