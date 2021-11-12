package ge.nlatsabidze.roomexample.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ge.nlatsabidze.roomexample.App
import ge.nlatsabidze.roomexample.BaseFragment
import ge.nlatsabidze.roomexample.databinding.FragmentHomeBinding
import ge.nlatsabidze.roomexample.recyclerViewAdapter.UserInfoRecyclerAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var userItemAdapter: UserInfoRecyclerAdapter
    private val userViewModel: HomeFragmentViewModel by viewModels()

    override fun start() {

        val result = checkForInternet()
        if (result) {
            userViewModel.setResult()
            initRecycler()
        } else {
            userViewModel.readData.observe(viewLifecycleOwner, {
                userItemAdapter.userInformation = it
            })
        }
    }

    private fun initRecycler() {

        userItemAdapter = UserInfoRecyclerAdapter()

        binding.recyclerId.apply {
            adapter = userItemAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }

        userViewModel.userInformation.observe(viewLifecycleOwner, {
            userItemAdapter.userInformation = it
        })
    }

    private fun checkForInternet(): Boolean {

        val connectivityManager = App.context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }
}