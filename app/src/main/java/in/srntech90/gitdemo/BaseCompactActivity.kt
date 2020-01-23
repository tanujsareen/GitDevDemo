package `in`.srntech90.gitdemo

import `in`.srntech90.gitdemo.databinding.BaseCompactActivityBinding
import `in`.srntech90.gitdemo.detail.DetailFragment
import `in`.srntech90.gitdemo.list.GitListFragment
import `in`.srntech90.gitdemo.model.GitItem
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.transition.*
import timber.log.Timber

/**
Created by Tanuj.Sareen on 23,January,2020
 **/
class BaseCompactActivity : AppCompatActivity() {


    private var baseVB: BaseCompactActivityBinding? = null

    private val DURATION: Long = 600
    private val START_DELAY: Long = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate")
        baseVB = DataBindingUtil.setContentView(this, R.layout.base_compact_activity)
        initBase()
    }

    private fun initBase() {
        setSupportActionBar(baseVB?.toolbar)
        initHomeFrag()
    }


    private fun initHomeFrag() {
        val listFragment = GitListFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.frameLayout, listFragment, "ListFragment")
                addToBackStack("ListFragment")
            }

        fragmentTransaction.commit()
    }

    fun setInitFrag(frag: Fragment) {

        when (frag) {
            frag as GitListFragment -> {
            }
            frag as DetailFragment -> {
            }


        }
    }

    fun isTablet(): Boolean {
        val isBoolean: Boolean

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        val widthDp: Float = metrics.widthPixels / metrics.density
        val heightDp: Float = metrics.heightPixels / metrics.density

        val smallestWidth: Float = widthDp.coerceAtMost(heightDp)

        isBoolean = if (smallestWidth > 720) {
            true
        } else smallestWidth > 600

        return isBoolean
    }

    fun callDetailFrag(gitItem: GitItem, img: ImageView, txtUserName: TextView) {

        val detailFrag = DetailFragment()
        val bundle = Bundle()
        bundle.putSerializable("gitItem", gitItem)
        bundle.putParcelable("IMAGE", (img.drawable as? BitmapDrawable)?.bitmap)
        detailFrag.arguments = bundle
        val enterTransitionSet = TransitionSet()
        enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move))
        enterTransitionSet.duration = DURATION
        enterTransitionSet.startDelay = START_DELAY
        enterTransitionSet.addTransition(ChangeBounds())
        enterTransitionSet.addTransition(ChangeTransform())
        enterTransitionSet.addTransition(ChangeImageTransform())
        detailFrag.sharedElementEnterTransition = enterTransitionSet

        val enterFade = Fade()
        enterFade.startDelay = DURATION + START_DELAY
        enterFade.duration = START_DELAY
        detailFrag.enterTransition = enterFade

        val fragmentTransaction = supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, detailFrag, "DetailFragment")
            addToBackStack("DetailFragment")

        }
        fragmentTransaction.addSharedElement(img, img.transitionName)
        fragmentTransaction.addSharedElement(txtUserName, txtUserName.transitionName)

        fragmentTransaction.commit()

    }

}