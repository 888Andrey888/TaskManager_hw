package com.example.taskmanager.ui.auth.verify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.data.local.Pref
import com.example.taskmanager.databinding.FragmentVerifyBinding
import com.example.taskmanager.ui.auth.phone.PhoneFragment.Companion.VERIFY_KEY
import com.example.taskmanager.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class VerifyFragment : Fragment() {

    private lateinit var binding: FragmentVerifyBinding
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val pref: Pref by lazy {
        Pref(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val verId = arguments?.getString(VERIFY_KEY)

        binding.btnAccept.setOnClickListener {
            val credential =
                PhoneAuthProvider.getCredential(verId!!, binding.etCode.text.toString())
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (!pref.isOnBoardingShowed()) {
                findNavController().navigate(R.id.onBoardingFragment)
            } else
                findNavController().navigate(R.id.navigation_home)
        }.addOnFailureListener {
            showToast(getString(R.string.error_any), requireActivity())
        }
    }
}