package ge.nlatsabidze.roomexample.fragments

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import ge.nlatsabidze.roomexample.BaseFragment
import ge.nlatsabidze.roomexample.databinding.FragmentHomeBinding
import ge.nlatsabidze.roomexample.recyclerViewAdapter.UserInfoRecyclerAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var userItemAdapter: UserInfoRecyclerAdapter
    private val userViewModel: HomeFragmentViewModel by viewModels()


    override fun start() {

        userItemAdapter = UserInfoRecyclerAdapter()
        userViewModel.setResult()
        initRecycler()
    }

    private fun initRecycler() {

        binding.recyclerId.apply {
            adapter = userItemAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }

        userViewModel.userInformation.observe(viewLifecycleOwner, {
            userItemAdapter.userInformation = it
        })
    }


}