package ge.nlatsabidze.roomexample.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ge.nlatsabidze.roomexample.ApiData.InformationItem
import ge.nlatsabidze.roomexample.databinding.RecyclerItemBinding

class UserInfoRecyclerAdapter: RecyclerView.Adapter<UserInfoRecyclerAdapter.UserViewHolder>() {

    var userInformation: List<InformationItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class UserViewHolder(private val binding: RecyclerItemBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var currentData: InformationItem

        fun onBind() {
            currentData = userInformation[bindingAdapterPosition]
            Glide.with(binding.root.context)
                    .load(currentData.cover)
                    .apply(RequestOptions().override(150, 210))
                    .into(binding.ivUser)
            binding.tvTitle.text = currentData.title.toString()
            binding.tvPrice.text = currentData.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoRecyclerAdapter.UserViewHolder {
        return UserViewHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount() = userInformation.size
}