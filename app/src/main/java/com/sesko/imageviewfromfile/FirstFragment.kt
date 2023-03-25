package com.sesko.imageviewfromfile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sesko.imageviewfromfile.databinding.FragmentFirstBinding
import java.io.File
import com.sesko.imageviewfromfile.MainActivity.Companion.localImageFile

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/

        setupImageView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupImageView() {
        binding.btnUpdateImage.setOnClickListener {
            if (localImageFile != null) {
                println(">>> $localImageFile")
                val bitmap = BitmapFactory.decodeFile(localImageFile!!.path)
                binding.imageView.setImageBitmap(bitmap)
            }
        }
    }
}