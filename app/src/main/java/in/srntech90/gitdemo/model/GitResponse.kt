package `in`.srntech90.gitdemo.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GitItem(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("repo")
    val repo: Repo,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("username")
    val username: String
) : Serializable

data class Repo(
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) : Serializable