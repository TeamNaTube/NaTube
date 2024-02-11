import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.natube.R
import com.example.natube.databinding.VideoItemBinding
import com.example.natube.model.searchmodel.Item
import com.example.natube.model.videomodel.VideoModel

class SearchAdapter(private var dataSet: List<VideoModel>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    // ViewHolder 클래스
    class ViewHolder(private val binding: VideoItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(video: VideoModel) {
            binding.ivVideoItemThumbnail.text =
            binding.tvVideoItemTitle.text =
            binding.tvVideoItemChannelName.text =
            binding.tvVideoItemUploadDate.text =
        }
    }

    // onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // onBindViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    // getItemCount
    override fun getItemCount(): Int = dataSet.size

    // updateData 함수
    fun updateData(newData: List<VideoModel>) {
        dataSet = newData
        notifyDataSetChanged()
    }
}