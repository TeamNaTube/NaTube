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

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

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

    //다음 페이지 정보가 들어간 위한 토큰
    private var nextPageTokenByCategory: String = ""
    var lastPositionCategory = 0

    // Dialog의  KeywordList
    private var _preKeywordList = MutableLiveData<List<Chip>>(listOf())
    val preKeywordList: LiveData<List<Chip>> get() = _preKeywordList

    //Keyword 리스트
    private var _mKeywordList = MutableLiveData<List<Chip>>(listOf())
    val mKeywordList: LiveData<List<Chip>> get() = _mKeywordList


    // keyword 에서 검색할 Query
    private var _keywordQuery = MutableLiveData<String>()
    val keywordQuery: LiveData<String> get() = _keywordQuery

    // Keyword 에 따른 해당 비디오 리스트
    private var _mItemByKeywordList = MutableLiveData<List<UnifiedItem>>(listOf())
    val mItemByKeywordList: LiveData<List<UnifiedItem>> get() = _mItemByKeywordList

    //다음 페이지 정보가 들어간 위한 토큰
    private var nextPageTokenByKeyword: String = ""
    var lastPositionKeyword = 0

    // 유효성 검사
    private var _isValidated = MutableLiveData<Boolean>(false)
    val isValidated: LiveData<Boolean> get() = _isValidated


    // 카테 고리,키워드 리스트 백업
    private var categoryListBackup: List<Chip> = listOf()
    private var keywordListBackup: List<Chip> = listOf()

    //Pref 에 저장된 값이 있는지 확인
    private var _isPrefEmpty = MutableLiveData<Boolean>(false)
    val isPrefEmpty: LiveData<Boolean> get() = _isPrefEmpty

    /**
     *  뷰모델 생성시 기본값들 정의
     *  - CategoryList 를 저장
     *  - SharedPreference 에 저장된 값으로 초기화
     */
    init {
//        homeRepository.clearList()
        isEmptyChipsList()
        initCategoryList()
        initKeywordList()
    }

    private fun isEmptyChipsList() {
        _isPrefEmpty.value = homeRepository.isEmptyList()
    }


    private fun initCategoryList() {
        val list = mutableListOf<Chip>()
        Chip.categoryMap.forEach { map ->
            list.add(Chip(categoryId = map.key, name = map.value))
        }

        // 초기가 아닐때 list가 선택되어 있어야함
        if (!homeRepository.isEmptyList()) {
            val prefList = homeRepository.getCategoryList()
            prefList.forEach { savedChip ->
                list.find { it.categoryId == savedChip.categoryId }?.isClicked = true
            }
        }

        _mCategoryList.value = list

        if (!homeRepository.isEmptyList()) {
            saveSelectedCategoryList()
        }
    }

    private fun initKeywordList() {
        if (homeRepository.isEmptyList()) return
        val list = homeRepository.getKeywordList()
        _preKeywordList.value = list
        saveKeywordList()

    }

    /**
     *  SettingChipsDialog 부분 기능 함수
     */
    fun backupChipList() {
        categoryListBackup = mCategoryList.value?.map { it.copy() } ?: listOf()
        keywordListBackup = preKeywordList.value ?: listOf()
    }

    fun rollBackChipList() {
        _mCategoryList.value = categoryListBackup
        _preKeywordList.value = keywordListBackup
    }

    // 해당 아이템 을 클릭시 mCategoryList 의 isClicked 를 변경
    fun isClickedItem(position: Int) {
        val list = _mCategoryList.value ?: listOf()
        list[position].isClicked = !list[position].isClicked
        _mCategoryList.value = list
        checkedValidate()

    }

    // 유효성 검사
    fun checkedValidate() {
        val isCategoryValidated =
            (1..5).contains(mCategoryList.value?.filter { it.isClicked }?.size)
        val isKeywordValidated = (1..5).contains(preKeywordList.value?.size)
        _isValidated.value = isCategoryValidated && isKeywordValidated
    }

    // 키워드 유효성 검사
    fun checkedQueryValidate(query: String): Int {
        //1.빈 문자열 이면 안됨
        if (query.trim().isEmpty()) return 1
        //2.중복된 문자열 이면 안됨
        preKeywordList.value?.forEach { chip ->
            if (chip.name == query) return 2
        }

        return 0
    }

    /**
     *  카테 고리 부분
     */

    // mCategoryList 에서 isClicked 가 True 인 경우 를 모아서 mSelectedCategoryList 에 저장
    fun saveSelectedCategoryList() {
        var list = mCategoryList.value?.map { it.copy() }?.filter { it.isClicked } ?: listOf()

        for (idx in 1..list.lastIndex) {
            list[idx].isClicked = false
        }

        selectedCategoryId = list[0].categoryId
        _mSelectedCategoryList.value = list
        homeRepository.updatePrefCategory(list)
    }

    // HomeFragment 에서 칩(카테고리) 하나만 선택 할수 있게 해주는 함수
    fun setSelectedCategoryPosition(position: Int) {
        val newList = mSelectedCategoryList.value ?: listOf()
        for (idx in newList.indices) {
            newList[idx].isClicked = idx == position
        }
        selectedCategoryId = newList[position].categoryId


        //선택 되어 지면 검색 실행(옵저버 에 연결)
        lastPositionCategory = 0
        if (nextPageTokenByCategory.isNotBlank()) nextPageTokenByCategory = ""
        _mItemByCategoryList.value = emptyList()
        _mSelectedCategoryList.value = newList


    }

    // selectedCategoryId 기반 검색 실행
    fun fetchSearchVideoByCategory() {
        // Id 가 default값(-1)이면 검색 x
        if (selectedCategoryId == "-1") return

        viewModelScope.launch {
            val unifiedItems = searchVideoByCategory()
            var list = mItemByCategoryList.value?.toMutableList() ?: emptyList()
            list = list + unifiedItems.toMutableList()
            _mItemByCategoryList.value = list
        }
    }

    private suspend fun searchVideoByCategory() = withContext(Dispatchers.IO) {
        val response =
            RetrofitInstance.api.getTrendingVideos(
                videoCategoryId = selectedCategoryId,
                nextPageToken = nextPageTokenByCategory
            )
        val items = response.items
        nextPageTokenByCategory = response.nextPageToken
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
        checkedValidate()
    }

    fun deleteKeywordChip(chip: Chip) {
        val list = _preKeywordList.value?.toMutableList() ?: mutableListOf()
        list.remove(chip)
        _preKeywordList.value = list
        checkedValidate()

    }

    fun saveKeywordList() {
        var list = preKeywordList.value ?: listOf()
        list[0].isClicked = true
        for (idx in 1..list.lastIndex) {
            list[idx].isClicked = false
        }
        _keywordQuery.value = list[0].name ?: ""
        _mKeywordList.value = list
        homeRepository.updatePrefKeyword(list)
        _isPrefEmpty.value = false
    }

    // HomeFragment 에서 칩(키워드) 하나만 선택 할수 있게 해주는 함수
    fun setKeywo정rdPosition(position: Int) {
        val newList = mKeywordList.value ?: listOf()
        for (idx in newList.indices) {
            newList[idx].isClicked = idx == position
        }

        lastPositionKeyword = 0

        _keywordQuery.value = newList[position].name ?: ""
        _mItemByKeywordList.value = emptyList()
        _mKeywordList.value = newList
    }

    fun fetchSearchVideoByKeyword() {
        // keywordQuery 에 값이 들어가있지 않으면 검색 x
        if (keywordQuery.value == null) return

        viewModelScope.launch {
            val unifiedItems = searchVideoByKeyword()
            var list = mItemByKeywordList.value?.toMutableList() ?: emptyList()
            list = list + unifiedItems.toMutableList()
            _mItemByKeywordList.value = list
        }
    }

    private suspend fun searchVideoByKeyword() = withContext(Dispatchers.IO) {
//        val response =
//            RetrofitInstance.api.getSearchingVideos(
//                query = keywordQuery.value.toString(),
//                nextPageToken = nextPageTokenByKeyword
//            )
        val response =
            RetrofitInstance.api.getTrendingVideos(
                videoCategoryId = selectedCategoryId,
                nextPageToken = nextPageTokenByCategory
            )
        val items = response.items
        nextPageTokenByKeyword = response.nextPageToken
        val unifiedItems = mutableListOf<UnifiedItem>()
        items.forEach { item ->
            unifiedItems.add(item.toUnifiedItem())
        }
        unifiedItems
    }

}