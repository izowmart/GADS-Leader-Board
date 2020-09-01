package ps.room.gadsleaderboard.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ps.room.gadsleaderboard.R

class LearningLeadersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var sectionNumber: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sectionNumber = it.getInt(ARG_SECTION_NUMBER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning_leaders, container, false)
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "learning leaders"
        @JvmStatic
        fun newInstance(section_number: Int) : LearningLeadersFragment{
            return LearningLeadersFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER,section_number)
                }
            }
            }
    }
}