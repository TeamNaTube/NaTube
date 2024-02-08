package com.example.natube.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hwangtube.network.RetrofitInstance
import com.example.natube.model.Chip
import com.example.natube.model.UnifiedItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    //Category 리스트
    private var _mCategoryList = MutableLiveData<List<Chip>>(listOf())
    val mCategoryList: LiveData<List<Chip>> get() = _mCategoryList

    //선택한 Category 리스트
    private var _mSelectedCategoryList = MutableLiveData<List<Chip>>(listOf())
    val mSelectedCategoryList: LiveData<List<Chip>> get() = _mSelectedCategoryList

    //HomeFragment 선택된 CategoryId
    private var selectedCategoryId = "-1"

    //Category 에 따른 해당 비디오 리스트
    private var _mItemByCategoryList = MutableLiveData<List<UnifiedItem>>(listOf())
    val mItemByCategoryList: LiveData<List<UnifiedItem>> get() = _mItemByCategoryList

    //Keyword 리스트
    private var _mKeywordList = MutableLiveData<List<Chip>>(listOf())
    val mKeywordList: LiveData<List<Chip>> get() = _mKeywordList

    // Dialog의  KeywordList
    private var _preKeywordList = MutableLiveData<List<Chip>>(listOf())
    val preKeywordList :LiveData<List<Chip>> get() =_preKeywordList

    // keyword 에서 검색할 Query
    private var _keywordQuery = MutableLiveData<String>()
    val keywordQuery: LiveData<String> get() = _keywordQuery

    // Keyword 에 따른 해당 비디오 리스트
    private var _mItemByKeywordList = MutableLiveData<List<UnifiedItem>>(listOf())
    val mItemByKeywordList: LiveData<List<UnifiedItem>> get() = _mItemByKeywordList

    /**
     *  뷰모델 생성시 기본값들 정의
     *  - CategoryList 를 저장
     *  - SharedPreference 에 저장된 값을 초기화(!)
     */
    init {
        initCategoryList()
    }


    private fun initCategoryList() {
        val list = mutableListOf<Chip>()
        Chip.categoryMap.forEach { map ->
            list.add(Chip(categoryId = map.key, name = map.value))
        }

        _mCategoryList.value = list

    }

    /**
     *  SettingChipsDialog 부분 기능 함수
     */


    // 해당 아이템 을 클릭시 mCategoryList 의 isClicked 를 변경
    fun isClickedItem(position: Int) {
        val list = _mCategoryList.value ?: listOf()
        list[position].isClicked = !list[position].isClicked
        _mCategoryList.value = list
    }

    /**
     *  카테 고리 부분
     */

    // mCategoryList 에서 isClicked 가 True 인 경우 를 모아서 mSelectedCategoryList 에 저장
    fun initSelectedCategoryList() {
        var list = mCategoryList.value?.map { it.copy() }?.filter { it.isClicked } ?: listOf()

        for (idx in 1..list.lastIndex) {
            list[idx].isClicked = false
        }

        selectedCategoryId = list[0].categoryId
        _mSelectedCategoryList.value = list
    }

    // HomeFragment 에서 칩(카테고리) 하나만 선택 할수 있게 해주는 함수
    fun setSelectedCategoryPosition(position: Int) {
        val newList = mSelectedCategoryList.value ?: listOf()
        for (idx in newList.indices) {
            newList[idx].isClicked = idx == position
        }
        selectedCategoryId = newList[position].categoryId
        _mSelectedCategoryList.value = newList
    }

    // selectedCategoryId 기반 검색 실행
    fun fetchSearchVideoByCategory() {
        // Id 가 default값(-1)이면 검색 x
        if (selectedCategoryId == "-1") return

        viewModelScope.launch {
            val unifiedItems = searchVideoByCategory()
            _mItemByCategoryList.value = unifiedItems
        }
    }

    private suspend fun searchVideoByCategory() = withContext(Dispatchers.IO) {
        val items =
            RetrofitInstance.api.getTrendingVideos(videoCategoryId = selectedCategoryId).items
        val unifiedItems = mutableListOf<UnifiedItem>()
        items.forEach { item ->
            unifiedItems.add(item.toUnifiedItem())
        }
        unifiedItems
    }

    /**
     *  Keyword 부분
     */
    fun addKeywordChip(query: String) {
        val keywordChip = Chip(categoryId = "-1", name = query)
        val list = _preKeywordList.value?.toMutableList() ?: mutableListOf()
        list.add(keywordChip)
        _preKeywordList.value = list
    }

    fun deleteKeywordChip(chip: Chip) {
        val list = _preKeywordList.value?.toMutableList() ?: mutableListOf()
        list.remove(chip)
        _preKeywordList.value = list
    }

    fun initKeywordList() {
        var list = preKeywordList.value ?: listOf()
        list[0].isClicked = true
        _keywordQuery.value = list[0].name ?:""
        _mKeywordList.value = list
    }

    // HomeFragment 에서 칩(키워드) 하나만 선택 할수 있게 해주는 함수
    fun setKeywordPosition(position: Int) {
        val newList = mKeywordList.value ?: listOf()
        for (idx in newList.indices) {
            newList[idx].isClicked = idx == position
        }
        _keywordQuery.value = newList[position].name ?: ""
        _mKeywordList.value = newList
    }

    fun fetchSearchVideoByKeyword() {
        // keywordQuery 에 값이 들어가있지 않으면 검색 x
        if (keywordQuery.value == null) return

        viewModelScope.launch {
            val unifiedItems = searchVideoByKeyword()
            _mItemByKeywordList.value = unifiedItems
        }
    }

    private suspend fun searchVideoByKeyword() = withContext(Dispatchers.IO) {
        val items =
            RetrofitInstance.api.getSearchingVideos(query = keywordQuery.value.toString()).items
        val unifiedItems = mutableListOf<UnifiedItem>()
        items.forEach { item ->
            unifiedItems.add(item.toUnifiedItem())
        }
        unifiedItems
    }
}