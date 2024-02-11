import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.natube.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 뷰 바인딩을 사용하여 레이아웃 인플레이트
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        adapter = SearchAdapter(emptyList())
        binding.rvSearchResult.adapter = adapter
        binding.rvSearchResult.layoutManager = LinearLayoutManager(requireContext())

        // ViewModel 설정
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        // LiveData를 관찰하여 RecyclerView 갱신
        viewModel.searchResults.observe(viewLifecycleOwner, Observer { results ->
            adapter.updateData(results)
        })

        // 검색 버튼 클릭 이벤트 설정
        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()
            viewModel.searchVideos(query)
        }
    }
}
