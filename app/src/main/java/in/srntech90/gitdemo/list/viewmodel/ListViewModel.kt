package `in`.srntech90.gitdemo.list.viewmodel

import `in`.srntech90.gitdemo.list.repos.GitRepository
import `in`.srntech90.gitdemo.model.GitItem
import `in`.srntech90.gitdemo.network.Outcome
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
Created by Tanuj.Sareen on 23,January,2020

ListView Model (perform all Business Logic Operations here)
 **/
class ListViewModel : ViewModel() {

    /*Declare GitList Repository*/
    private val mListRepository: GitRepository?

    /*declare  Mutable Live Data (change change before updating to the UI screen)*/
    private var gitItemLD: MutableLiveData<Outcome<ArrayList<GitItem>>>?

    /*Initialize binding Repository along with Data (MutableLive Data)*/
    init {
        mListRepository = GitRepository()
        gitItemLD = MutableLiveData()
    }


    /*call Developers API*/
    fun listRepos(
        language: String,
        since: String
    ) {
        gitItemLD = mListRepository?.callDevelopersAPI(language, since)
    }

    /*Observe  Movie List*/
    fun observerGitItemList(): MutableLiveData<Outcome<ArrayList<GitItem>>>? {
        return gitItemLD
    }
}