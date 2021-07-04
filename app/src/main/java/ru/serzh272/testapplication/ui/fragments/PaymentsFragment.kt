package ru.serzh272.testapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.serzh272.testapplication.R
import ru.serzh272.testapplication.data.managers.AppSettings
import ru.serzh272.testapplication.databinding.FragmentPaymentsBinding
import ru.serzh272.testapplication.extensions.isNetworkAvailable
import ru.serzh272.testapplication.repositories.MainRepository
import ru.serzh272.testapplication.ui.adapters.PaymentsAdapter

class PaymentsFragment : Fragment() {
    private lateinit var binding: FragmentPaymentsBinding
    private val args by navArgs<PaymentsFragmentArgs>()
    private var backPressed: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressed + 2000 > System.currentTimeMillis()) {
                    activity?.finish()
                } else {
                    Toast.makeText(context, R.string.exit_message, Toast.LENGTH_SHORT).show()
                }
                backPressed = System.currentTimeMillis()
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        val paymentsAdapter = PaymentsAdapter()
        binding = FragmentPaymentsBinding.inflate(inflater).apply {
            logoutButton.setOnClickListener {
                returnBack()
            }
            rvPayments.adapter = paymentsAdapter
            rvPayments.layoutManager = LinearLayoutManager(requireContext())
            rvPayments.addItemDecoration(divider)
        }
        if (activity?.isNetworkAvailable() == true) {
            MainRepository.getPayments(
                MainRepository.getAppSettings().value?.token ?: args.token ?: ""
            ) {
                activity?.runOnUiThread {
                    paymentsAdapter.updateData(it)
                }
            }
        }else{
            Toast.makeText(
                requireContext(),
                requireContext().getString(R.string.internet_connection_error),
                Toast.LENGTH_LONG
            )
                .show()
        }
        return binding.root
    }

    fun returnBack() {
        MainRepository.setAppSettings(AppSettings(""))
        val navController = findNavController()
        navController.popBackStack()
    }
}