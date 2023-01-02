package com.example.sample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sample.databinding.FragmentFirstBinding
import com.example.sample.i.*

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<Rove2LiveVideoViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return Rove2LiveVideoViewModelImpl(
                    stateLiveData = MutableLiveData<CameraR2Status>(),
                    roveR2UseCase = RoveR2UseCase(),
                    roveR2LiveVideoUseCase = RoveR2LiveVideoUseCase(),
                    appPreference = AppPreference(),
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, ::onRoveR2CameraResponse)
        viewModel.getR2CameraConnectedOrNot()
    }

    private fun onRoveR2CameraResponse(state: CameraR2Status) {
        println(
            "onRoveR2CameraResponse(): " +
                    "state:${state.hashCode()} " +
                    "status:${state.status}"
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}