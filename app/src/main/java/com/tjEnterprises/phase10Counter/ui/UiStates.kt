package com.tjEnterprises.phase10Counter.ui

import com.tjEnterprises.phase10Counter.data.local.database.Game
import com.tjEnterprises.phase10Counter.data.local.database.Player
import com.tjEnterprises.phase10Counter.data.local.database.PointHistory

sealed interface PlayersUiState {
    object PlayersLoading : PlayersUiState
    data class PlayersError(val throwable: Throwable) : PlayersUiState
    data class PlayersSuccess(val data: List<Player>) : PlayersUiState
}

sealed interface GamesUiState {
    object GamesLoading : GamesUiState
    data class GamesError(val throwable: Throwable) : GamesUiState
    data class GamesSuccess(val data: List<Game>) : GamesUiState
}

sealed interface GameUiState {
    object GameLoading : GameUiState
    data class GameError(val throwable: Throwable) : GameUiState
    data class GameSuccess(val data: Game) : GameUiState
}

sealed interface PointHistoryUiState {
    object PointHistoryLoading : PointHistoryUiState
    data class PointHistoryError(val throwable: Throwable) : PointHistoryUiState
    data class PointHistorySuccess(val data: List<PointHistory>) : PointHistoryUiState
}
