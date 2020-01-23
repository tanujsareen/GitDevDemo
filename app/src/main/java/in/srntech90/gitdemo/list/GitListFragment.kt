package `in`.srntech90.gitdemo.list

import `in`.srntech90.gitdemo.network.Outcome
import `in`.srntech90.gitdemo.BaseCompactActivity
import `in`.srntech90.gitdemo.R
import `in`.srntech90.gitdemo.databinding.ListFragmentBinding
import `in`.srntech90.gitdemo.list.viewmodel.ListViewModel
import `in`.srntech90.gitdemo.model.GitItem
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


    private var activity: Activity? = null

    private var listVB: ListFragmentBinding? = null

    private val viewModel by activityViewModels<ListViewModel>()


    private var gitItems: ArrayList<GitItem> = ArrayList()

    private var movieAdapter: GitListAdapter? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.i("onAttach")
        this.activity = context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate")
    }

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

    private fun initListFrag() {
        onConfigurationChanged(resources.configuration)
        (listVB?.recycleViewer?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            true
        listVB?.recycleViewer?.setHasFixedSize(true)


        activity?.let {

            if (movieAdapter == null) {

                movieAdapter = GitListAdapter(it, gitItems, object : ICallBackSelector {
                    override fun callDetailView(gitItem: GitItem, img: ImageView, txtUserName: TextView) {
                        (activity as BaseCompactActivity).callDetailFrag(gitItem, img, txtUserName)
                    }
                })

                listVB?.recycleViewer?.adapter = movieAdapter

                /*(activity as BaseCompactActivity).search_badge.setQuery(
                    resources.getString(R.string.app_name),
                    true
                )*/
                callMovies(
                    "Java",
                    "weekly"
                )
                observeData()
            } else {
                setValues(Outcome.loading(false))
                listVB?.recycleViewer?.adapter = movieAdapter
            }
        }

    }

    private fun observeData() {
        activity?.let {
            viewModel.observerMovieList()
                ?.observe(this, Observer {
                    setValues(it)
                })
        }
    }


    private fun callMovies(language: String, since: String) {
        viewModel.listRepos(language, since)
    }

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

    private fun showFailure(error: String) {

    }
}