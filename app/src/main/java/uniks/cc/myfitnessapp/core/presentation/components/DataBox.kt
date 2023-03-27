package uniks.cc.myfitnessapp.feature_core.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DataBox(
    title: String,
    data: String,
    unit: String
) {

    Box(
        modifier = Modifier
            .padding(10.dp)
            .aspectRatio(1f)
            .border(
                width = 5.dp,
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(20)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "$data $unit",
                modifier = Modifier
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
