package com.example.moneymanager.ui.home.add

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentAddExpenseBinding
import kotlinx.coroutines.launch

class AddExpenseFragment : Fragment() {
    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddViewModel by viewModels()

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        Log.d("nang", "null() called with: bitmap = $bitmap")
        bitmap?.let {
            viewModel.setBitmap(it)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            takePictureLauncher.launch(null)
        } else {
            Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateDateTime()
        pickDate()
        pickTime()
        selectImage()
        observe()
    }

    fun observe(){

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedTime.collect { time ->
                    binding.etTime.setText(time)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedDate.collect { date ->
                    binding.etDate.setText(date)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentDateTime.collect { dateTime ->
                    binding.etDate.setText(dateTime.first)
                    binding.etTime.setText(dateTime.second)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imageUri.collect { bitmap ->
                    binding.ivImage.setImageBitmap(bitmap)
                }
            }
        }
    }

    fun selectImage(){
        binding.ivMemo.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setItems(R.array.select_camera_and_gallery) { _, which ->
                when (which) {
                    0 -> {
                        if (requireActivity().checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            takePictureLauncher.launch(null)
                        } else {
                            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                    }
                    1 -> {
                    }
                }
            }
            builder.show()
        }
    }


    fun pickTime(){
        binding.etTime.setOnClickListener {
            viewModel.showTimePickerDialog(requireContext())
        }

    }

    fun pickDate(){
        binding.etDate.setOnClickListener {
            viewModel.showDatePickerDialog(requireContext())
        }
    }

    private fun saveData(){
        val amount = binding.etAmount.text.toString().toDouble()
        val description = binding.etDescription.text.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}