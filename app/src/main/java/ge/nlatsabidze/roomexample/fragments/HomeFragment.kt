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
import ge.nlatsabidze.roomexample.BaseFragment
import ge.nlatsabidze.roomexample.databinding.FragmentHomeBinding
import ge.nlatsabidze.roomexample.recyclerViewAdapter.UserInfoRecyclerAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var userItemAdapter: UserInfoRecyclerAdapter
    private val userViewModel: HomeFragmentViewModel by viewModels()

    override fun start() {

        userViewModel.setResult()
        initRecycler()
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

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}