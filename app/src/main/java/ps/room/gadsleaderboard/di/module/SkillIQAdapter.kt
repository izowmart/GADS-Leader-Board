package ps.room.gadsleaderboard.di.module

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.single_skill_iq_item.view.*
import ps.room.gadsleaderboard.R
import ps.room.gadsleaderboard.model.SkilledIQLearners

class SkillIQAdapter(private val skilledIQLearners: ArrayList<SkilledIQLearners>) :
    RecyclerView.Adapter<SkillIQAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(skilledIQLearners: SkilledIQLearners) {
            itemView.skill_iq_name.text = skilledIQLearners.name
            itemView.skill_iq_hours.text = skilledIQLearners.score.toString()
            itemView.skill_iq_country.text = skilledIQLearners.country

            val options = RequestOptions().placeholder(R.drawable.btn_background)
            Glide.with(itemView.context)
                .applyDefaultRequestOptions(options)
                .load(skilledIQLearners.badge_url)
                .into(itemView.skill_iq_imageView)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.single_skill_iq_item, parent, false)
    )

    override fun getItemCount() = skilledIQLearners.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(skilledIQLearners[position])
    }

    fun setData(learners: List<SkilledIQLearners>) {
        skilledIQLearners.addAll(learners)
    }
}