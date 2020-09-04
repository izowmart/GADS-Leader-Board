package ps.room.gadsleaderboard.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_skill_i_q.*
import ps.room.gadsleaderboard.R
import ps.room.gadsleaderboard.di.module.SkillIQAdapter

@AndroidEntryPoint
class SkillIQFragment : Fragment() {
    private var sectionNumber: Int? = null
    private lateinit var adapter: SkillIQAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sectionNumber = it.getInt(ARG_SECTION_NUMBER)
        }

        setupUI()
    }

    private fun setupUI() {
        adapter = SkillIQAdapter(arrayListOf())
        val linearLayoutManager = recyclerView_iq.layoutManager as LinearLayoutManager
        recyclerView_iq.addItemDecoration(
            DividerItemDecoration(recyclerView_iq.context, linearLayoutManager.orientation)
        )
        recyclerView_iq.adapter = adapter

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_skill_i_q, container, false)
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "skill_iq_leaders"

        @JvmStatic
        fun newInstance(section_number: Int) = SkillIQFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, section_number)

            }
        }
    }
}