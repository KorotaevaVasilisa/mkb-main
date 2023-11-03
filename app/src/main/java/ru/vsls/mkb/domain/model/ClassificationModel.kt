package ru.vsls.mkb.domain.model

data class ClassificationModel(
    val id:Int,
    val parentId:Int,
    val mkbCode:String,
    val mkbName:String
)
