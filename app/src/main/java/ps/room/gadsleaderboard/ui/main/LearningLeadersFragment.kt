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
import kotlinx.android.synthetic.main.fragment_learning_leaders.*
import kotlinx.android.synthetic.main.fragment_learning_leaders.view.*
import ps.room.gadsleaderboard.R
import ps.room.gadsleaderboard.di.module.LearnersAdapter
import ps.room.gadsleaderboard.util.Status

@AndroidEntryPoint
class LearningLeadersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var sectionNumber: Int? = null
    private val learnersViewModel: LearnersViewModel by viewModels()
    private lateinit var adapter: LearnersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sectionNumber = it.getInt(ARG_SECTION_NUMBER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_learning_leaders, container, false)
        setupUI(view)
        setupObserver()
        return view
    }

    private fun setupObserver() {
        learnersViewModel.learningLearners.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    displayProgressView(false)
                    it.data?.let { data -> adapter.setData(data) }
                }
                Status.ERROR -> {
                    displayProgressView(false)
                    displayErrorMessage(it.message)
                }
                Status.LOADING -> {
                    displayProgressView(true)
                }
            }
        })

    }

    private fun setupUI(view: View) {
        adapter = LearnersAdapter(arrayListOf())

        view.recyclerView_ll.addItemDecoration(
            DividerItemDecoration(
                view.recyclerView_ll.context,
                (view.recyclerView_ll.layoutManager as LinearLayoutManager).orientation
            )
        )

        view.recyclerView_ll.adapter = adapter
    }

    private fun displayErrorMessage(exception: String?) {
        if (exception != null) {
            Toast.makeText(context, exception, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "UNKNOWN ERROR", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayProgressView(isLoading: Boolean) {
        progressBar_ll.visibility = if (isLoading) {
            View.VISIBLE
        } else View.GONE
    }


    companion object {
        private const val ARG_SECTION_NUMBER = "learning leaders"

        @JvmStatic
        fun newInstance(section_number: Int): LearningLeadersFragment {
            return LearningLeadersFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, section_number)
                }
            }
        }
    }
}