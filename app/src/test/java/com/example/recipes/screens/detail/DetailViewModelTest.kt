


import com.example.recipes.domain.uses_case.GetSelectedRecipeUC
import com.example.recipes.presentation.screens.detail.DetailViewModel
import com.example.recipes.rules.CoroutineTestRule
import com.example.recipes.util.recipe
import com.example.recipes.util.recipeDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.*

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @RelaxedMockK
    lateinit var getSelectedCharacterUC:GetSelectedRecipeUC


    private lateinit var detailViewModel: DetailViewModel

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        detailViewModel = DetailViewModel(
            getSelectedCharacterUC,
            dispatcher
        )
    }

    @Test
    fun `getRecipe Selected should return a specific recipe`() = runTest {
        //Given
        coEvery { getSelectedCharacterUC(1) } returns recipe

        //When
        detailViewModel.getSelectedRecipe(1)

        //Then
        Assert.assertEquals(
            recipeDTO,
            detailViewModel.recipeSelected.value
        )
        coVerify { getSelectedCharacterUC(1) }
    }

    @Test
    fun `getRecipe Selected should return null`() = runTest {
        //Given
        coEvery { getSelectedCharacterUC(1) } returns null

        //When
        detailViewModel.getSelectedRecipe(1)

        //Then
        Assert.assertEquals(
            null,
            detailViewModel.recipeSelected.value
        )
        coVerify { getSelectedCharacterUC(1) }
    }


}