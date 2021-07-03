package ru.serzh272.testapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.serzh272.testapplication.R
import ru.serzh272.testapplication.data.remote.req.UserLoginRequest
import ru.serzh272.testapplication.databinding.FragmentAuthorizationBinding
import ru.serzh272.testapplication.repositories.MainRepository

class AuthorizationFragment : Fragment() {
    private lateinit var binding:FragmentAuthorizationBinding
    private val repository = MainRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizationBinding.inflate(inflater)
        prepareViews()
        return binding.root
    }

    private fun prepareViews(){
        binding.loginButton.setOnClickListener {
            repository.getToken(UserLoginRequest(binding.teLogin.text.toString(), binding.tePassword.text.toString())){ it ->
                if (it !=null){
                    val navController = findNavController()
                    val act = AuthorizationFragmentDirections.actionAutorizationFragmentToPaymentsFragment(it)
                    navController.navigate(act)
                }else{
                    Snackbar.make(requireContext(), binding.root, requireContext().getString(R.string.authorization_error),Snackbar.LENGTH_SHORT).show()
                }
            }

        }
    }
}