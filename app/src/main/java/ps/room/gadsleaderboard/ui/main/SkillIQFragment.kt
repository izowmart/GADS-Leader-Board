package ps.room.gadsleaderboard.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_skill_i_q.*
import kotlinx.android.synthetic.main.fragment_skill_i_q.view.*
import ps.room.gadsleaderboard.R
import ps.room.gadsleaderboard.di.module.SkillIQAdapter
import ps.room.gadsleaderboard.util.DataState

@AndroidEntryPoint
class SkillIQFragment : Fragment() {
    private var sectionNumber: Int? = null
    private lateinit var adapter: SkillIQAdapter
    private val viewModel: SkillIQViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sectionNumber = it.getInt(ARG_SECTION_NUMBER)
        }


    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_skill_i_q, container, false)
        setupUI(view)
        setupObserver()
        return view
    }


    private fun setupObserver() {
        viewModel.skilledIQLearners.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Success -> {
                    displayProgressView(false)
                    it.data?.let { it1 -> adapter.setData(it1) }
                }
                is DataState.Error -> {
                    displayProgressView(false)
                    displayErrorMessage(it.exception)
                }
                DataState.Loading -> {
                    displayProgressView(true)
                }
            }
        })
    }

    private fun displayErrorMessage(exception: String?) {
        if (exception != null) {
            Toast.makeText(context, exception, Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "UNKNOWN ERROR", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayProgressView(isLoading: Boolean) {
        progressBar_iq.visibility = if (isLoading) {
            View.VISIBLE
        } else View.GONE
    }

    private fun setupUI(view: View) {
        view.recyclerView_iq.layoutManager = LinearLayoutManager(context)
        adapter = SkillIQAdapter(arrayListOf())
        view.recyclerView_iq.addItemDecoration(
            DividerItemDecoration(view.recyclerView_iq.context, (view.recyclerView_iq.layoutManager as LinearLayoutManager).orientation)
        )
        view.recyclerView_iq.adapter = adapter

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