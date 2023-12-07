package uk.ac.tees.w9623063.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import uk.ac.tees.w9623063.myapplication.R
import uk.ac.tees.w9623063.myapplication.domain.network.model.Result
import uk.ac.tees.w9623063.myapplication.utils.Screen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.auth.FirebaseAuth
import uk.ac.tees.w9623063.myapplication.ui.LoginRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    list: State<List<Result>?>,
    modifier: Modifier = Modifier,
    isLoading: State<Boolean>,
    onRefresh: () -> Unit
) {
    val swiperRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading.value)
    val scaffoldState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(scaffoldState) },
        topBar = {
            TopAppBar(
                navigationIcon = {},
                actions = {
                    IconButton(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        navController.popBackStack()
                        navController.navigate(LoginRoutes.SignIn.name)
                    }) {
                        Icon(tint = Color.Black,
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.baseline_logout_24),
                            contentDescription = "logout"
                        )
                    }
                },
                title = { Text(text = "Home") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text(text = "Welcome to football scores",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                modifier = Modifier.padding(4.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))


        }

    }


    SwipeRefresh(
        state = swiperRefreshState,
        onRefresh = onRefresh
    ) {
        LazyColumn {
            itemsIndexed(
                list.value!!
            ) { _, item ->
                ColumnItem(
                    item = item,
                    navController = navController
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ColumnItem(
    item: Result,
    modifier: Modifier = Modifier,
    navController: NavController
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

@Composable
fun WebViewAction(
    navController: NavController
) {
    val context = LocalContext.current
    IconButton(onClick = {
        navController.navigate("${Screen.WebScreen.route}/123")
    }) {
        Icon(
            imageVector = Icons.Filled.Send,
            tint = Color.White,
            contentDescription = "web_view"
        )
    }
}
