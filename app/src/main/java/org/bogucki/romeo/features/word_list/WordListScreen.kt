package org.bogucki.romeo.features.word_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.bogucki.romeo.R
import org.bogucki.romeo.usecases.parameters.SortParameter

@Composable
fun WordListScreen(modifier: Modifier = Modifier) {

    val viewModel: WordListViewModel = viewModel()
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    var sortingBottomSheetState by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                totalCount = uiState.totalCount,
                onSortActionClicked = {
                    sortingBottomSheetState = true
                }
            )
        },
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {

                items(uiState.wordsWithCount) {
                    Text(text = "${it.word} (${it.count})", Modifier.padding(vertical = 8.dp))
                }
            }
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }
        }

        if (sortingBottomSheetState) {
            SortingBottomSheet(
                onDismissRequest = {
                    sortingBottomSheetState = false
                },
                sortOptions = uiState.sortOptions,
                selectedOption = uiState.orderedBy,
                onItemSelected = viewModel::loadSortedList
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(totalCount: Int, onSortActionClicked: () -> Unit, modifier: Modifier = Modifier) {

    TopAppBar(
        title = {
            Column() {
                Text(stringResource(R.string.main_screen_title))
                Text(
                    stringResource(R.string.main_screen_subtitle_total_count, totalCount),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.alpha(0.5f)
                )
            }
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            IconButton(onClick = onSortActionClicked) {
                Icon(
                    painterResource(id = R.drawable.ic_sort),
                    stringResource(R.string.main_screen_menu_sort_options_content_description)
                )

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortingBottomSheet(
    onDismissRequest: () -> Unit,
    sortOptions: List<SortParameter>,
    selectedOption: SortParameter,
    onItemSelected: (SortParameter) -> Unit
) {

    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        Column(Modifier.padding(16.dp)) {
            Text(stringResource(R.string.main_screen_bottom_sheet_select_sorting_option))
            Spacer(Modifier.height(16.dp))
            sortOptions.forEach {
                Text(
                    text = it.displayName,
                    fontWeight = if (it == selectedOption) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier
                        .clickable {
                            onItemSelected(it)
                            onDismissRequest()
                        }
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}