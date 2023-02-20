

import app.cash.turbine.test
import com.example.recipes.data.model.RecipeDTO
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.repository.RecipeRepository
import com.example.recipes.presentation.screens.home.HomeViewModel
import com.example.recipes.rules.CoroutineTestRule
import com.example.recipes.util.recipe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var homeViewModel: HomeViewModel

    @MockK(relaxed = true)
    lateinit var recipeRepository: RecipeRepository

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        homeViewModel = HomeViewModel(recipeRepository, testDispatcher)
    }

    @Test
    fun `searchedRecipes given a string should return a character list`() = runTest {
        //Given
        val data = listOf(recipe)
        coEvery { recipeRepository.searchRecipes("Ensalada con Naranja")  } returns flowOf(data)

        //When
        homeViewModel.searchRecipes("Ensalada con Naranja")

        //Then
        Assert.assertEquals(
            "Dummy",
            homeViewModel.searchedRecipes.value.first().name
        )
        coVerify { recipeRepository.searchRecipes("Ensalada con Naranja") }
    }

    @Test
    fun `searchedRecipes given a string should return a empty list`() = runTest {
        //Given
        coEvery { recipeRepository.searchRecipes("Ensalada con Naranja") } returns flowOf(emptyList<Recipe>())

        //When
        homeViewModel.searchRecipes("Ensalada con Naranja")

        //Then
        homeViewModel.searchedRecipes.test {
            val item = expectMostRecentItem()
            Assert.assertEquals(
                emptyList<RecipeDTO>(),
                item
            )
        }
        coVerify { recipeRepository.searchRecipes("Ensalada con Naranja")}
    }
}