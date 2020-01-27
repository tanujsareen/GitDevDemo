package `in`.srntech90.gitdemo.list

import `in`.srntech90.gitdemo.BaseCompactActivity
import `in`.srntech90.gitdemo.R
import `in`.srntech90.gitdemo.databinding.ListFragmentBinding
import `in`.srntech90.gitdemo.list.viewmodel.ListViewModel
import `in`.srntech90.gitdemo.model.GitItem
import `in`.srntech90.gitdemo.network.Outcome
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import timber.log.Timber

/**
Created by Tanuj.Sareen on 23,January,2020
 **/
class GitListFragment : Fragment() {


    /*Context for current Fragment*/
    private var activity: Activity? = null

    /*layout Binding of Git List Fragment*/
    private var listVB: ListFragmentBinding? = null

    /*Initialize View Model using Kotlin KTX Extension*/
    private val viewModel by activityViewModels<ListViewModel>()

    /*Initail Dummy List for Collection of Elements*/
    private var gitItems: ArrayList<GitItem> = ArrayList()

    /*Custom List Adapter*/
    private var movieAdapter: GitListAdapter? = null

    /*providing Context after reattaching scenarios*/
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.i("onAttach")
        this.activity = context as Activity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate")
    }

    /*Set content View using Databinding*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("onCreateView")
        (activity as BaseCompactActivity).setInitFrag(this)
        listVB = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        initListFrag()
        return listVB?.root
    }

    /*initialze GitListFrag */
    private fun initListFrag() {

        /*set configuration Changes*/
        onConfigurationChanged(resources.configuration)
        (listVB?.recycleViewer?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            true
        listVB?.recycleViewer?.setHasFixedSize(true)


        /*check context for current Fragment*/
        activity?.let {

            if (movieAdapter == null) {

                movieAdapter = GitListAdapter(it, gitItems, object : ICallBackSelector {
                    override fun callDetailView(
                        gitItem: GitItem,
                        img: ImageView,
                        txtUserName: TextView
                    ) {
                        (activity as BaseCompactActivity).callDetailFrag(gitItem, img, txtUserName)
                    }
                })

                listVB?.recycleViewer?.adapter = movieAdapter

                /*API Call*/
                callMovies(
                    "Java",
                    "weekly"
                )

                /*call Observer*/
                observeData()
            } else {
                setValues(Outcome.loading(false))
                listVB?.recycleViewer?.adapter = movieAdapter
            }
        }

    }

    /*Connect Observer / Data Emit to UI*/
    private fun observeData() {
        activity?.let {
            viewModel.observerGitItemList()
                ?.observe(this, Observer {
                    setValues(it)
                })
        }
    }


    /*Call Git Developer API from List View Model*/
    private fun callMovies(language: String, since: String) {
        viewModel.listRepos(language, since)
    }

    /*Notify Changes for data emit according to number of columns in the grid List Card */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.orientation) {
            Configuration.ORIENTATION_UNDEFINED -> {
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                if ((activity as BaseCompactActivity).isTablet()) {

                    val layoutManager = GridLayoutManager(activity, 5)
                    listVB?.recycleViewer?.layoutManager = layoutManager


                } else {
                    val layoutManager = GridLayoutManager(activity, 3)
                    listVB?.recycleViewer?.layoutManager = layoutManager
                }

            }
            Configuration.ORIENTATION_PORTRAIT -> {
                if ((activity as BaseCompactActivity).isTablet()) {
                    val layoutManager = GridLayoutManager(activity, 4)
                    listVB?.recycleViewer?.layoutManager = layoutManager

                } else {
                    val layoutManager = GridLayoutManager(activity, 2)
                    listVB?.recycleViewer?.layoutManager = layoutManager

                }
            }
        }

        movieAdapter?.notifyDataSetChanged()
    }


    /*set Data from Observer*/
    private fun setValues(outCome: Outcome<ArrayList<GitItem>>?) {

        when (outCome) {
            is Outcome.Progress -> {

                listVB?.progressBar?.visibility = if (outCome.loading)
                    View.VISIBLE
                else
                    View.GONE
            }
            is Outcome.Failure -> showFailure(outCome.error)
            is Outcome.Success -> {

                outCome.data.forEach {
                    gitItems.add(it)
                }
                movieAdapter?.notifyDataSetChanged()
            }

        }
    }

    /*Update API Failur / can even start the scenario again*/
    private fun showFailure(error: String) {

    }
}