package `in`.srntech90.gitdemo.list.viewmodel

import `in`.srntech90.gitdemo.network.Outcome
import `in`.srntech90.gitdemo.list.repos.GitRepository
import `in`.srntech90.gitdemo.model.GitItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
Created by Tanuj.Sareen on 23,January,2020
 **/
class ListViewModel : ViewModel() {

    private val mListRepository: GitRepository?

    private var gitItemLD: MutableLiveData<Outcome<ArrayList<GitItem>>>?

    init {
        mListRepository = GitRepository()
        gitItemLD = MutableLiveData()
    }


    fun listRepos(
        language: String,
        since: String
    ) {
        gitItemLD = mListRepository?.callDevelopersAPI(language, since)
    }

    fun observerMovieList(): MutableLiveData<Outcome<ArrayList<GitItem>>>? {
        return gitItemLD
    }
}