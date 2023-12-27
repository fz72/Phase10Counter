package com.tjEnterprises.phase10Counter.ui.addGame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tjEnterprises.phase10Counter.data.local.database.Game
import com.tjEnterprises.phase10Counter.ui.component.DefaultScaffold
import com.tjEnterprises.phase10Counter.ui.navigation.NavigationDestination

@Composable
fun AddGameScreen(
    modifier: Modifier = Modifier,
    navigateToGame: (String) -> Unit,
    openDrawer: () -> Unit,
    viewModel: AddGameViewModel = hiltViewModel()
) {
    val newCreatedGameID by viewModel.newCreatedGameID.collectAsState()

    AddGameScreen(openDrawer = openDrawer,
        addGame = { gameName, names ->
            viewModel.addGame(gameName, names)
        },
        newCreatedGameID = newCreatedGameID,
        tempPlayerNames = viewModel.tempPlayerNames,
        removeTempPlayerName = { viewModel.removeTempPlayerName(it) },
        navigateToGame = navigateToGame,
        resetNewCreatedGameID = { viewModel.resetNewCreatedGameID() },
        modifier = modifier
    )

}

@Composable
internal fun AddGameScreen(
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit,
    navigateToGame: (String) -> Unit,
    addGame: (String, List<String>) -> Unit,
    resetNewCreatedGameID: () -> Unit,
    newCreatedGameID: Long,
    tempPlayerNames: SnapshotStateList<String>,
    removeTempPlayerName: (Int) -> Unit
) {
    var textPlayer by remember { mutableStateOf("") }
    var textGame by remember { mutableStateOf("") }

    // when the gameID is not -1 (default) the side effect will cause a navigation to the newly created game
    // there are no other circumstances, where newCreatedGameID will change its value from -1L
    if (newCreatedGameID != -1L) {
        LaunchedEffect(key1 = newCreatedGameID, block = {
            resetNewCreatedGameID()
            tempPlayerNames.clear()
            navigateToGame(NavigationDestination.GAMESCREEN + "/" + newCreatedGameID)
        })
    }

    DefaultScaffold(title = "Add new Game", openDrawer = openDrawer) { scaffoldModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = scaffoldModifier
                .then(modifier)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            BoxWithConstraints {
                // Put TextFields and button in a row
                if (maxWidth > 400.dp) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextField(value = textGame,
                            onValueChange = { textGame = it },
                            label = { Text("Game Name") },
                            maxLines = 1,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                            ),
                            modifier = Modifier
                                .widthIn(1.dp, 150.dp)
                                .padding(horizontal = 8.dp)

                        )
                        TextField(value = textPlayer,
                            onValueChange = { textPlayer = it },
                            label = { Text("Player Name") },
                            maxLines = 1,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            modifier = Modifier.widthIn(1.dp, 150.dp)
                        )
                        Button(modifier = Modifier.padding(horizontal = 8.dp), onClick = {
                            tempPlayerNames.add(0, textPlayer)
                            textPlayer = ""
                        }) {
                            Text(text = "Add Player")
                        }
                    }
                }
                // Stack TextFields and button above each other
                else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(value = textGame,
                            onValueChange = { textGame = it },
                            label = { Text("Game Name") },
                            maxLines = 1,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                            ),
                            modifier = Modifier
                                .widthIn(1.dp, 150.dp)
                                .padding(bottom = 8.dp)

                        )
                        TextField(value = textPlayer,
                            onValueChange = { textPlayer = it },
                            label = { Text("Player Name") },
                            maxLines = 1,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            modifier = Modifier
                                .widthIn(1.dp, 150.dp)
                                .padding(bottom = 4.dp)
                        )
                        Button(onClick = {
                            if (textPlayer != "") {
                                tempPlayerNames.add(0, textPlayer)
                                textPlayer = ""
                            }
                        }) {
                            Text(text = "Add Player")
                        }
                    }
                }
            }

            Button(onClick = {
                addGame(textGame, tempPlayerNames)
            }, modifier = Modifier.align(Alignment.End)) {
                Text(text = "Start")
            }
            PlayersList(
                modifier = modifier,
                tempPlayerNames = tempPlayerNames,
                removeTempPlayerName = removeTempPlayerName
            )

        }
    }
}

@Composable
internal fun PlayersList(
    modifier: Modifier,
    tempPlayerNames: SnapshotStateList<String>,
    removeTempPlayerName: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(top = 8.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(tempPlayerNames) { index, item ->
            BoxWithConstraints {
                val maxWidth = maxWidth
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item, modifier = Modifier
                            .widthIn(max = maxWidth.minus(64.dp))
                            .wrapContentWidth()
                            .padding(start = 16.dp)
                    )
                    IconButton(
                        onClick = { removeTempPlayerName(index) },
                        modifier = Modifier.padding(end = 16.dp, start = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete, contentDescription = "Delete Player"
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 400)
@Preview(showBackground = true, widthDp = 500)
@Composable
fun AddGameScreenPreview() {
    val tempPlayerNames = remember {
        mutableStateListOf<String>(
            "Player 1",
            "Player 2",
            "Player 3",
            "Plaaaaaaaaaaaaaaaaayyyyyyyyyyyyyyyyeeeeeeeeeeeeeeeerrrrrrrrrrrrrrrrr",
            "Plaaaaaaaaaaaaaaaaayyyyyyyyyyyyyyyyeeeeee"
        )
    }
    AddGameScreen(openDrawer = { },
        navigateToGame = {},
        addGame = { _, _ -> },
        resetNewCreatedGameID = { },
        newCreatedGameID = -1L,
        tempPlayerNames = tempPlayerNames,
        removeTempPlayerName = {})
}