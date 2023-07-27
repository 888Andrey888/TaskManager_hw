package com.example.taskmanager.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskmanager.data.local.Pref
import com.example.taskmanager.databinding.FragmentProfileBinding
import com.example.taskmanager.utils.AppTextWatcher
import com.example.taskmanager.utils.loadImage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val pref: Pref by lazy {
        Pref(requireContext())
    }

    /*private val cropImage = registerForActivityResult(CropImageContract()) {
        if (it.isSuccessful) {
            val uriContent = it.uriContent
            val uriFilePath = it.getUriFilePath(requireContext())
        } else {
            Toast.makeText(requireActivity(), it.error.toString(), Toast.LENGTH_SHORT).show()
        }
    }*/

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
        CropImage.activity().setAspectRatio(1, 1).setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL).start(requireActivity(), this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val resultUri = CropImage.getActivityResult(data).uri
            pref.setImage(resultUri.toString())
            pref.getImage()?.let { binding.imgPhotoSettings.loadImage(it) }
        }
    }
}