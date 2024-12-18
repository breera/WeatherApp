import com.breera.core.data.local.DataStorePref
import com.breera.feature_home.data.local.DataStoreLocalCityDataSource
import com.breera.feature_home.domain.model.CityWeatherModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DataStoreLocalCityDataSourceTest {

    private lateinit var dataSource: DataStoreLocalCityDataSource
    private lateinit var mockDataStore: DataStorePref

    @BeforeEach
    fun setUp() {
        mockDataStore = mockk(relaxed = true)
        dataSource = DataStoreLocalCityDataSource(mockDataStore, Dispatchers.Unconfined)
    }

    @Test
    fun `saveCity should save city data to data store`() = runTest {
        // Arrange
        val cityWeatherModel = CityWeatherModel(cityName = "Test City", temperature = "25.0")

        // Act
        dataSource.saveCity(cityWeatherModel)

        // Assert
        coVerify { mockDataStore.putObject("key_city_data", cityWeatherModel, CityWeatherModel::class) }
    }

    @Test
    fun `getCity should return city data from data store`() = runTest {
        // Arrange
        val expectedCityWeatherModel = CityWeatherModel(cityName = "Test City", temperature = "25.0")
        coEvery { mockDataStore.getObject("key_city_data", CityWeatherModel::class) } returns expectedCityWeatherModel

        // Act
        val result = dataSource.getCity()

        // Assert
        assertEquals(expectedCityWeatherModel, result)
    }
}