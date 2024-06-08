import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androidart.common.domain.model.Result
import com.androidart.common.domain.useCase.GetStocksUseCase
import com.androidart.stocksscreen.model.asPresentation
import com.androidart.stocksscreen.viewmodel.StocksViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever


@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class StocksViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: StocksViewModel

    @Mock
    private lateinit var getStocksUseCase: GetStocksUseCase

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = StocksViewModel(getStocksUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test loadStocks with success result`() = testScope.runTest {
        // Given
        val stocks = mockStocks()
        val expectedUiState = StocksViewModel.StocksUiState.Success(
            stocks.asPresentation()
        )
        whenever(getStocksUseCase(false)).thenReturn(Result.Success(stocks))

        // When
        viewModel.loadStocks(false)
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.first()
        assert(uiState is StocksViewModel.StocksUiState.Success)
        assertEquals(expectedUiState, uiState)
    }

    @Test
    fun `test loadStocks with failure result`() = testScope.runTest {
        // Given
        val errorMessageId = com.androidart.stocksscreen.R.string.network_error
        whenever(getStocksUseCase(false)).thenReturn(Result.Failure(Exception()))

        // When
        viewModel.loadStocks(false)
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.first()
        assert(uiState is StocksViewModel.StocksUiState.Error)
        assertEquals(errorMessageId, (uiState as StocksViewModel.StocksUiState.Error).messageId)
    }
}
