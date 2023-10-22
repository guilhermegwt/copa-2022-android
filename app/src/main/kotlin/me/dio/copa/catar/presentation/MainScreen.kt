package me.dio.copa.catar.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.dio.copa.catar.R
import me.dio.copa.catar.domain.extensions.getDate
import me.dio.copa.catar.domain.model.MatchDomain
import me.dio.copa.catar.domain.model.TeamDomain
import me.dio.copa.catar.ui.theme.Shapes

@Composable
fun MainScreen(
    matches: List<MatchDomain>,
    onClick: (match: MatchDomain) -> Unit,
) {
    MainScreenList(matches, onClick)
}

@Composable
fun MainScreenList(
    matches: List<MatchDomain>,
    onClick: (match: MatchDomain) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(matches) { match ->
                MainScreenListItem(match, onClick)
            }
        }
    }
}

@Composable
fun MainScreenListItem(
    match: MatchDomain,
    onClick: (match: MatchDomain) -> Unit,
) {
    Card(
        shape = Shapes.large,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box {
            AsyncImage(
                model = match.stadium.image,
                contentDescription = "Imagem do estádio!",
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(160.dp)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

                    var drawable: Int
                    var description: Int

                    if (match.notificationEnabled) {
                        drawable = R.drawable.ic_notifications_active
                        description = R.string.enabled_notification
                    } else {
                        drawable = R.drawable.ic_notifications
                        description = R.string.disabled_notification
                    }

                    Image(
                        painter = painterResource(id = drawable),
                        modifier = Modifier.clickable {
                            onClick(match)
                        },
                        contentDescription = description.toString()
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${match.date.getDate()} - ${match.name}",
                        style = MaterialTheme.typography.h6.copy(color = Color.White)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    MainScreenListItemTeam(match.team1)

                    Text(
                        text = "X",
                        modifier = Modifier.padding(end = 16.dp, start = 16.dp),
                        style = MaterialTheme.typography.h5.copy(color = Color.White)
                    )

                    MainScreenListItemTeam(match.team2)

                }
            }
        }
    }
}

@Composable
fun MainScreenListItemTeam(team: TeamDomain) {
    Row (verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = team.flag,
            modifier = Modifier.align(Alignment.CenterVertically),
            style = MaterialTheme.typography.h3.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = team.displayName,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6.copy(color = Color.White)
        )

    }
}
