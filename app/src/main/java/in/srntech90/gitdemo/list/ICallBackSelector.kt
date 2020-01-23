package `in`.srntech90.gitdemo.list

import `in`.srntech90.gitdemo.model.GitItem
import android.widget.ImageView
import android.widget.TextView

/**
Created by Tanuj.Sareen on 22,January,2020
 **/
interface ICallBackSelector {

    fun callDetailView(gitItem: GitItem, img: ImageView, txtUserName: TextView)

}