package uniks.cc.myfitnessapp.feature_dashboard.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.entry.entryModelOf
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepsHistory(dialogShownStateFlow: MutableStateFlow<Boolean>) {
    if (dialogShownStateFlow.collectAsState().value) {
        AlertDialog(
            onDismissRequest = { dialogShownStateFlow.tryEmit(false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            val map = LinkedHashMap<Int, String>().apply {
                put(0, "02.")
                put(1, "03.")
                put(2, "04.")
                put(3, "05.")
                put(4, "06.")
                put(5, "07.")
                put(6, "08.")
                put(7, "09.")
                put(8, "10.")
                put(9, "11.")
            }
            val chartEntryModel =
                entryModelOf(20333f, 12367f, 8573f, 162f, 75f, 23132f, 10232, 10232, 10232, 10232)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large)
            ) {
                Text(
                    text = "Your step history",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

                Chart(
                    chart = columnChart(),
                    model = chartEntryModel,
                    startAxis = startAxis(valueFormatter = { value, _ ->
                        value.toInt().toString()
                    }),
                    bottomAxis = bottomAxis(valueFormatter = { value, _ -> map[value.toInt()].toString() }),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

