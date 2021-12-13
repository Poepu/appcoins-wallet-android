package com.asfoundation.wallet.onboarding

import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.Nullable
import androidx.core.view.marginTop
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.asf.wallet.R
import com.asf.wallet.databinding.FragmentOnboardingBinding
import com.asfoundation.wallet.base.SingleStateFragment
import com.asfoundation.wallet.my_wallets.create_wallet.CreateWalletDialogFragment
import com.asfoundation.wallet.viewmodel.BasePageViewFragment
import com.rd.draw.data.Orientation
import javax.inject.Inject

class OnboardingFragment : BasePageViewFragment(),
    SingleStateFragment<OnboardingState, OnboardingSideEffect> {

  @Inject
  lateinit var onboardingViewModelFactory: OnboardingViewModelFactory

  @Inject
  lateinit var navigator: OnboardingNavigator

  private val viewModel: OnboardingViewModel by viewModels { onboardingViewModelFactory }
  private val views by viewBinding(FragmentOnboardingBinding::bind)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    handleBackPress()
    handleFragmentResult()
  }

  private fun handleBackPress() {
    requireActivity().onBackPressedDispatcher.addCallback(this,
        object : OnBackPressedCallback(true) {
          override fun handleOnBackPressed() {
            when (viewModel.state.pageNumber) {
              0 -> {
                isEnabled = false
                activity?.onBackPressed()
              }
              1 -> viewModel.handleBackButtonClick()
            }
          }
        })
  }

  private fun handleFragmentResult() {
    parentFragmentManager.setFragmentResultListener(CreateWalletDialogFragment.RESULT_REQUEST_KEY,
        this) { _, _ ->
      navigator.navigateToMainActivity(fromSupportNotification = false)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?,
                            @Nullable savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_onboarding, container, false)
  }

  override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setClickListeners()
    viewModel.collectStateAndEvents(lifecycle, viewLifecycleOwner.lifecycleScope)
  }

  private fun setClickListeners() {
    views.onboardingWelcomeButtons.onboardingNextButton.setOnClickListener { viewModel.handleNextClick() }
    views.onboardingWelcomeButtons.onboardingExistentWalletButton.setOnClickListener { viewModel.handleRecoverClick() }

    views.onboardingValuePropositionButtons.onboardingBackButton.setOnClickListener { viewModel.handleBackButtonClick() }
    views.onboardingValuePropositionButtons.onboardingGetStartedButton.setOnClickListener { navigator.navigateToTermsBottomSheet() }
  }

  override fun onStateChanged(state: OnboardingState) {
    when (state.pageNumber) {
      0 -> showWelcomeScreen()
      1 -> showValuesScreen()
    }
  }

  override fun onSideEffect(sideEffect: OnboardingSideEffect) {
    when (sideEffect) {
      OnboardingSideEffect.NavigateToRecoverWallet -> navigator.navigateToRestoreActivity()
    }
  }

  private fun showWelcomeScreen() {
    views.onboardingWalletIcon.visibility = View.VISIBLE

    views.onboardingWelcomeMessage.onboardingWelcomeMessageLayout.visibility = View.VISIBLE
    views.onboardingWelcomeButtons.onboardingWelcomeButtonsLayout.visibility = View.VISIBLE

    views.onboardingValuePropositions.onboardingValuePropositionsLayout.visibility = View.GONE
    views.onboardingValuePropositionButtons.onboardingValuePropositionsLayout.visibility = View.GONE
  }

  private fun showValuesScreen() {
    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      views.onboardingWalletIcon.visibility = View.GONE
    } else {
      views.onboardingWalletIcon.visibility = View.VISIBLE
    }

    views.onboardingValuePropositions.onboardingValuePropositionsLayout.visibility = View.VISIBLE
    views.onboardingValuePropositionButtons.onboardingValuePropositionsLayout.visibility =
        View.VISIBLE

    views.onboardingWelcomeMessage.onboardingWelcomeMessageLayout.visibility = View.GONE
    views.onboardingWelcomeButtons.onboardingWelcomeButtonsLayout.visibility = View.GONE
  }

  companion object {
    fun newInstance() = OnboardingFragment()
  }
}