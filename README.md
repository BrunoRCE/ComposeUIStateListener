# ComposeUIStateListener

This example use the follow lib:

Required function **collectAsStateWithLifecycle**

```
implementation "androidx.lifecycle:lifecycle-runtime-compose:2.6.0"
```

### UIState
Generic sealed class used to manage the states of the UI, they have three base states that are:

**Loading state:** when the data operation (object) is started

**Success status:** when the data operation was successful (generic that accepts any type of data)

**Error state:** when the operation failed (Throwable type)


```
sealed class UIState<out T> {

    object Loading : UIState<Nothing>()

    data class Success<T>(val data: T) : UIState<T>()

    data class Error(val error: Throwable) : UIState<Nothing>()
}
```

### CollectUIState
Generic StateFlow extension function that emits the base states of the UI, making use of collectAsStateWithLifecycle, this to perform the emission of data according to the life cycle in a safe way

```
@Composable
fun <T> StateFlow<UIState<T>>.CollectUIState(
    onLoading: @Composable () -> Unit,
    onSuccess: @Composable (T) -> Unit,
    onError: @Composable (Throwable) -> Unit,
) {
    val state by collectAsStateWithLifecycle()

    when (state) {
        is UIState.Loading -> {
            onLoading()
        }

        is UIState.Success -> {
            onSuccess((state as UIState.Success<T>).data)
        }

        is UIState.Error -> {
            onError((state as UIState.Error).error)
        }
    }
}
```

### Use
In order to use it, its respective mutable stateflow is declared and initialized with a default value (Loading)

you can use the required type in the diamond operator

```
private val _uiState = MutableStateFlow<UIState<String>>(UIState.Loading)
val uiState = _uiState.asStateFlow()
```

Now you just have to call CollectUIState from the declared variable (uistate)

```
    viewModel.uiState.CollectUIState(
        onLoading = {
            Loader()
        },
        onSuccess = {
            Content(it)
        },
        onError = {
            ShowError(it.message)
        }
    )
```

### OnUIStateListener
You can also use the OnUIStateListener variant for another way to get the UI states, according to your needs

Interface with composable methods and generic type to return success status in any type

```
interface OnUIStateListener<T> {

    @Composable
    fun Loading()

    @Composable
    fun Success(data: T)

    @Composable
    fun Error(error: Throwable)
}
```

Generic StateFlow extension function that emits the base states of the UI, making use of collectAsStateWithLifecycle, this to carry out the emission of data according to the life cycle in a safe way, using the generic interface

```
@Composable
fun <T> StateFlow<UIState<T>>.SetUIStateListener(
    listener: OnUIStateListener<T>
) {

    val state by collectAsStateWithLifecycle()

    when (state) {
        is UIState.Loading -> {
            listener.Loading()
        }

        is UIState.Success -> {
            listener.Success((state as UIState.Success<T>).data)
        }

        is UIState.Error -> {
            listener.Error((state as UIState.Error).error)
        }
    }
}

```
## Use

```
viewModel.uiState.SetUIStateListener(object : OnUIStateListener<List<String>> {
        @Composable
        override fun Loading() {
            Loader()
        }

        @Composable
        override fun Success(data: List<String>) {
            Content(data)
        }

        @Composable
        override fun Error(error: Throwable) {
            ShowError(error.message)
        }
    })
```

## BaseViewModel
As a bonus is the base viewModel with the stateFlow already declared so that it can be accessed from a child viewmodel

```
abstract class BaseViewModel<T> : ViewModel() {
    protected val _uiState = MutableStateFlow<UIState<T>>(UIState.Loading)
    val uiState = _uiState.asStateFlow()
}
```
