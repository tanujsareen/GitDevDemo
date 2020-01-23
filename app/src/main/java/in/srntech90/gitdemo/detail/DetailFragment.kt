package `in`.srntech90.gitdemo.detail

import `in`.srntech90.gitdemo.R
import `in`.srntech90.gitdemo.databinding.DetailFragmentBinding
import `in`.srntech90.gitdemo.model.GitItem
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import timber.log.Timber

/**
Created by Tanuj.Sareen on 23,January,2020
 **/
class DetailFragment : Fragment() {

    private var activity: Activity? = null

    private var detailVB: DetailFragmentBinding? = null

    private var gitItem:GitItem? =null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.i("onAttach")
        this.activity = context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate")
        gitItem=arguments?.getSerializable("gitItem") as GitItem
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("onCreateView")
        detailVB = DataBindingUtil.inflate(inflater,
            R.layout.detail_fragment, container, false)
        initDetailFrag()
        return detailVB?.root
    }

    private fun initDetailFrag() {

        ViewCompat.setTransitionName(
            detailVB?.imgDetail!!,
            TextUtils.concat(gitItem?.avatar).toString()
        )

        ViewCompat.setTransitionName(
            detailVB?.txtUserName!!,
            TextUtils.concat(gitItem?.name).toString()
        )

        detailVB?.imgDetail?.setImageBitmap(arguments?.getParcelable("IMAGE"))
        detailVB?.txtUserName?.text = gitItem?.name

    }
}