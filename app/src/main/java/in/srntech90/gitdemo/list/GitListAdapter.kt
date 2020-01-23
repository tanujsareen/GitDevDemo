package `in`.srntech90.gitdemo.list

import `in`.srntech90.gitdemo.R
import `in`.srntech90.gitdemo.model.GitItem
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

/**
Created by Tanuj.Sareen on 21,January,2020
 **/
class GitListAdapter(
    private val context: Context,
    private val gitItem: ArrayList<GitItem>,
    private val iCallBack: ICallBackSelector
) : RecyclerView.Adapter<GitListAdapter.ListViewHolder>() {

    var count: Int = 1

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgUserPicture: ImageView = itemView.findViewById(R.id.imgUserPicture)
        val txtUserName: TextView = itemView.findViewById(R.id.txtUserName)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.git_list_item, null)
        return ListViewHolder(item)
    }

    override fun getItemCount(): Int {
        return gitItem.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, pos: Int) {


        context.let {

            Glide.with(it).load(gitItem[pos].avatar)
                .fitCenter()
                .thumbnail(0.25f)
                .addListener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.imgUserPicture.scaleType = ImageView.ScaleType.FIT_CENTER
                        holder.imgUserPicture.setImageResource(R.drawable.ic_image_black_50dp)

                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        /*   holder.progressBarGridImage.visibility = View.GONE*/
                        holder.imgUserPicture.scaleType = ImageView.ScaleType.FIT_XY
                        holder.imgUserPicture.setImageDrawable(resource)

                        return true
                    }

                }).into(holder.imgUserPicture)
        }
        holder.txtUserName.text = gitItem[pos].name

        ViewCompat.setTransitionName(
            holder.imgUserPicture,
            TextUtils.concat(gitItem[pos].avatar).toString()
        )

        ViewCompat.setTransitionName(
            holder.txtUserName,
            TextUtils.concat(gitItem[pos].name).toString()
        )


        holder.cardView.setOnClickListener {
            iCallBack.callDetailView(gitItem[pos], holder.imgUserPicture,holder.txtUserName)
        }

    }

    fun initCounter() {
        count = 1
        notifyDataSetChanged()
    }


}