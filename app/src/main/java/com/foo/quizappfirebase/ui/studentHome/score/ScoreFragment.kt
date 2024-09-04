package com.foo.quizappfirebase.ui.studentHome.score

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.databinding.FragmentScoreBinding
import com.foo.quizappfirebase.ui.base.BaseFragment

class ScoreFragment : BaseFragment<FragmentScoreBinding>() {

    override val viewModel: ScoreViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_score
    private val args: ScoreFragmentArgs by navArgs()

    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.run {
            tvScore.text = getString(R.string.score_text, args.scoreId)
            tvTimeTaken.text = getString(R.string.time_taken_text, args.timeTakenId)

            btnBack.setOnClickListener {
                findNavController().navigate(
                    R.id.studentHomeFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.scoreFragment, true)
                        .build()
                )
            }
        }
    }


}


