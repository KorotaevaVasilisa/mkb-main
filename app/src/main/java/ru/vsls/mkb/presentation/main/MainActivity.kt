package ru.vsls.mkb.presentation.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.vsls.mkb.presentation.standard.ClassificationDiseasesScreen
import ru.vsls.mkb.presentation.navigation.Screen
import ru.vsls.mkb.presentation.tree.TreeScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.vsls.mkb.R
import ru.vsls.mkb.ui.theme.MkbTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )
        setContent {
            val navController = rememberNavController()

            val state by viewModel.screenState.collectAsState()
            var navigationArrow: Int? by remember { mutableStateOf(null) }

            navController.addOnDestinationChangedListener { _, _, arguments ->
                navigationArrow = arguments?.getInt("parentId")
            }

            MkbTheme {
                InnerScreen(
                    navController = navController,
                    startDestination = if (!state.switcher) {
                        Screen.TreeScreen.route
                    } else {
                        Screen.ClassificationScreen.route + "/{parentId}"
                    },
                    switcher = state.switcher,
                    searcher = state.searcher,
                    numberOfPage = navigationArrow,
                    changeSearcherState = viewModel::changeSearcherState,
                    putDataInSharedPreferences = viewModel::putDataInSharedPreferences
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InnerScreen(
    navController: NavHostController,
    startDestination: String,
    switcher: Boolean,
    searcher: Boolean,
    numberOfPage: Int?,
    changeSearcherState: () -> Unit,
    putDataInSharedPreferences: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.mkb_title),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.inversePrimary
                    ),
                    navigationIcon = {
                        when (numberOfPage) {
                            null, 0 -> {}
                            else -> {
                                IconButton(
                                    onClick = {
                                        navController.navigateUp()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { changeSearcherState() }) {
                            if (searcher) {
                                Icon(
                                    painter = painterResource(id = R.drawable.round_search_off),
                                    contentDescription = stringResource(
                                        id = R.string.end_search
                                    )
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.round_search),
                                    contentDescription = stringResource(
                                        id = R.string.start_search
                                    )
                                )
                            }
                        }
                        ThemeSwitcher(state = switcher, onClick = { putDataInSharedPreferences() })
                    }
                )
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(
                    route = Screen.ClassificationScreen.route + "/{parentId}",
                    arguments = listOf(navArgument("parentId") {
                        type = NavType.IntType
                        defaultValue = 0
                    })
                ) { _ ->
                    ClassificationDiseasesScreen(navController, searcher)
                }
                composable(route = Screen.TreeScreen.route) {
                    TreeScreen(searcher)
                }
            }
        }
    }
}




