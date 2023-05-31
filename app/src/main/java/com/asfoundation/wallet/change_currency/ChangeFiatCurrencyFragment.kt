package com.asfoundation.wallet.change_currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.appcoins.wallet.feature.changecurrency.ui.ChangeFiatCurrencyRoute
import com.appcoins.wallet.ui.common.theme.WalletTheme
import com.asfoundation.wallet.home.usecases.DisplayChatUseCase
import com.asfoundation.wallet.viewmodel.BasePageViewFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChangeFiatCurrencyFragment : BasePageViewFragment() {

  @Inject
  lateinit var displayChat: DisplayChatUseCase

  companion object {
    fun newInstance() = ChangeFiatCurrencyFragment()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return ComposeView(requireContext()).apply {
      setContent {
        WalletTheme {
          Surface(modifier = Modifier.fillMaxSize()) {
            ChangeFiatCurrencyRoute(
              onExitClick = { handleBackPress() },
              onChatClick = { displayChat() }
            )
          }
        }
      }
    }
  }

  private fun handleBackPress() {
    parentFragmentManager.popBackStack()
  }
}