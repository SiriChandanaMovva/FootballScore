@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package uk.ac.tees.w9623063.myapplication.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import uk.ac.tees.w9623063.myapplication.models.Scores
import uk.ac.tees.w9623063.myapplication.utils.NoteColors
import com.google.firebase.Timestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uk.ac.tees.w9623063.myapplication.R
import uk.ac.tees.w9623063.myapplication.Singletons
import uk.ac.tees.w9623063.myapplication.domain.network.model.Result
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    homeViewModel: HomeViewModel?,

    onNoteClick:(id:String) -> Unit,
    navToDetailPage:() -> Unit,
    navToLoginPage:() -> Unit
){
    var list = homeViewModel?.resultList?.observeAsState()
    val scaffoldState = remember{SnackbarHostState()}

    LaunchedEffect(key1 = Unit){
        homeViewModel?.getAllCache()

    }
    homeViewModel?.getAllCache()
    val resultList: MutableLiveData<List<Result>> = MutableLiveData()
        list = resultList.observeAsState()
        print("The data from network" + list)
        Scaffold(
            snackbarHost = { SnackbarHost(scaffoldState) },
            floatingActionButton = {
                FloatingActionButton(onClick = { navToDetailPage.invoke() }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            },
            topBar = {
                TopAppBar(
                    navigationIcon = {},
                    actions = {
                        IconButton(onClick = {
                            homeViewModel?.signOut()
                            navToLoginPage.invoke()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = null,
                            )
                        }
                    },
                    title = { Text(text = "Home") })
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding)) {
                Text(
                    text = "Welcome to football scores",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier.padding(4.dp)
                )
                Spacer(modifier = Modifier.size(4.dp))
                /*
            LazyColumn {
                itemsIndexed(
                    list?.value!!
                ) { _, item ->
                    ColumnItem(
                        item = item
                    )
                }
            }
            */

                /*when(homeUiState.scoresList){
                is Resources.Loading ->{
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)
                    )
                }
                is Resources.Success -> {
                    LazyVerticalGrid(columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp)
                    ){
                        items(
                            homeUiState.scoresList.data ?: emptyList()
                        ){ note ->
                            NoteItem(scores = note,
                                onLongClick = {
                                    openDialog = true
                                    selectedNote = note
                                },
                                ) {
                                onNoteClick.invoke(note.documentId)
                            }
                        }
                    }
                }
                else -> {
                    Text(text = homeUiState.scoresList.throwable?.localizedMessage ?: "Unknown Error",
                        color = Color.Red
                    )
                }
            }*/
            }

        }

    LaunchedEffect(key1 = homeViewModel?.hasUser){
        if(homeViewModel?.hasUser == false){
            navToLoginPage.invoke()
        }
    }


}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ColumnItem(
    item: Result,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .padding(3.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row {
                GlideImage(
                    model = item.home_team_logo,
                    modifier = modifier
                        .size(60.dp, 60.dp)
                        .padding(vertical = 2.dp, horizontal = 5.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = stringResource(R.string.home_team_logo)

                ) {
                    it
                        .error(item.country_logo)
                        .load(item.home_team_logo)
                }
                Divider(
                    modifier = modifier
                        .fillMaxHeight()
                        .width(1.dp),
                    color = Color.LightGray
                )
            }

            Column(
                modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.league_name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = item.event_final_result,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = item.event_time,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Row {
                Divider(
                    modifier = modifier
                        .fillMaxHeight()
                        .width(1.dp),
                    color = Color.LightGray
                )
                GlideImage(
                    model = item.away_team_logo,
                    modifier = modifier
                        .size(60.dp, 60.dp)
                        .padding(vertical = 2.dp, horizontal = 5.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = stringResource(R.string.away_team_logo)
                ) {
                    it
                        .error(item.country_logo)
                        .load(item.away_team_logo)
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    scores:Scores,
    onLongClick:() -> Unit,
    onClick:() -> Unit
){
    Card(
        modifier = Modifier
            .combinedClickable(
                onLongClick = { onLongClick.invoke() },
                onClick = { onClick.invoke() },
            )
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(NoteColors.colors[scores.colorIndex])

    ) {
        MaterialTheme{
            Column {
                Text(text = scores.title,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier.padding(4.dp)
                )
                Spacer(modifier = Modifier.size(4.dp))
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f))
                {
                    Text(
                        text = scores.description,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(4.dp),
                        maxLines = 4,
                    )
                }
                Spacer(modifier = Modifier.size(4.dp))

                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f))
                {
                    Text(
                        text = formatDate(scores.timestamp),
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(4.dp)
                            .align(Alignment.End),
                        maxLines = 4,
                    )
                }
            }
        }

    }
}

private fun formatDate(timestamp: Timestamp):String{
    val sdf = SimpleDateFormat("MM-dd-yy hh:mm", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}
