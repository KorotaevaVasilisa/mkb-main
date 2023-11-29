package ru.vsls.mkb.presentation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import ru.vsls.mkb.ui.theme.md_theme_dark_primary


fun getAnnotatedString(text: String, needle: String): AnnotatedString {
    val indexes = text.indexesOf(needle, true)

    if (indexes.isEmpty()){
        return AnnotatedString(text)
    }

    val annotatedLinkString = buildAnnotatedString {
        append(text)
        for (startIndex in indexes) {
            addStyle(
                style = SpanStyle(
                    color = md_theme_dark_primary, // в идеале цвет брать согласно темы но пока хз как
                    fontWeight = FontWeight.SemiBold,
                ), start = startIndex, end = startIndex + needle.length
            )
        }
    }
    return annotatedLinkString
}
fun String?.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> {
    return this?.let {
        val regex = if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr)
        regex.findAll(this).map { it.range.first }.toList()
    } ?: emptyList()
}
