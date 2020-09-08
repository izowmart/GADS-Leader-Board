package ps.room.gadsleaderboard.di.module

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.single_learning_leader_item.view.*
import ps.room.gadsleaderboard.R
import ps.room.gadsleaderboard.model.Learners

class LearnersAdapter(private val learningLearners: ArrayList<Learners>) :
    RecyclerView.Adapter<LearnersAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(learner: Learners) {
            itemView.leaning_leader_name.text = learner.name
            itemView.leaning_leader_country.text = learner.country
            itemView.leaning_leader_hours.text = learner.hours.toString()

            val options = RequestOptions().placeholder(R.drawable.btn_background)
            Glide.with(itemView.context)
                .applyDefaultRequestOptions(options)
                .load(learner.badgeUrl)
                .into(itemView.learning_leader_imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.single_learning_leader_item, parent, false)
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val learner = learningLearners[position]
        holder.bind(learner)
    }

    override fun getItemCount() = learningLearners.size

    fun setData(learner: List<Learners>) {
        this.learningLearners.addAll(learner)
        notifyDataSetChanged()
    }
}