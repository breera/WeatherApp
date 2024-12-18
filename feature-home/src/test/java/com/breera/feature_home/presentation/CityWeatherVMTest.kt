import com.breera.feature_home.domain.CityRepository
import com.breera.feature_home.domain.model.CityWeatherModel
import com.breera.feature_home.presentation.CityWeatherAction
import com.breera.feature_home.presentation.CityWeatherVM
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class CityWeatherVMTest {

    private lateinit var viewModel: CityWeatherVM
    private lateinit var mockRepository: CityRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk(relaxed = true)
        viewModel = CityWeatherVM(mockRepository)
    }


    @Test
    fun `onAction should handle search query changes`() = runTest {
        viewModel.onAction(CityWeatherAction.OnSearchQueryChange("Lahore"))
        assertEquals("Lahore", viewModel._state.value.searchQuery)
    }

    @Test
    fun `onAction should handle detail weather request`() = runTest {
        val cityWeatherModel = CityWeatherModel(cityName = "Detail City", temperature = "22.0")
        viewModel.onAction(CityWeatherAction.OnDetailWeatherRequest(cityWeatherModel))
        assertEquals(cityWeatherModel, viewModel._state.value.searchResult)
        coVerify { mockRepository.saveLocalCityData(cityWeatherModel) }
    }
}
