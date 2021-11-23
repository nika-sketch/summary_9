package ge.nlatsabidze.roomexample.ApiData

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "info_table")
@JsonClass(generateAdapter = true)
data class InformationItem(
        @Json(name = "cover")
        val cover: String?,
        @Json(name = "liked")
        val liked: Boolean?,
        @Json(name = "price")
        val price: String?,
        @Json(name = "title")
        val title: String?
) {
        @PrimaryKey(autoGenerate = true) var id: Int = 0
}