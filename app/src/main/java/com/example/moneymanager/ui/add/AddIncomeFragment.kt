package com.example.moneymanager.ui.add

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.moneymanager.R
import com.example.moneymanager.data.entity.AddIncomeAndExpense
import com.example.moneymanager.databinding.FragmentAddIncomeBinding
import kotlinx.coroutines.launch

class AddIncomeFragment : Fragment() {
    private var _binding: FragmentAddIncomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddViewModel by activityViewModels()

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
        _binding = FragmentAddIncomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navGraph()
        pickDate()
        pickTime()
        selectImage()
        observe()
        saveTranfer()
        selectWallet()
        selectCategory()
        back()
    }

    fun saveTranfer() {
        binding.tvSave.setOnClickListener {
            val amountText = binding.etAmount.text.toString()
            if (amountText.isNotEmpty()) {
                val amount = amountText.toDouble()
                val description = binding.etDescription.text.toString()
                val time = binding.etTime.text.toString()
                val date = binding.etDate.text.toString()
                val getbitmap = viewModel.getBitmap()
                val imagePath = viewModel.saveDrawableToAppStorage(requireContext(), getbitmap)
                val category = binding.spCategory.selectedItem.toString()
                val typeOfExpenditure = "Income"
                val idWallet = 1L
                val incomeAndExpense = AddIncomeAndExpense(amount, description, category, typeOfExpenditure, idWallet, imagePath ?: "", date, time)
                viewModel.saveIncomeAndExpense(incomeAndExpense)
            } else {
                Log.e("AddExpenseFragment", "Amount is empty")
            }
        }
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

    private fun navGraph() {
        val controller_nav = findNavController()
        binding.tvAddExpense.setOnClickListener {
            controller_nav.navigate(R.id.addExpenseFragment)
        }
        binding.tvTransfer.setOnClickListener {
            controller_nav.navigate(R.id.addTranferFragment)
        }
    }

    fun selectCategory(){
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spCategory.adapter = adapter
        }
    }

    fun selectWallet(){
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.wallet_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spWallet.adapter = adapter
        }
    }

    fun back(){
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}