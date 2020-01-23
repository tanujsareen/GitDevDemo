package `in`.srntech90.gitdemo.list.repos

import `in`.srntech90.gitdemo.network.Outcome
import `in`.srntech90.gitdemo.model.GitItem
import `in`.srntech90.gitdemo.network.BaseNetwork
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
Created by Tanuj.Sareen on 23,January,2020
 **/
class GitRepository {

    fun callDevelopersAPI(language: String, since: String): MutableLiveData<Outcome<ArrayList<GitItem>>> {

        Timber.i("request:language->${language}, since->${since}")
        val data = MutableLiveData<Outcome<ArrayList<GitItem>>>()
        data.value = Outcome.loading(true)


        BaseNetwork.callAPI.callDevelopers(language, since).enqueue(object : Callback<ArrayList<GitItem>> {
            override fun onFailure(call: Call<ArrayList<GitItem>>, t: Throwable) {
                Timber.i("exception")
                data.value = Outcome.loading(false)
                data.value = Outcome.failure("Something went wrong")
            }

            override fun onResponse(call: Call<ArrayList<GitItem>>, response: Response<ArrayList<GitItem>>) {
                Timber.i("onResponse: response.body() > ${response.body()}")
                data.value = Outcome.loading(false)
                if (response.isSuccessful && response.body() != null)
                    data.value = Outcome.success(response.body()!!)
                else
                    data.value = Outcome.failure(response.errorBody().toString())
            }

        })

        return data
    }
}