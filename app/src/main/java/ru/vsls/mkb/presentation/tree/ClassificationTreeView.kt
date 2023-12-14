package ru.vsls.mkb.presentation.tree

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vsls.mkb.presentation.models.ClassificationTreeModel
import com.mr0xf00.lazytreelist.ExpansionState
import com.mr0xf00.lazytreelist.ExperimentalLazyTreeListApi
import com.mr0xf00.lazytreelist.LazyTreeList
import com.mr0xf00.lazytreelist.LazyTreeListScope

@OptIn(ExperimentalLazyTreeListApi::class)
@Composable
fun ClassificationTreeView(
    treeModes: List<ClassificationTreeModel>,
    confirmedSearchString: String,
    loadChildren: (Int) -> Unit,
) {
    val expansionState = remember { ExpansionState() }

    LazyTreeList(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        expansionState = expansionState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        generateTree(treeModes, confirmedSearchString, loadChildren)
    }
}

fun LazyTreeListScope.generateTree(
    children: List<ClassificationTreeModel>,
    confirmedSearchString: String,
    loadChildren: (Int) -> Unit,
) {
    items(
        count = children.size,
        key = { index ->
            children[index].id
        },
        subItems = { index ->
            generateTree(children[index].children, confirmedSearchString, loadChildren)
        },
        itemContent = { index ->
            val content = children[index]
            DepthStyle(
                depth = depth, modifier = Modifier
                    .fillMaxWidth()
            ) {
                val modifier = Modifier.fillMaxWidth()
                if (content.hasChildren) {
                    ContainerItem(
                        name = content.mkbName,
                        mkbCode = content.mkbCode,
                        isExpanded = isExpanded,
                        confirmedSearchString = confirmedSearchString,
                        onExpand = {
                            if (content.children.isEmpty()) {
                                loadChildren(content.id)
                            }
                            expandItem()
                        },
                        modifier = modifier
                    )
                } else {
                    LeafItem(
                        name = content.mkbName,
                        mkbCode = content.mkbCode,
                        confirmedSearchString = confirmedSearchString,
                        modifier = modifier
                    )
                }
            }
        }
    )
}
