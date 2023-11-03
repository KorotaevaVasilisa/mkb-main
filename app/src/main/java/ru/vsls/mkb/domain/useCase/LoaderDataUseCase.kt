package ru.vsls.mkb.domain.useCase

import ru.vsls.mkb.data.GetAssetsTools
import ru.vsls.mkb.domain.model.ClassificationModel
import javax.inject.Inject

class LoaderDataUseCase @Inject constructor(private val assetsInPutStream: GetAssetsTools) {
    fun execute(): List<ClassificationModel> {
        val simpleList = mutableListOf<ClassificationModel>()
        val csvData =
            assetsInPutStream.execute("data_list.csv").bufferedReader().use { it.readText() }
        var count = 0
        val rows: List<String> = csvData.split("\n").map { it.trim() }
        for (row in rows) {
            if (count == 0) {
                count++
                continue
            }
            val cells: List<String> = row.split(";").map { it.trim() }
            val parentId = if (cells[3] == "") 0 else cells[3].toInt()
            val element =
                ClassificationModel(cells[7].toInt(), parentId, cells[4], cells[5])

            simpleList.add(element)
        }

        return simpleList
    }
}
