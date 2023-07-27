package com.example.taskmanager.ui.profile

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.taskmanager.data.local.Pref
import com.example.taskmanager.databinding.FragmentProfileBinding
import com.example.taskmanager.utils.AppTextWatcher
import com.example.taskmanager.utils.loadImage
import com.github.dhaval2404.imagepicker.ImagePicker

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val pref: Pref by lazy {
        Pref(requireContext())
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    pref.setImage(fileUri.toString())
                    pref.getImage()?.let { binding.imgPhotoSettings.loadImage(it) }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etSetName.setText(pref.getName())

        if (pref.getImage()?.length!! > 0) {
            pref.getImage()?.let { binding.imgPhotoSettings.loadImage(it) }
        }

        binding.etSetName.addTextChangedListener(AppTextWatcher {
            pref.setName(binding.etSetName.text.toString())
        })
        binding.imgPhotoSettings.setOnClickListener {
            changePhoto()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun changePhoto() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }
}

