

import androidx.paging.PagingSource
import com.example.recipes.data.model.ApiResponse
import com.example.recipes.data.model.InfoDTO
import com.example.recipes.data.model.RecipeDTO
import com.example.recipes.data.repository.RecipePagingSource
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.repository.RemoteDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class CharacterPagingSourceTest {

    private lateinit var recipePagingSource:RecipePagingSource

    private val recipe = mutableListOf<Recipe>().apply {
        add(
            Recipe(
                id = 1,
                image = "",
                name = "1",
                description = ""
            )
        )
        add(
            Recipe(
                id = 2,
                image = "",
                name = "2",
                description = ""
            )
        )
        add(
            Recipe(
                id = 3,
                image = "",
                name = "3",
                description = ""
            )
        )
    }

    private val exceptionIO = IOException("Error")

    private val exceptionHttp = HttpException(
        Response.error<Any>(
            409,
            "raw response body as string".toResponseBody("plain/text".toMediaTypeOrNull())
        )
    )

    private enum class Errors {
        NONE,
        RETURN_EMPTY,
        IO_EXCEPTION,
        HTTP_EXCEPTION
    }

    @Test
    fun `expect multiple recipes, assert LoadResult_Page`() = runBlocking {
        recipePagingSource =RecipePagingSource(
            FakeRemoteDataSourceImpl()
        )

        kotlin.test.assertEquals<PagingSource.LoadResult<Int, Recipe>>(
            expected = PagingSource.LoadResult.Page(
                data = listOf(recipe[0], recipe[1], recipe[2]),
                prevKey = null,
                nextKey = null,
            ),
            actual = recipePagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 5,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `expect empty recipes, assert LoadResult_Page`() = runBlocking {
        recipePagingSource = RecipePagingSource(
            FakeRemoteDataSourceImpl(Errors.RETURN_EMPTY)
        )

        kotlin.test.assertEquals<PagingSource.LoadResult<Int, Recipe>>(
            expected = PagingSource.LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null,
            ),
            actual = recipePagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 5,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `getAllCharacter should catch a IOException, assert LoadResult_Error`() = runBlocking {
        recipePagingSource = RecipePagingSource(
            FakeRemoteDataSourceImpl(Errors.IO_EXCEPTION)
        )

        kotlin.test.assertEquals<PagingSource.LoadResult<Int, Recipe>>(
            expected = PagingSource.LoadResult.Error(
                throwable = exceptionIO
            ),
            actual = recipePagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 5,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `getAllCharacter should catch a HttpException, assert LoadResult_Error`() = runBlocking {
        recipePagingSource =RecipePagingSource(
            FakeRemoteDataSourceImpl(Errors.HTTP_EXCEPTION)
        )

        kotlin.test.assertEquals<PagingSource.LoadResult<Int, Recipe>>(
            expected = PagingSource.LoadResult.Error(
                throwable = exceptionHttp
            ),
            actual = recipePagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 5,
                    placeholdersEnabled = false
                )
            )
        )
    }

    private inner class FakeRemoteDataSourceImpl(val error: Errors = Errors.NONE) :
        RemoteDataSource {



        override suspend fun getAllRecipes(page: Int): ApiResponse<RecipeDTO> {
            return when (error) {
                Errors.NONE -> {
                    return ApiResponse(
                        info = InfoDTO(
                            count = 1,
                            next = null,
                            prev = null,
                            pages = 0
                        ),
                        results = recipe.map { it.toRecipeDTO() }
                    )
                }
                Errors.RETURN_EMPTY -> {
                    return ApiResponse(
                        info = InfoDTO(
                            count = 1,
                            next = null,
                            prev = null,
                            pages = 0
                        ),
                        results = emptyList()
                    )
                }
                Errors.IO_EXCEPTION -> {
                    throw exceptionIO
                }
                Errors.HTTP_EXCEPTION -> {
                    throw exceptionHttp
                }
            }
        }
    }
}