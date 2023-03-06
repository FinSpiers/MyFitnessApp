package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.yaxis.SimpleYAxisDrawer
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState

@Composable
fun StepsHistory(dialogState: MaterialDialogState) {
    val x = listOf(
        LineChartData.Point(11030f, "02.03."),
        LineChartData.Point(1030f, "03.03."),
        LineChartData.Point(7892f, "04.03."),
        LineChartData.Point(7348f, "05.03."),
        LineChartData.Point(16002f, "06.03."),
        LineChartData.Point(6532f, "07.03."),
    )
    val lineChartData = listOf(LineChartData(points = x, lineDrawer = SolidLineDrawer()))
    MaterialDialog(
        dialogState = dialogState,
        shape = MaterialTheme.shapes.medium,

        ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Your step history",
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                LineChart(
                    linesChartData = lineChartData,
                    yAxisDrawer = SimpleYAxisDrawer(labelValueFormatter = { value: Float -> value.toInt().toString() })
                )
            }

        }
    }
}

