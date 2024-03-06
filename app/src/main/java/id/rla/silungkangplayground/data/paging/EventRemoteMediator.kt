package id.rla.silungkangplayground.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.rla.silungkangplayground.data.local.EventDatabase
import id.rla.silungkangplayground.data.local.EventEntity
import id.rla.silungkangplayground.data.local.RemoteKeys
import id.rla.silungkangplayground.data.remote.network.ApiService
import id.rla.silungkangplayground.domain.helper.Mapper
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class EventRemoteMediator @Inject constructor(
    private val database: EventDatabase,
    private val apiService: ApiService
):RemoteMediator<Int, EventEntity>() {

    private val eventDao = database.eventDao()
    private val remoteKeysDao = database.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EventEntity>
    ): MediatorResult {
        val page = when(loadType){
            LoadType.REFRESH ->{
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND ->{
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND ->{
                val remoteKeys = getRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val responseData = apiService.getEventPlaygroundInPaging(page, state.config.pageSize)
            val data = responseData.data ?: emptyList()
            val eventEntities = Mapper.mapEventDtoToEventEntity(data)
            val endOfPaginationReached = eventEntities.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH){
                    remoteKeysDao.deleteAllKeys()
                    eventDao.deleteAllEvent()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page+1

                val keys = eventEntities.map {
                    RemoteKeys(it.id, prevKey = prevKey, nextKey = nextKey)
                }
                remoteKeysDao.insertAllKeys(keys)
                eventDao.insertEvent(eventEntities)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e:Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeysForLastItem(
        state:PagingState<Int, EventEntity>
    ):RemoteKeys?{
        return state.pages.lastOrNull{
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { data ->
            remoteKeysDao.getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, EventEntity>
    ):RemoteKeys?{
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()?.let { data ->
                remoteKeysDao.getRemoteKeysId(data.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state:PagingState<Int, EventEntity>):RemoteKeys?{
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeysId(id)
            }
        }
    }

    private companion object{
        const val INITIAL_PAGE_INDEX = 1

    }
}