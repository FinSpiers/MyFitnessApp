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
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.core.chart.column.ColumnChart
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.composed.ComposedChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryModelOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import uniks.cc.myfitnessapp.core.domain.model.Steps

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepsHistory(steps : List<Steps>,dialogShownStateFlow: MutableStateFlow<Boolean>) {
    if (dialogShownStateFlow.collectAsState().value) {
        AlertDialog(
            onDismissRequest = { dialogShownStateFlow.tryEmit(false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            val map = LinkedHashMap<Int, String>()
            for (i in 6 downTo 0) {
                map[i] = steps[i].date.dropLast(7)
            }
            val chartEntryModel = entryModelOf(*steps.map { it -> it.dailyCount }.toTypedArray())

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

