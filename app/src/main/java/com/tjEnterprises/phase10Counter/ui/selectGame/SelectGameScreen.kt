package com.tjEnterprises.phase10Counter.ui.selectGame

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.tjEnterprises.phase10Counter.data.local.database.Game
import com.tjEnterprises.phase10Counter.data.local.database.Player
import com.tjEnterprises.phase10Counter.ui.GamesUiState
import com.tjEnterprises.phase10Counter.ui.PlayersUiState
import com.tjEnterprises.phase10Counter.ui.component.DefaultScaffold
import com.tjEnterprises.phase10Counter.ui.component.GamePreviewComponent

@Composable
fun SelectGame(
    modifier: Modifier = Modifier,
    viewModel: SelectGameViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    navigateToGame: (String) -> Unit
) {
    val playersUiState by viewModel.playerUiState.collectAsState()
    val gamesUiState by viewModel.gamesUiState.collectAsState()

    when (playersUiState) {
        is PlayersUiState.PlayersSuccess -> {
            when (gamesUiState) {
                is GamesUiState.GamesSuccess -> {
                    SelectGame(
                        games = (gamesUiState as GamesUiState.GamesSuccess).data,
                        players = (playersUiState as PlayersUiState.PlayersSuccess).data,
                        openDrawer = openDrawer,
                        navigateToGame = navigateToGame,
                        resetGame = {viewModel.resetGameWithData(it)},
                        deleteGame = {viewModel.deleteGameWithData(it)},
                        modifier = modifier
                    )
                }

                else -> {}
            }

        }

        else -> {}
    }
}

@Composable
internal fun SelectGame(
    modifier: Modifier = Modifier,
    games: List<Game>,
    players: List<Player>,
    openDrawer: () -> Unit,
    navigateToGame: (String) -> Unit,
    resetGame: (Game) -> Unit,
    deleteGame: (Game) -> Unit
) {
    // TODO Expand card to show all players with their points
    // TODO Delete Games
    // TODO reset games -> delete the point history and phases
    // TODO make gridLayout
    // TODO add "Reset Game" Button
    DefaultScaffold(title = "Select Game", openDrawer = openDrawer) { scaffoldModifier ->
        LazyColumn(modifier = scaffoldModifier
            .then(modifier)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.End,
            content = {
                items(games) { game ->
                    GamePreviewComponent(
                        game = game,
                        players = players.filter { it.gameID == game.gameId },
                        navigateToGame = navigateToGame,
                        deleteGame = deleteGame,
                        resetGame = resetGame
                    )

                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun SelectGamePreview() {
    SelectGame(
        games = listOf(Game("Game 1"), Game("Game 2"), Game("Game 3")),
        players = listOf(
            Player(0L, "Player1", 1L),
            Player(0L, "Player2", 1L),
            Player(0L, "Player3", 1L),
            Player(0L, "Player1", 2L),
            Player(0L, "Player2", 2L),
            Player(0L, "Player3", 2L),
            Player(0L, "Player1", 3L),
            Player(0L, "Player2", 3L),
            Player(0L, "Player3", 3L)
        ),
        openDrawer = {  },
        deleteGame = {},
        resetGame = {},
        navigateToGame = {}
    )
}