package ru.serzh272.testapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.serzh272.testapplication.R
import ru.serzh272.testapplication.data.managers.AppSettings
import ru.serzh272.testapplication.data.remote.req.UserLoginRequest
import ru.serzh272.testapplication.databinding.FragmentAuthorizationBinding
import ru.serzh272.testapplication.extensions.isNetworkAvailable
import ru.serzh272.testapplication.repositories.MainRepository

class AuthorizationFragment : Fragment() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    lateinit var binding: FragmentAuthorizationBinding
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val repository = MainRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizationBinding.inflate(inflater)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        MainRepository.getAppSettings().observe(viewLifecycleOwner, {
            if (it.token != "") {
                val navController = findNavController()
                val act = AuthorizationFragmentDirections.actionAuthorizationFragmentToPaymentsFragment(it.token)
                navController.navigate(act)
            } else {
                binding.tePassword.text?.clear()
            }
        })
        prepareViews()
        return binding.root
    }

    /**
     * Prepare views for interaction
     */
    private fun prepareViews() {
        binding.loginButton.setOnClickListener {
            if (activity?.isNetworkAvailable() == true){
                if (binding.teLogin.text.isNullOrEmpty()){
                    binding.tiLtLoginText.error = requireContext().getString(R.string.empty_login_error)
                    return@setOnClickListener
                }else{
                    binding.tiLtLoginText.error = null
                }
                if (binding.tePassword.text.isNullOrEmpty()){
                    binding.tiLtPasswordText.error = requireContext().getString(R.string.empty_password_error)
                    return@setOnClickListener
                }else{
                    binding.tiLtPasswordText.error = null
                }
                repository.getToken(
                    UserLoginRequest(
                        binding.teLogin.text.toString(),
                        binding.tePassword.text.toString()
                    )
                ) { token ->
                    if (token.isNullOrBlank()) {
                        Snackbar.make(
                            requireContext(),
                            binding.root,
                            requireContext().getString(R.string.authorization_error),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else
                        MainRepository.setAppSettings(AppSettings(token))
                }
            }else{
                Snackbar.make(
                    requireContext(),
                    binding.root,
                    requireContext().getString(R.string.internet_connection_error),
                    Snackbar.LENGTH_SHORT
                )
                    .setAction("Exit") {view ->
                        activity?.finish()
                    }
                    .show()
            }

        }

    }
}