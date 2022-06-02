package cz.rimu.findlenssamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import cz.rimu.findlenssamples.ui.theme.FindLensSamplesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyAppContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    FindLensSamplesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}

@Composable
fun MyAppContent(names: List<String> = List(50) { "Android $it" }) {

    var inputText by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            SearchBarUI(searchText = inputText,
                placeholderText = "Type name of lens",
                onSearchTextChanged = { inputText = it },
                onClearClick = { inputText = "" },
                onShowMenu = {},
                matchesFound = false,
                results = { NoSearchResults() }
            )
        }
    ) {
        //content
    }
}

@Composable
fun SearchBarUI(
    searchText: String,
    placeholderText: String = "",
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
    onShowMenu: () -> Unit = {},
    matchesFound: Boolean,
    results: @Composable () -> Unit = {}
) {

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SearchBar(
                searchText,
                placeholderText,
                onSearchTextChanged,
                onClearClick,
                onShowMenu
            )

            if (matchesFound) {
                Text("Results", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
                results()
            } else {
                if (searchText.isNotEmpty()) {
                    NoSearchResults()
                }
            }
        }

    }
}

@Composable
fun SearchBar(
    searchText: String,
    placeholderText: String = "",
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
    onShowMenu: () -> Unit = {}
) {
    var showClearButton by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    TopAppBar(title = { Text("") }, navigationIcon = {
        IconButton(onClick = { onShowMenu() }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                modifier = Modifier,
                contentDescription = stringResource(id = R.string.icn_search_back_content_description)
            )
        }
    }, actions = {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .onFocusChanged { focusState ->
                    showClearButton = (focusState.isFocused)
                }
                .focusRequester(focusRequester),
            value = searchText,
            onValueChange = onSearchTextChanged,
            placeholder = {
                Text(text = placeholderText, color = Color.White)
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
            ),
            trailingIcon = {
                if (showClearButton) {
                    IconButton(onClick = { onClearClick() }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(id = R.string.icn_search_back_content_description),
                            tint = Color.White
                        )
                    }
                }
            },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
        )

    })

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
fun NoSearchResults() {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text("No matches found", color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FindLensSamplesTheme {
        MyApp {
            MyAppContent()
        }
    }
}