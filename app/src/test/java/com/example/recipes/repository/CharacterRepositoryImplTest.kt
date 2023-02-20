

import androidx.paging.ExperimentalPagingApi
import app.cash.turbine.test
import com.example.recipes.data.local.RecipeDatabase
import com.example.recipes.data.repository.RecipeRepositoryImpl
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.repository.LocalDataSource
import com.example.recipes.domain.repository.RecipeRepository
import com.example.recipes.domain.repository.RemoteDataSource
import com.example.recipes.rules.CoroutineTestRule
import com.example.recipes.util.recipe
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class CharacterRepositoryImplTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var repository: RecipeRepository

    @RelaxedMockK
    lateinit var remoteDataSource: RemoteDataSource

    @RelaxedMockK
    lateinit var recipeDatabase:RecipeDatabase

    @RelaxedMockK
    lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = RecipeRepositoryImpl(
            remoteDataSource,
            recipeDatabase,
            localDataSource
        )
    }

    @Test
    fun `getSelectedRecipe should return a specific recipe by id`() = runTest {
        //Given
        coEvery { localDataSource.getSelectedRecipe(1)} returns recipe

        //When
        val result = repository.getSelectedRecipes(1)

        //Then
        Assert.assertEquals(
            recipe,
            result
        )
        coVerify(exactly = 1) { repository.getSelectedRecipes(1) }
    }

    @Test
    fun `getSelectedRecipe should return a null recipe with undefined id`() = runTest {
        //Given
        val slot = slot<Int>()
        coEvery { localDataSource.getSelectedRecipe(capture(slot)) } returns null

        //When
        val result = repository.getSelectedRecipes(-1)

        //Then
        Assert.assertEquals(
            null,
            result
        )
        Assert.assertEquals(
            -1,
            slot.captured
        )

        coVerify(exactly = 1) { repository.getSelectedRecipes(-1) }
    }

    @Test
    fun `searchRecipes given a string should return a list of recipe`() = runTest {
        //Given
        val slot = slot<String>()
        every { localDataSource.searchRecipes(capture(slot)) } returns flow {
            emit(
                listOf(
                    recipe
                )
            )
        }

        //When
        val result = repository.searchRecipes("test")

        result.test {
            val data = awaitItem()
            Assert.assertEquals(
                listOf(recipe),
                data
            )
            awaitComplete()
        }

        Assert.assertEquals(
            "%test%",
            slot.captured
        )
        verify { localDataSource.searchRecipes("%test%") }
    }

    @Test
    fun `searchRecipes given a undefined string should return a empty list of recipe`() =
        runTest {
            //Given
            val slot = slot<String>()
            every { localDataSource.searchRecipes(capture(slot)) } returns flow {
                emit(
                    emptyList()
                )
            }

            //When
            val result = repository.searchRecipes("test")

            //Then
            result.test {
                val data = awaitItem()
                Assert.assertEquals(
                    emptyList<Recipe>(),
                    data
                )
                awaitComplete()
            }

            Assert.assertEquals(
                "%test%",
                slot.captured
            )
            verify { localDataSource.searchRecipes("%test%") }
        }
}