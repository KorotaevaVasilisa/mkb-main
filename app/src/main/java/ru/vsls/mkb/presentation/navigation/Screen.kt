package ru.vsls.mkb.presentation.navigation

sealed class Screen(val route:String){
    data object ClassificationScreen: Screen(route = "classification")
    data object TreeScreen: Screen(route = "tree")

}
