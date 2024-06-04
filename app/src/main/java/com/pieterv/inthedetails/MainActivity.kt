package com.pieterv.inthedetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pieterv.inthedetails.ui.theme.InTheDetailsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InTheDetailsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    ListDetailLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailLayout(modifier: Modifier = Modifier) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val contentData = ContentData()
    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(25) {
                    val displayIndex = it + 1
                    Text(
                        LocalContext.current.getString(
                            R.string.item_template,
                            displayIndex.toString()
                        ),
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .clickable {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail,
                                    content = contentData.copy(index = displayIndex)
                                )
                            }
                            .padding(16.dp)
                    )
                }
            }
        },
        detailPane = {
            val data = navigator.currentDestination?.content as? ContentData
            AnimatedPane {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (data?.index == null) {
                        Text(LocalContext.current.getString(R.string.select_option))
                        return@AnimatedPane
                    }

                    when (data.animalType) {
                        AnimalType.Cat -> {
                            Animation(anim = R.raw.anim_cat)
                        }

                        AnimalType.Dog -> {
                            Animation(anim = R.raw.anim_dog)
                        }

                        null -> {
                            Text(
                                text =
                                LocalContext.current.getString(
                                    R.string.item_template,
                                    "${data.index}"
                                )
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                AssistChip(
                                    onClick = {
                                        navigator.navigateTo(
                                            pane = ListDetailPaneScaffoldRole.Extra,
                                            content = data.copy(animalType = AnimalType.Cat)
                                        )
                                    },
                                    label = {
                                        Text(text = LocalContext.current.getString(R.string.random_cat))
                                    }
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = LocalContext.current.getString(R.string.or))
                                Spacer(modifier = Modifier.width(16.dp))
                                AssistChip(
                                    onClick = {
                                        navigator.navigateTo(
                                            pane = ListDetailPaneScaffoldRole.Extra,
                                            content = data.copy(animalType = AnimalType.Dog)
                                        )
                                    },
                                    label = {
                                        Text(text = LocalContext.current.getString(R.string.random_dog))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        },
        extraPane = {
            val data = navigator.currentDestination?.content as? ContentData
            AnimatedPane {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.tertiaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    val displayText = when (data?.animalType) {
                        AnimalType.Cat -> {
                            LocalContext.current.getString(R.string.cat_text)
                        }

                        AnimalType.Dog -> {
                            LocalContext.current.getString(R.string.dog_text)
                        }

                        null -> ""
                    }
                    Text(text = displayText)
                }
            }
        }
    )
}