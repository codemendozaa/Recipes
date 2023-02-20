
import com.example.recipes.domain.repository.RecipeRepository
import com.example.recipes.domain.uses_case.GetSelectedRecipeUC
import com.example.recipes.util.recipe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(
    ExperimentalCoroutinesApi::class,
    DelicateCoroutinesApi::class
)
class GetSelectedCharacterUCTest {

    private lateinit var getSelectedRecipeUC: GetSelectedRecipeUC

    @MockK
    lateinit var repository: RecipeRepository

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(mainThreadSurrogate)
        getSelectedRecipeUC = GetSelectedRecipeUC(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `given a specific character id should return a character`() = runTest {
        //Given
        coEvery { repository.getSelectedRecipes(1) } returns recipe

        //When
        val result = getSelectedRecipeUC(1)

        //Then
        Assert.assertEquals(
            recipe,
            result
        )

        coVerify(exactly = 1) { repository.getSelectedRecipes(1)}
    }
    @Test
    fun `given a specific recipe id that doesn't exist should return null`() = runTest {
        //Given
        coEvery { repository.getSelectedRecipes(1)} returns null

        //When
        val result =getSelectedRecipeUC(1)

        //Then
        Assert.assertEquals(
            null,
            result
        )

        coVerify(exactly = 1) { repository.getSelectedRecipes(1) }
    }
}