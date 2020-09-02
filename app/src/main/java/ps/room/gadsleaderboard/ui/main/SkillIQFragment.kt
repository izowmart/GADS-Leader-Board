package ps.room.gadsleaderboard.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ps.room.gadsleaderboard.R

/**
 * A simple [Fragment] subclass.
 * Use the [SkillIQFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SkillIQFragment : Fragment() {
    private var sectionNumber: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sectionNumber = it.getInt(ARG_SECTION_NUMBER)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
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