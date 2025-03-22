package com.example.moodplusplus2.ui.external

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moodplusplus2.MoodTopAppBar
import com.example.moodplusplus2.R
import com.example.moodplusplus2.data.ExternalRes
import com.example.moodplusplus2.ui.AppViewModelProvider
import com.example.moodplusplus2.ui.navigation.BottomNavigationDestination

object ExternalResDestination: BottomNavigationDestination {
    override val route = "ExternalRes"
    override val titleRes = R.string.external_resources
    override val iconRes = R.drawable.bottom_bookmark
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExternalResScreen(
    botNavBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExternalResViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val externalResUiState = viewModel.uiState
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MoodTopAppBar(
                title = stringResource(ExternalResDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            botNavBar()
        }
    ) {
        innerPadding ->
        ExternalResBody(
            externalResList = externalResUiState.externalResList,
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        )
    }
}

@Composable
private fun ExternalResBody(
    externalResList: List<ExternalRes>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val clipboard = LocalClipboardManager.current

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(externalResList, key = { externalRes -> externalRes.id }) { externalRes ->
            ExternalResCard(
                externalRes = externalRes,
                clipboard = clipboard,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun ExternalResCard(
    externalRes: ExternalRes,
    clipboard: ClipboardManager,
    modifier: Modifier
) {
    val access = (stringResource(externalRes.accessMethod))
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(externalRes.title),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(externalRes.description),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "Access Method",
                style = MaterialTheme.typography.titleMedium
            )
            Button(
                onClick = {
                    clipboard.setText(
                        AnnotatedString(
                            text = access.substringAfterLast("- ")
                        )
                    )
                }
            ) {
                Text(
                    text = access,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }
}